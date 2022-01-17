using System;
using System.Collections.Generic;
using System.Configuration;
using System.IO;
using System.Linq;
using System.Threading.Tasks;
using Grpc.Core;
using Microsoft.AspNetCore.Hosting;
using Microsoft.Extensions.Hosting;
using SwimModel.Validator;
using SwimServer.Repository.Database;
using SwimServer.Repository.Interface;

namespace SwimService
{
    public class Program
    {
        const int Port = 50051;

        public static void Main(string[] args)
        {
            ConnectionStringSettings settings = ConfigurationManager.ConnectionStrings["database.url"];
            String url = settings.ConnectionString;
            ParticipantValidator participantValidator = new ParticipantValidator();
            ParticipantRepository repo1 = new ParticipantRepositoryDB(url, participantValidator);
            ProbaRepository repo2 = new ProbaRepositoryDB(url);
            InscriereRepository repo3 = new InscriereRepositoryDB(url);
            UserRepository repo4 = new UserRepositoryDB(url);
            ServiceImpl serviceImpl = new ServiceImpl(repo1, repo2, repo3, repo4);

            Server server = new Server
            {
                Services = {SwimService.BindService(serviceImpl)},
                Ports = {new ServerPort("localhost", Port, ServerCredentials.Insecure)}
            };
            server.Start();

            Console.WriteLine("Greeter server listening on port " + Port);
            Console.WriteLine("Press any key to stop the server...");
            Console.ReadKey();

            server.ShutdownAsync().Wait();
        }

        // public static void Main(string[] args)
        // {
        // CreateHostBuilder(args).Build().Run();
        // }
        // Additional configuration is required to successfully run gRPC on macOS.
        // For instructions on how to configure Kestrel and gRPC clients on macOS, visit https://go.microsoft.com/fwlink/?linkid=2099682
        // public static IHostBuilder CreateHostBuilder(string[] args) =>
        //     Host.CreateDefaultBuilder(args)
        //         .ConfigureWebHostDefaults(webBuilder =>
        //         {
        //             webBuilder.UseStartup<Startup>();
        //         });
    }
}