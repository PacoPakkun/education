using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using System.Data.SqlClient;

namespace Sgbd1
{
    public partial class Form1 : Form
    {
        string connectionString;
        SqlConnection connection;
        SqlDataAdapter da;
        DataSet liquor;
        //DataSet dsCategorii;
        //DataSet dsProduse;
        BindingSource bs;

        public Form1()
        {
            InitializeComponent();

            connectionString = "Server=LAPTOP-SJ532SIA\\SQLEXPRESS;Database=LIQUOR STORE;Integrated Security=true;";
            connection = new SqlConnection(connectionString);
            da = new SqlDataAdapter();
            //dsCategorii = new DataSet();
            //dsProduse = new DataSet();
            liquor = new DataSet();
            bs = new BindingSource();

            try
            {
                SqlCommand cmd = new SqlCommand("SELECT id, nume FROM Categorii", connection);
                connection.Open();
                da.SelectCommand = cmd;
                //dsCategorii.Clear();
                da.Fill(liquor, "categorii");
                //da.Fill(dsCategorii);
                //dataGridView1.DataSource = dsCategorii.Tables[0];
                dataGridView1.DataSource = liquor.Tables["categorii"];
                connection.Close();
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message, "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
            }
        }

        public void reload()
        {
            try
            {
                SqlCommand cmd = new SqlCommand("SELECT id, nume, concentratie, cantitate, descriere, pret FROM Produse where id_categorie=@idc", connection);
                int curent = -1;
                if (dataGridView1.Rows.Count > 0)
                    curent = (int)dataGridView1.Rows[dataGridView1.SelectedCells[0].RowIndex].Cells[0].Value;
                cmd.Parameters.AddWithValue("@idc", curent);

                connection.Open();
                da.SelectCommand = cmd;
                //dsProduse.Clear();
                if (liquor.Tables.Contains("produse"))
                {
                    liquor.Tables["produse"].Clear();
                }
                //liquor.Clear();
                //da.Fill(dsProduse);
                da.Fill(liquor,"produse");
                //dataGridView2.DataSource = dsProduse.Tables[0];
                dataGridView2.DataSource = liquor.Tables["produse"];
                connection.Close();
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message, "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
            }
        }

        private void dataGridView1_CellClick(object sender, DataGridViewCellEventArgs e)
        {
            if (dataGridView1.Rows.Count > 0)
            {
                button3.Enabled = true;
            }
            reload();
        }

        private void dataGridView2_CellClick(object sender, DataGridViewCellEventArgs e)
        {
            if (dataGridView2.Rows.Count > 0)
            {
                button1.Enabled = true;
                button2.Enabled = true;
            }
        }

        private void button1_Click(object sender, EventArgs e)
        {
            try
            {
                SqlCommand cmd = new SqlCommand("update Produse set nume=@nume, concentratie=@concentratie, cantitate=@cantitate, descriere=@descriere, pret=@pret where id = @id", connection);
                da.UpdateCommand = cmd;
                int curent = -1;
                if (dataGridView2.Rows.Count > 0)
                    curent = (int)dataGridView2.Rows[dataGridView2.SelectedCells[0].RowIndex].Cells[0].Value;
                da.UpdateCommand.Parameters.AddWithValue("@id", curent);
                if (textBox1.Text != "")
                    da.UpdateCommand.Parameters.AddWithValue("@nume", textBox1.Text);
                else
                    da.UpdateCommand.Parameters.AddWithValue("@nume", (string)dataGridView2.Rows[dataGridView2.SelectedCells[0].RowIndex].Cells[1].Value);
                if (textBox2.Text != "")
                    da.UpdateCommand.Parameters.AddWithValue("@concentratie", textBox2.Text);
                else
                    da.UpdateCommand.Parameters.AddWithValue("@concentratie", (double)dataGridView2.Rows[dataGridView2.SelectedCells[0].RowIndex].Cells[2].Value);
                if (textBox3.Text != "")
                    da.UpdateCommand.Parameters.AddWithValue("@cantitate", textBox3.Text);
                else
                    da.UpdateCommand.Parameters.AddWithValue("@cantitate", (double)dataGridView2.Rows[dataGridView2.SelectedCells[0].RowIndex].Cells[3].Value);
                if (textBox4.Text != "")
                    da.UpdateCommand.Parameters.AddWithValue("@descriere", textBox4.Text);
                else
                    da.UpdateCommand.Parameters.AddWithValue("@descriere", (string)dataGridView2.Rows[dataGridView2.SelectedCells[0].RowIndex].Cells[4].Value);
                if (textBox5.Text != "")
                    da.UpdateCommand.Parameters.AddWithValue("@pret", textBox5.Text);
                else
                    da.UpdateCommand.Parameters.AddWithValue("@pret", (double)dataGridView2.Rows[dataGridView2.SelectedCells[0].RowIndex].Cells[5].Value);
                
                connection.Open();
                da.UpdateCommand.ExecuteNonQuery();
                connection.Close();
                reload();
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message, "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
            }
        }

        private void button2_Click(object sender, EventArgs e)
        { 
            try
            {
                using (SqlConnection connection = new SqlConnection(connectionString))
                {
                    SqlCommand cmd = new SqlCommand("delete FROM Produse where id=@id", connection);
                    da.DeleteCommand = cmd;
                    int curent = -1;
                    if (dataGridView2.Rows.Count > 0)
                        curent = (int)dataGridView2.Rows[dataGridView2.SelectedCells[0].RowIndex].Cells[0].Value;
                    da.DeleteCommand.Parameters.AddWithValue("@id", curent);

                    connection.Open();
                    da.DeleteCommand.ExecuteNonQuery();
                    connection.Close();
                    reload();
                }

            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message, "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
            }
        }

        private void button3_Click(object sender, EventArgs e)
        {
            try
            {
                using (SqlConnection connection = new SqlConnection(connectionString))
                {
                    connection.Open();
                    SqlCommand pre = new SqlCommand("select max(id) as max from Produse", connection);
                    SqlDataReader reader = pre.ExecuteReader();
                    int id = 1;
                    while (reader.Read())
                    {
                        id = reader.GetInt32(0) + 1;
                    }
                    connection.Close();

                    SqlCommand cmd = new SqlCommand("insert into Produse(id,nume,concentratie,cantitate,descriere,pret,id_furnizor,id_categorie) values (@id,@nume,@concentratie,@cantitate,@descriere,@pret,1,@categorie)", connection);
                    da.InsertCommand = cmd;
                    da.InsertCommand.Parameters.AddWithValue("@id", id);
                    int curent = -1;
                    if (dataGridView1.Rows.Count > 0)
                        curent = (int)dataGridView1.Rows[dataGridView1.SelectedCells[0].RowIndex].Cells[0].Value;
                    da.InsertCommand.Parameters.AddWithValue("@categorie", curent);
                    if (textBox1.Text != "")
                        da.InsertCommand.Parameters.AddWithValue("@nume", textBox1.Text);
                    else
                        throw new Exception("nume invalid");
                    if (textBox2.Text != "")
                        da.InsertCommand.Parameters.AddWithValue("@concentratie", textBox2.Text);
                    else
                        throw new Exception("concentratie invalida");
                    if (textBox3.Text != "")
                        da.InsertCommand.Parameters.AddWithValue("@cantitate", textBox3.Text);
                    else
                        throw new Exception("cantitate invalida");
                    if (textBox4.Text != "")
                        da.InsertCommand.Parameters.AddWithValue("@descriere", textBox4.Text);
                    else
                        throw new Exception("descriere invalida");
                    if (textBox5.Text != "")
                        da.InsertCommand.Parameters.AddWithValue("@pret", textBox5.Text);
                    else
                        throw new Exception("pret invalid");

                    connection.Open();
                    da.InsertCommand.ExecuteNonQuery();
                    connection.Close();
                    reload();
                }

            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message, "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
            }
        }
    }
}
