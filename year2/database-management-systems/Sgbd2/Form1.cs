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
using System.Configuration;

namespace Sgbd1
{
    public partial class Form1 : Form
    {
        string connectionString;
        SqlConnection connection;
        SqlDataAdapter dataAdapter;
        DataSet dataSet;

        public Form1()
        {
            InitializeComponent();

            connectionString = ConfigurationManager.ConnectionStrings["connection_string"].ConnectionString; 
            connection = new SqlConnection(connectionString);
            dataAdapter = new SqlDataAdapter();
            dataSet = new DataSet();
        }

        private void Form1_Load(object sender, EventArgs e)
        {
            int y = 20;
            for (int i = 0; i < Int32.Parse(ConfigurationManager.AppSettings["nrColChild"]); i++)
            {
                string column = ConfigurationManager.AppSettings["columnsChild"].Split(',')[i];
                TextBox textBox = new TextBox();
                textBox.Name = column;
                textBox.Location = new Point(0, y);
                textBox.Width = 140;
                Label label = new Label();
                label.Text = column;
                label.Location = new Point(0, y - 20);
                panel1.Controls.Add(textBox);
                panel1.Controls.Add(label);
                y += 60;
            }
            try
            {
                SqlCommand cmd = new SqlCommand(ConfigurationManager.AppSettings["selectParent"], connection);
                connection.Open();
                dataAdapter.SelectCommand = cmd;
                dataAdapter.Fill(dataSet, ConfigurationManager.AppSettings["tableParent"]);
                dataGridView1.DataSource = dataSet.Tables[ConfigurationManager.AppSettings["tableParent"]];
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
                SqlCommand cmd = new SqlCommand(ConfigurationManager.AppSettings["selectChild"], connection);
                int curent = -1;
                if (dataGridView1.Rows.Count > 0)
                    curent = (int)dataGridView1.Rows[dataGridView1.SelectedCells[0].RowIndex].Cells[0].Value;
                cmd.Parameters.AddWithValue("@id", curent);

                connection.Open();
                dataAdapter.SelectCommand = cmd;
                if (dataSet.Tables.Contains(ConfigurationManager.AppSettings["tableChild"]))
                {
                    dataSet.Tables[ConfigurationManager.AppSettings["tableChild"]].Clear();
                }
                dataAdapter.Fill(dataSet, ConfigurationManager.AppSettings["tableChild"]);
                dataGridView2.DataSource = dataSet.Tables[ConfigurationManager.AppSettings["tableChild"]];
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
                SqlCommand cmd = new SqlCommand(ConfigurationManager.AppSettings["update"], connection);
                dataAdapter.UpdateCommand = cmd;
                int curent = -1;
                if (dataGridView2.Rows.Count > 0)
                    curent = (int)dataGridView2.Rows[dataGridView2.SelectedCells[0].RowIndex].Cells[0].Value;
                dataAdapter.UpdateCommand.Parameters.AddWithValue("@id", curent);

                int i = 1;
                foreach(string column in ConfigurationManager.AppSettings["columnsChild"].Split(','))
                {
                    TextBox textbox = (TextBox)panel1.Controls[column];
                    if (textbox.Text != "")
                        dataAdapter.UpdateCommand.Parameters.AddWithValue("@" + textbox.Name, textbox.Text);
                    else
                        dataAdapter.UpdateCommand.Parameters.AddWithValue("@" + textbox.Name, dataGridView2.Rows[dataGridView2.SelectedCells[0].RowIndex].Cells[i].Value.ToString());
                    i++;
                }

                connection.Open();
                dataAdapter.UpdateCommand.ExecuteNonQuery();
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
                    SqlCommand cmd = new SqlCommand(ConfigurationManager.AppSettings["delete"], connection);
                    dataAdapter.DeleteCommand = cmd;
                    int curent = -1;
                    if (dataGridView2.Rows.Count > 0)
                        curent = (int)dataGridView2.Rows[dataGridView2.SelectedCells[0].RowIndex].Cells[0].Value;
                    dataAdapter.DeleteCommand.Parameters.AddWithValue("@id", curent);

                    connection.Open();
                    dataAdapter.DeleteCommand.ExecuteNonQuery();
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
                    SqlCommand pre = new SqlCommand(ConfigurationManager.AppSettings["max"], connection);
                    SqlDataReader reader = pre.ExecuteReader();
                    int id = 1;
                    while (reader.Read())
                    {
                        id = reader.GetInt32(0) + 1;
                    }
                    connection.Close();

                    SqlCommand cmd = new SqlCommand(ConfigurationManager.AppSettings["insert"], connection);
                    dataAdapter.InsertCommand = cmd;
                    dataAdapter.InsertCommand.Parameters.AddWithValue("@id", id);
                    int curent = -1;
                    if (dataGridView1.Rows.Count > 0)
                        curent = (int)dataGridView1.Rows[dataGridView1.SelectedCells[0].RowIndex].Cells[0].Value;
                    dataAdapter.InsertCommand.Parameters.AddWithValue("@idParent", curent);

                    foreach (string column in ConfigurationManager.AppSettings["columnsChild"].Split(','))
                    {
                        TextBox textbox = (TextBox)panel1.Controls[column];
                        if (textbox.Text != "")
                            dataAdapter.InsertCommand.Parameters.AddWithValue("@" + textbox.Name, textbox.Text);
                        else
                            throw new Exception(textbox.Name + " invalid");
                    }

                    connection.Open();
                    dataAdapter.InsertCommand.ExecuteNonQuery();
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
