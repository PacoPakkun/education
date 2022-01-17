using Lab2Sharp.Domain;
using Lab2Sharp.Domain.Validator;
using Lab2Sharp.Repo.Database;
using Lab2Sharp.Repo.Interfaces;
using Lab2Sharp.Srv;
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
        String url;
        Service service;
        public Form1(Service service, String url)
        {
            InitializeComponent();
            this.service = service;
            this.url = url;

            foreach(Proba p in service.FindAllProbe())
            {
                dataGridView1.Rows.Add(p.id, p.stil, p.distanta, service.NrParticipanti(p));
                comboBox1.Items.Add(p.ToString());
                listView1.Items.Add(p.ToString());
            }
        }

        private void comboBox1_SelectedIndexChanged(object sender, EventArgs e)
        {
            dataGridView2.Rows.Clear();
            Proba proba = service.FindProba(comboBox1.SelectedIndex + 1);
            foreach (Participant p in service.GetInscrisi(proba))
            {
                String probe = "";
                foreach(Proba pr in service.GetInscrieri(p))
                {
                    probe += pr.ToString() + ", ";
                }
                dataGridView2.Rows.Add(p.nume, p.varsta, probe);
            }
        }

        private void button1_Click(object sender, EventArgs e)
        {
            try
            {
                String nume = textBox2.Text;
                int varsta = Int32.Parse(textBox3.Text);
                Participant p = new Participant(nume, varsta);
                p = service.AddParticipant(p);
                if (listView1.SelectedItems.Count == 0)
                {
                    throw new Exception("Selection empty");
                }
                foreach (int index in listView1.SelectedIndices)
                {
                    Inscriere i = new Inscriere(p.id, index + 1);
                    service.AddInscriere(i);
                }
            }
            catch (Exception ex) {
                MessageBox.Show(ex.Message);
            }
        }

        private void button3_Click(object sender, EventArgs e)
        {
            foreach(User u in service.FindAllUseri())
            {
                if(u.username==textBox4.Text && u.password == textBox5.Text)
                {
                    label5.Visible = false;
                    label6.Visible = false;
                    textBox4.Visible = false;
                    textBox5.Visible = false;
                    button3.Visible = false;
                    tabControl1.Visible = true;
                    button2.Visible = true;
                }
            }
        }

        private void button2_Click(object sender, EventArgs e)
        {
            label5.Visible = true;
            label6.Visible = true;
            textBox4.Visible = true;
            textBox5.Visible = true;
            button3.Visible = true;
            tabControl1.Visible = false;
            button2.Visible = false;
        }
    }
}
