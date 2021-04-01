using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using System.Diagnostics;
using System.IO;
//@Cret Andreea 2019

namespace GUI
{
    public partial class Form1 : Form
    //formular cu interfata generala a aplicatiei de conversie
    {
        public Form1()
        {
            InitializeComponent();
        }

        private void Form1_Load(object sender, EventArgs e)
        {

        }

        private string run_python_exe_conversion(string numar, string baza_initiala, string baza_finala)
        //functie care apeleaza un executabil python
        //primeste ca parametru numarul, baza sa, respectiv baza in care va fi convertit (stringuri)
        //apeleaza in consola un proces srv.exe (functionalitatea de conversie)
        //returneaza rezultatul obtinut sau eroarea obtinuta daca este cazul (string)
        {
            ProcessStartInfo psi = new ProcessStartInfo();
            psi.FileName = @"source/srv_convert.exe";
            psi.Arguments = string.Format("{0} {1} {2}", numar, baza_initiala, baza_finala);
            psi.UseShellExecute = false;
            psi.CreateNoWindow = true;
            psi.RedirectStandardOutput = true;
            psi.RedirectStandardError = true;
            string errors = "";
            string result = "";
            using (var process = Process.Start(psi))
            {
                errors = process.StandardError.ReadToEnd();
                result = process.StandardOutput.ReadToEnd();
            }
            if (errors != "") { return errors; }
            else { return result; }
        }

        private void button2_Click(object sender, EventArgs e)
        //functie corespunzatoare apasarii obiectului butonului de conversie din interfata
        //retine parametrii preluati din interfata (numarul, baza data, respectiv baza in care va fi convertit)
        //afiseaza in richtextbox eroarea "[Numar invalid]" daca nr introdus nu e o valoare numerica
        //afiseaza in richtetxtbox nr convertit returnat de functia run_python_exe_conversion
        {
            richTextBox1.Text = "";
            char[] symbols = new char[22] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'A', 'b', 'B', 'c', 'C', 'd', 'D', 'e', 'E', 'f', 'F' };
            bool ok = true;
            string numar = textBox1.Text;
            if (numar == "") ok = false;
            else
            {
                foreach (char cifra in numar)
                {
                    if (symbols.Contains(cifra) == false)
                    {
                        ok = false;
                    }
                }
            }
            if (ok == false) richTextBox1.Text = "[Numar invalid]";
            else
            {
                string baza_initiala = comboBox1.Text.Split(' ')[0];
                string baza_finala = comboBox2.Text.Split(' ')[0];
                richTextBox1.Text = run_python_exe_conversion(numar, baza_initiala, baza_finala);
            }
        }

        private void button4_Click(object sender, EventArgs e)
        //functie corespunzatoare alegerii meniului de conversie
        //afiseaza obiectele corespunzatoare acestor functionalitati, le ascunde pe cele nedorite
        {
            label4.Hide();
            label5.Hide();
            label6.Hide();
            label7.Hide();
            comboBox3.Hide();
            comboBox4.Hide();
            textBox2.Hide();
            textBox3.Hide();
            button1.Hide();
            label1.Show();
            label2.Show();
            label3.Show();
            textBox1.Show();
            comboBox1.Show();
            comboBox2.Show();
            button2.Show();
            button5.Show();
            richTextBox1.Show();
        }

        private void button3_Click(object sender, EventArgs e)
        //functie corespunzatoare alegerii meniului de calcul
        //afiseaza obiectele corespunzatoare acestor functionalitati, le ascunde pe cele nedorite
        {
            label4.Show();
            label5.Show();
            label6.Show();
            label7.Show();
            comboBox3.Show();
            comboBox4.Show();
            textBox2.Show();
            textBox3.Show();
            button1.Show();
            button5.Hide();
            label1.Hide();
            label2.Hide();
            label3.Hide();
            textBox1.Hide();
            comboBox1.Hide();
            comboBox2.Hide();
            button2.Hide();
            richTextBox1.Show();
        }

        private string run_python_exe_calculator(string numar1, string numar2, string baza, string operatie)
        //functie care apeleaza un executabil python
        //primeste ca parametru numerele, baza in care se vor face operatiile, respectiv operatia dorita (stringuri)
        //apeleaza in consola un proces srv.exe (functionalitatea de calcul)
        //returneaza rezultatul obtinut sau eroarea obtinuta daca este cazul (string)
        {
            ProcessStartInfo psi = new ProcessStartInfo();
            psi.FileName = @"source/srv_calculate.exe";
            psi.Arguments = string.Format("{0} {1} {2} {3}", numar1, numar2, baza, operatie);
            psi.UseShellExecute = false;
            psi.CreateNoWindow = true;
            psi.RedirectStandardOutput = true;
            psi.RedirectStandardError = true;
            string errors = "";
            string result = "";
            using (var process = Process.Start(psi))
            {
                errors = process.StandardError.ReadToEnd();
                result = process.StandardOutput.ReadToEnd();
            }
            if (errors != "") { return errors; }
            else { return result; }
        }

        private void button1_Click(object sender, EventArgs e)
        //functie corespunzatoare apasarii obiectului butonului de calcule din interfata
        //retine parametrii preluati din interfata (cele 2 numere, baza in care se va efectua operatia, respectiv operatia dorita)
        //afiseaza eroarea "[Numar invalid]" in richtextbox daca nr introduse nu sunt valoari numerice
        //afiseaza in richtextbox nr convertit returnat de functia run_python_exe_caclulator
        {
            richTextBox1.Text = "";
            char[] symbols = new char[22] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'A', 'b', 'B', 'c', 'C', 'd', 'D', 'e', 'E', 'f', 'F' };
            bool ok = true;
            string numar1 = textBox2.Text;
            if (numar1 == "") ok = false;
            else
            {
                foreach (char cifra in numar1)
                {
                    if (symbols.Contains(cifra) == false)
                    {
                        ok = false;
                    }
                }
            }
            if (ok == false) richTextBox1.Text = "[Numar invalid]";
            else
            {
                ok = true;
                string numar2 = textBox3.Text;
                if (numar2 == "") ok = false;
                else
                {
                    foreach (char cifra in numar2)
                    {
                        if (symbols.Contains(cifra) == false)
                        {
                            ok = false;
                        }
                    }
                }
                if (ok == false) richTextBox1.Text = "[Numar invalid]";
                else 
                {
                    string baza = comboBox3.Text.Split(' ')[0];
                    string operatie = comboBox4.Text.Split(' ')[0];
                    richTextBox1.Text = run_python_exe_calculator(numar1, numar2, baza, operatie);
                }
            }
        }

        private string run_python_exe_conversion_baza_intermediara(string numar, string baza_initiala, string baza_finala)
        //functie care apeleaza un executabil python
        //primeste ca parametru numarul, baza sa, respectiv baza in care va fi convertit (stringuri)
        //apeleaza in consola un proces srv.exe (functionalitatea de conversie)
        //returneaza rezultatul obtinut sau eroarea obtinuta daca este cazul (string)
        {
            ProcessStartInfo psi = new ProcessStartInfo();
            psi.FileName = @"source/srv_convert_baza_intermediara.exe";
            psi.Arguments = string.Format("{0} {1} {2}", numar, baza_initiala, baza_finala);
            psi.UseShellExecute = false;
            psi.CreateNoWindow = true;
            psi.RedirectStandardOutput = true;
            psi.RedirectStandardError = true;
            string errors = "";
            string result = "";
            using (var process = Process.Start(psi))
            {
                errors = process.StandardError.ReadToEnd();
                result = process.StandardOutput.ReadToEnd();
            }
            if (errors != "") { return errors; }
            else { return result; }
        }

        private void button5_Click(object sender, EventArgs e)
        //functie corespunzatoare apasarii obiectului butonului de conversie cu baza intermediara din interfata
        //retine parametrii preluati din interfata (numarul, baza data, respectiv baza in care va fi convertit)
        //afiseaza in richtextbox eroarea "[Numar invalid]" daca nr introdus nu e o valoare numerica
        //afiseaza in richtetxtbox nr convertit returnat de functia run_python_exe_conversion_baza_intermediara
        {
            richTextBox1.Text = "";
            char[] symbols = new char[22] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'A', 'b', 'B', 'c', 'C', 'd', 'D', 'e', 'E', 'f', 'F' };
            bool ok = true;
            string numar = textBox1.Text;
            if (numar == "") ok = false;
            else
            {
                foreach (char cifra in numar)
                {
                    if (symbols.Contains(cifra) == false)
                    {
                        ok = false;
                    }
                }
            }
            if (ok == false) richTextBox1.Text = "[Numar invalid]";
            else
            {
                string baza_initiala = comboBox1.Text.Split(' ')[0];
                string baza_finala = comboBox2.Text.Split(' ')[0];
                richTextBox1.Text = run_python_exe_conversion_baza_intermediara(numar, baza_initiala, baza_finala);
            }
        }

        private void conversiiToolStripMenuItem_Click(object sender, EventArgs e)
        //functie corespunzatoare alegerii meniului de conversie
        //afiseaza obiectele corespunzatoare acestor functionalitati, le ascunde pe cele nedorite
        {
            label4.Hide();
            label5.Hide();
            label6.Hide();
            label7.Hide();
            comboBox3.Hide();
            comboBox4.Hide();
            textBox2.Hide();
            textBox3.Hide();
            button1.Hide();
            label1.Show();
            label2.Show();
            label3.Show();
            textBox1.Show();
            comboBox1.Show();
            comboBox2.Show();
            button2.Show();
            button5.Show();
            richTextBox1.Show();
        }

        private void calculeToolStripMenuItem_Click(object sender, EventArgs e)
        //functie corespunzatoare alegerii meniului de calcul
        //afiseaza obiectele corespunzatoare acestor functionalitati, le ascunde pe cele nedorite
        {
            label4.Show();
            label5.Show();
            label6.Show();
            label7.Show();
            comboBox3.Show();
            comboBox4.Show();
            textBox2.Show();
            textBox3.Show();
            button1.Show();
            button5.Hide();
            label1.Hide();
            label2.Hide();
            label3.Hide();
            textBox1.Hide();
            comboBox1.Hide();
            comboBox2.Hide();
            button2.Hide();
            richTextBox1.Show();
        }
    }
}
