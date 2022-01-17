using Microsoft.AspNetCore.Builder;
using Microsoft.AspNetCore.Hosting;
using Microsoft.AspNetCore.SpaServices.ReactDevelopmentServer;
using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Hosting;
using UDT.Business.Implementation;
using UDT.Business.Interfaces;
using UDT.Repository;
using UDT.Business.Task;
using UDT.Business.Helpers;
using UDT.Model;

namespace UniversityDeadlineTracker
{
    public class Startup
    {
        public Startup(IConfiguration configuration)
        {
            Configuration = configuration;
        }

        public IConfiguration Configuration { get; }

        // This method gets called by the runtime. Use this method to add services to the container.
        public void ConfigureServices(IServiceCollection services)
        {
            services.Configure<AuthorizationSettings>(Configuration.GetSection("AuthorizationSettings"));
            services.AddDbContext<DataContext>(options => options.UseSqlServer(Configuration.GetConnectionString("DbConnection")));

            services.AddControllersWithViews();
            
            services.AddTransient<IServiceTask, ServiceTask>();
            services.AddTransient<IUserService, UserService>();
            services.AddTransient<IAccountService, AccountService>();
            services.AddTransient<IService, Service>();
            services.AddTransient<IAuthorizationHelper, AuthorizationHelper>();

            services.AddScoped<ISubjectService, SubjectService>();

            services.AddScoped<IUserTaskService, UserTaskService>();

            // In production, the React files will be served from this directory
            services.AddSpaStaticFiles(configuration => { configuration.RootPath = "ClientApp/build"; });
            services.AddSwaggerGen(c =>
            {
                c.SwaggerDoc("V1", new Microsoft.OpenApi.Models.OpenApiInfo { Title = "UDT", Version = "V1" });
            });
        }

        // This method gets called by the runtime. Use this method to configure the HTTP request pipeline.
        public void Configure(IApplicationBuilder app, IWebHostEnvironment env)
        {
            if (env.IsDevelopment())
            {
                app.UseDeveloperExceptionPage();
                app.UseSwagger();
                app.UseSwaggerUI(c => c.SwaggerEndpoint("/swagger", "UDT"));
            }
            else
            {
                app.UseExceptionHandler("/Error");
                // The default HSTS value is 30 days. You may want to change this for production scenarios, see https://aka.ms/aspnetcore-hsts.
                app.UseHsts();
            }

            app.UseHttpsRedirection();
            app.UseStaticFiles();
            app.UseSpaStaticFiles();

            app.UseRouting();

            app.UseEndpoints(endpoints =>
            {
                endpoints.MapControllerRoute(
                    name: "default",
                    pattern: "{controller}/{action=Index}/{id?}");
            });

            app.UseSpa(spa =>
            {
                spa.Options.SourcePath = "ClientApp";

                if (env.IsDevelopment())
                {
                    spa.UseReactDevelopmentServer(npmScript: "start");
                }
            });
        }
    }
}