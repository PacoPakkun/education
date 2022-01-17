using SwimClient.Controller;
using SwimModel.Services;
using SwimNetwork;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace SwimClient
{
    static class Program
    {
        /// <summary>
        /// The main entry point for the application.
        /// </summary>
        [STAThread]
        static void Main()
        {
            Application.EnableVisualStyles();
            Application.SetCompatibleTextRenderingDefault(false);

            Console.WriteLine("mesaj");
            IService server = new ServerProxy("127.0.0.1", 55555);
            MainController ctrl = new MainController(server);
            Form1 win = new Form1(ctrl);
            Application.Run(win);
        }
    }
}
