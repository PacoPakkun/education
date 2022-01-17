using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Data.SqlClient;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace Deadlock
{
    public partial class Form1 : Form
    {
        public Form1()
        {
            InitializeComponent();
        }

        private void buttonStart_Click(object sender, EventArgs e)
        {
            ThreadStart deadlock1 = new ThreadStart(Deadlock1);
            ThreadStart deadlock2 = new ThreadStart(Deadlock2);

            Thread d1 = new Thread(deadlock1);
            Thread d2 = new Thread(deadlock2);

            d1.Start();
            d2.Start();
        }

        private void Deadlock1()
        {
            Deadlock("deadlock1");
        }

        private void Deadlock2()
        {
            Deadlock("deadlock2");
        }

        void Deadlock(String deadlock)
        {
            string cmd;
            if (deadlock == "deadlock1")
            {
                cmd =
                    "SET DEADLOCK_PRIORITY high begin tran update Furnizori set nume='Glovo' where id=4 waitfor delay '00:00:02' update Categorii set nume='Dulciuri' where id=13 commit tran";
            }
            else
            {
                cmd =
                    "begin tran update Categorii set nume='Snacks' where id=13 waitfor delay '00:00:02' update Furnizori set nume='Panda' where id=4 commit tran";
            }

            SqlConnection connection =
                new SqlConnection("Server=LAPTOP-SJ532SIA\\SQLEXPRESS;Database=LIQUOR STORE;Integrated Security=true;");
            MessageBox.Show(deadlock + " started!");
            SqlCommand command = new SqlCommand(cmd, connection);
            connection.Open();
            int rows_affected = 0;
            try
            {
                rows_affected = command.ExecuteNonQuery();
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
                MessageBox.Show(deadlock + " failed!");
                int tries = 1;
                while (rows_affected < 2 && tries < 3)
                {
                    MessageBox.Show(deadlock + " failed! rows affected: " + rows_affected + " tries: " + tries);
                    try
                    {
                        rows_affected = command.ExecuteNonQuery();
                        MessageBox.Show(deadlock + " tries: " + tries);

                        MessageBox.Show("succes");
                    }
                    catch (Exception exe)
                    {
                        MessageBox.Show(exe.Message);
                        MessageBox.Show(deadlock + " failed!");
                    }
                    finally
                    {
                        tries++;
                    }
                }
                if (tries == 3)
                {
                    MessageBox.Show(deadlock + " aborted!");
                }
            }
        }
    }
}