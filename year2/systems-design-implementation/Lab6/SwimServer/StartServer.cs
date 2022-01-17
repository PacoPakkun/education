using SwimModel.Services;
using SwimModel.Validator;
using SwimNetwork;
using SwimServer.Repository.Database;
using SwimServer.Repository.Interface;
using SwimServer.Services;
using System;
using System.Collections.Generic;
using System.Configuration;
using System.Linq;
using System.Net.Sockets;
using System.Text;
using System.Threading;
using System.Threading.Tasks;

namespace SwimServer
{
    public class StartServer
    {
        public static void Main(string[] args)
        {

            ConnectionStringSettings settings = ConfigurationManager.ConnectionStrings["database.url"];
            String url = settings.ConnectionString;
            ParticipantValidator participantValidator = new ParticipantValidator();
            ParticipantRepository repo1 = new ParticipantRepositoryDB(url, participantValidator);
            ProbaRepository repo2 = new ProbaRepositoryDB(url);
            InscriereRepository repo3 = new InscriereRepositoryDB(url);
            UserRepository repo4 = new UserRepositoryDB(url);
            IService service = new Service(repo1, repo2, repo3, repo4);

            SerialServer server = new SerialServer("127.0.0.1", 55555, service);
            server.Start();
            Console.WriteLine("Server started ...");
            Console.ReadLine();
        }
    }

    public class SerialServer : ConcurrentServer
    {
        private IService server;
        private ClientWorker worker;
        public SerialServer(string host, int port, IService server) : base(host, port)
        {
            this.server = server;
            Console.WriteLine("SerialServer...");
        }
        protected override Thread createWorker(TcpClient client)
        {
            worker = new ClientWorker(server, client);
            return new Thread(new ThreadStart(worker.run));
        }
    }
}
