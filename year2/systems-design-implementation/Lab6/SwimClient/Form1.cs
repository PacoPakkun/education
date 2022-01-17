using SwimClient.Controller;
using System;
using System.Collections.Generic;
using System.Windows.Forms;

namespace SwimClient
{
    public partial class Form1 : Form
    {
        MainController controller;
        public Form1(MainController controller)
        {
            InitializeComponent();
            this.controller = controller;
            controller.updateEvent += update;
        }
        
        public void update(object sender, EventArgs e)
        {
            this.BeginInvoke(new UpdateCallback(this.updateData));
        }

        private void updateData()
        {
            Console.WriteLine("entered updatedata");
            dataGridView1.Rows.Clear();
            foreach (String s in controller.LoadProbe())
            {
                dataGridView1.Rows.Add(s.Split(';')[0], s.Split(';')[1], s.Split(';')[2], s.Split(';')[3]);
            }
            dataGridView2.Rows.Clear();
            if (comboBox1.SelectedIndex >= 0)
            {
                foreach (String s in controller.LoadCombo(comboBox1.SelectedIndex + 1))
                {
                    dataGridView2.Rows.Add(s.Split(';')[0], s.Split(';')[1], s.Split(';')[2]);
                }
            }

            Console.WriteLine("finished updatedata");
        }

        public delegate void UpdateCallback();
        
        private void comboBox1_SelectedIndexChanged(object sender, EventArgs e)
        {
            dataGridView2.Rows.Clear();
            foreach (String s in controller.LoadCombo(comboBox1.SelectedIndex + 1))
            {
                dataGridView2.Rows.Add(s.Split(';')[0], s.Split(';')[1], s.Split(';')[2]);
            }
        }

        private void button1_Click(object sender, EventArgs e)
        {
            try
            {
                if (listView1.SelectedItems.Count == 0)
                {
                    throw new Exception("Selection empty");
                }
                List<int> indices = new List<int>();
                foreach (int index in listView1.SelectedIndices) indices.Add(index);
                controller.Register(textBox2.Text, Int32.Parse(textBox3.Text), indices);
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }

        private void button3_Click(object sender, EventArgs e)
        {
            try
            {
                controller.Login(textBox4.Text, textBox5.Text);
                foreach (String s in controller.LoadProbe())
                {
                    dataGridView1.Rows.Add(s.Split(';')[0], s.Split(';')[1], s.Split(';')[2], s.Split(';')[3]);
                    comboBox1.Items.Add(s.Split(';')[1] + " " + s.Split(';')[2]);
                    listView1.Items.Add(s.Split(';')[1] + " " + s.Split(';')[2]);
                }
                label5.Visible = false;
                label6.Visible = false;
                textBox4.Visible = false;
                textBox5.Visible = false;
                button3.Visible = false;
                tabControl1.Visible = true;
                button2.Visible = true;
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }

        private void button2_Click(object sender, EventArgs e)
        {
            controller.Logout();
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
