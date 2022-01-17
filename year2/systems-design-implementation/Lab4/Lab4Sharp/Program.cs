using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using System.Windows.Forms;
using System.Data.SQLite;
using System.Configuration;
using Lab2Sharp.Repo.Database;
using Lab2Sharp.Domain;
using Lab2Sharp.Utils;
using Lab2Sharp.Domain.Validator;
using Lab2Sharp.Repo.Interfaces;
using Lab2Sharp.Srv;

namespace Lab2Sharp
{
    static class Program
    {
        [STAThread]
        static void Main()
        {
            ConnectionStringSettings settings = ConfigurationManager.ConnectionStrings["database.url"];
            String url = settings.ConnectionString;
            ParticipantValidator participantValidator = new ParticipantValidator();
            ParticipantRepository repo1 = new ParticipantRepositoryDB(url, participantValidator);
            ProbaRepository repo2 = new ProbaRepositoryDB(url);
            InscriereRepository repo3 = new InscriereRepositoryDB(url);
            UserRepository repo4 = new UserRepositoryDB(url);
            Service service = new Service(repo1, repo2, repo3, repo4);

            Application.EnableVisualStyles();
            Application.SetCompatibleTextRenderingDefault(false);
            Application.Run(new Form1(service, url));
        }
    }
}
