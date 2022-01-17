using Lab2Sharp.Domain;
using Lab2Sharp.Repo.Database;
using Lab2Sharp.Utils;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Configuration;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace Lab2Sharp
{
    public partial class Form1 : Form
    {
        public Form1()
        {
            InitializeComponent();
        }

        private void button1_Click(object sender, EventArgs e)
        {
            ConnectionStringSettings settings = ConfigurationManager.ConnectionStrings["database.url"];
            string url = settings.ConnectionString;

            ParticipantRepositoryDB<int, Participant> repo = new ParticipantRepositoryDB<int, Participant>(url);
            ProbaRepositoryDB<int, Proba> repo2 = new ProbaRepositoryDB<int, Proba>(url);
            InscriereRepositoryDB<Pair<int, int>, Inscriere> repo3 = new InscriereRepositoryDB<Pair<int, int>, Inscriere>(url);
            UserRepositoryDB<int, User> repo4 = new UserRepositoryDB<int, User>(url);
            richTextBox1.Text = "";
            foreach (var el in repo.FindAll().ToList())
            {
                richTextBox1.Text += el + "\n";
            }
            richTextBox1.Text += "\n";
            foreach (var el in repo2.FindAll().ToList())
            {
                richTextBox1.Text += el + "\n";
            }
            richTextBox1.Text += "\n";
            foreach (var el in repo3.FindAll().ToList())
            {
                richTextBox1.Text += el + "\n";
            }
            richTextBox1.Text += "\n";
            foreach (var el in repo4.FindAll().ToList())
            {
                richTextBox1.Text += el + "\n";
            }
            richTextBox1.Text += "\n";
        }
    }
}
