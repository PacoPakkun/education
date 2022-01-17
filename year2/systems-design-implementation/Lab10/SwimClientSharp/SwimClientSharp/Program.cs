using System;
using System.Net.Http;
using System.Net.Http.Headers;
using System.Threading.Tasks;
using Newtonsoft.Json;

namespace SwimClientSharp
{
    class Program
    {
        static HttpClient client = new HttpClient();
        static String url = "http://localhost:8080/swim/probe";
        static int id;

        public static void Main(string[] args)
        {
            RunAsync().Wait();
        }

        static async Task RunAsync()
        {
            client.DefaultRequestHeaders.Accept.Clear();
            client.DefaultRequestHeaders.Accept.Add(new MediaTypeWithQualityHeaderValue("application/json"));

            Console.WriteLine("\n--- Find all probe ---\n");
            Proba[] probe = await findAll();
            foreach (var proba in await findAll())
            {
                Console.WriteLine(proba);
            }

            Console.WriteLine("\n--- Find one proba ---\n");
            Proba p = await findOne();
            Console.WriteLine(p);

            Console.WriteLine("\n--- Create proba ---\n");
            p = await create();
            id = p.id;
            probe = await findAll();
            foreach (var proba in await findAll())
            {
                Console.WriteLine(proba);
            }

            Console.WriteLine("\n--- Update proba ---\n");
            p = await update();
            probe = await findAll();
            foreach (var proba in await findAll())
            {
                Console.WriteLine(proba);
            }
            
            Console.WriteLine("\n--- Delete proba ---\n");
            p = await delete();
            probe = await findAll();
            foreach (var proba in await findAll())
            {
                Console.WriteLine(proba);
            }
        }

        static async Task<Proba[]> findAll()
        {
            Proba[] probe = null;
            HttpResponseMessage response = await client.GetAsync(url);
            if (response.IsSuccessStatusCode)
            {
                probe = await response.Content.ReadAsAsync<Proba[]>();
            }

            return probe;
        }

        static async Task<Proba> findOne()
        {
            Proba proba = null;
            HttpResponseMessage response = await client.GetAsync(url + "/1");
            if (response.IsSuccessStatusCode)
            {
                proba = await response.Content.ReadAsAsync<Proba>();
            }

            return proba;
        }

        static async Task<Proba> create()
        {
            Proba proba = new Proba(500, "spate");
            string json = JsonConvert.SerializeObject(proba);
            StringContent httpContent = new StringContent(json, System.Text.Encoding.UTF8, "application/json");

            HttpResponseMessage response = await client.PostAsync(url, httpContent);
            if (response.IsSuccessStatusCode)
            {
                proba = await response.Content.ReadAsAsync<Proba>();
            }

            return proba;
        }

        static async Task<Proba> update()
        {
            Proba proba = new Proba(id, 350, "lateral");
            string json = JsonConvert.SerializeObject(proba);
            StringContent httpContent = new StringContent(json, System.Text.Encoding.UTF8, "application/json");

            HttpResponseMessage response = await client.PutAsync(url, httpContent);
            if (response.IsSuccessStatusCode)
            {
                proba = await response.Content.ReadAsAsync<Proba>();
            }

            return proba;
        }

        static async Task<Proba> delete()
        {
            Proba proba = null;
            HttpResponseMessage response = await client.DeleteAsync(url + "/" + id);
            if (response.IsSuccessStatusCode)
            {
                proba = await response.Content.ReadAsAsync<Proba>();
            }

            return proba;
        }
    }
}