using System;
using System.Collections.Generic;
using System.Configuration;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Builder;
using Microsoft.AspNetCore.Hosting;
using Microsoft.AspNetCore.Http;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Hosting;
using SwimModel.Validator;
using SwimServer.Repository.Database;
using SwimServer.Repository.Interface;

namespace SwimService
{
    public class Startup
    {
        // This method gets called by the runtime. Use this method to add services to the container.
        // For more information on how to configure your application, visit https://go.microsoft.com/fwlink/?LinkID=398940
        public void ConfigureServices(IServiceCollection services)
        {
            ConnectionStringSettings settings = ConfigurationManager.ConnectionStrings["database.url"];
            String url = settings.ConnectionString;
            ParticipantValidator participantValidator = new ParticipantValidator();
            ParticipantRepository repo1 = new ParticipantRepositoryDB(url, participantValidator);
            ProbaRepository repo2 = new ProbaRepositoryDB(url);
            InscriereRepository repo3 = new InscriereRepositoryDB(url);
            UserRepository repo4 = new UserRepositoryDB(url);
            ServiceImpl serviceImpl = new ServiceImpl(repo1, repo2, repo3, repo4);
            SwimService.BindService(serviceImpl);
            services.AddGrpc();
        }

        // This method gets called by the runtime. Use this method to configure the HTTP request pipeline.
        public void Configure(IApplicationBuilder app, IWebHostEnvironment env)
        {
            if (env.IsDevelopment())
            {
                app.UseDeveloperExceptionPage();
            }

            app.UseRouting();

            app.UseEndpoints(endpoints =>
            {
                endpoints.MapGrpcService<ServiceImpl>();

                endpoints.MapGet("/", async context =>
                {
                    await context.Response.WriteAsync("Communication with gRPC endpoints must be made through a gRPC client. To learn how to create a client, visit: https://go.microsoft.com/fwlink/?linkid=2086909");
                });
            });
        }
    }
}
