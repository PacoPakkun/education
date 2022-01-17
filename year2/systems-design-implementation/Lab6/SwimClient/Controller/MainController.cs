using SwimModel.Domain;
using SwimModel.Services;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace SwimClient.Controller
{
    public class MainController : Observer
    {
        public event EventHandler<EventArgs> updateEvent;
        IService service;
        User user;

        public MainController(IService server)
        {
            this.service = server;
        }

        protected virtual void OnUserEvent(EventArgs e)
        {
            if (updateEvent == null) return;
            updateEvent(this, e);
            Console.WriteLine("Update Event called");
        }

        public void Update(Inscriere inscriere)
        {
            EventArgs eventArgs = new EventArgs();
            OnUserEvent(eventArgs);
        }

        public List<String> LoadProbe()
        {
            List<String> entries = new List<string>();
            foreach (Proba p in service.FindAllProbe())
            {
                String result = p.id.ToString() + ";" + p.stil.ToString() + ";" + p.distanta + ";" +
                                service.NrParticipanti(p).ToString();
                entries.Add(result);
            }

            return entries;
        }

        public List<String> LoadCombo(int id)
        {
            List<String> entries = new List<string>();
            Proba proba = service.FindProba(id);
            foreach (Participant p in service.GetInscrisi(proba))
            {
                String result = "";
                foreach (Proba pr in service.GetInscrieri(p))
                {
                    result += pr.ToString() + ", ";
                }

                result = p.nume + ";" + p.varsta + ";" + result;
                entries.Add(result);
            }

            return entries;
        }

        public void Login(String username, String password)
        {
            user = new User(username, password);
            service.Login(user, this);
        }

        public void Logout()
        {
            service.Logout(user);
        }

        public void Register(String nume, int varsta, IEnumerable<int> probe)
        {
            Participant p = new Participant(nume, varsta);
            p = service.AddParticipant(p);
            foreach (int id in probe)
            {
                Inscriere i = new Inscriere(p.id, id + 1);
                service.AddInscriere(i, user);
            }
        }
    }
}