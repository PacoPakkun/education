using SwimModel.Domain;
using SwimModel.Services;
using SwimServer.Repository.Interface;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace SwimServer.Services
{
    public class Service : IService
    {
        private ParticipantRepository participantRepository;
        private ProbaRepository probaRepository;
        private InscriereRepository inscriereRepository;
        private UserRepository userRepository;
        private readonly IDictionary<String, Observer> loggedUsers;

        public Service(ParticipantRepository repo1, ProbaRepository repo2, InscriereRepository repo3,
            UserRepository repo4)
        {
            participantRepository = repo1;
            probaRepository = repo2;
            inscriereRepository = repo3;
            userRepository = repo4;
            loggedUsers = new Dictionary<String, Observer>();
        }


        public void Login(User user, Observer client)
        {
            User usr = null;
            foreach (User u in userRepository.FindAll())
            {
                if (u.username == user.username && u.password == user.password)
                {
                    usr = u;
                }
            }

            if (usr != null)
                loggedUsers[user.username] = client;
            else
                throw new Exception("Authentication failed.");
        }

        public void Logout(User user)
        {
            loggedUsers.Remove(user.username);
        }

        public IEnumerable<Proba> FindAllProbe()
        {
            return probaRepository.FindAll();
        }

        public Proba FindProba(int id)
        {
            return probaRepository.FindOne(id);
        }

        public Participant AddParticipant(Participant participant)
        {
            return participantRepository.Add(participant);
        }

        public Inscriere AddInscriere(Inscriere inscriere, User user)
        {
            Inscriere i = inscriereRepository.Add(inscriere);
            foreach (String username in loggedUsers.Keys)
            {
                // if (user.username != username)
                // {
                    Task.Run(() => loggedUsers[username].Update(i));
                // }
            }

            return i;
        }

        public int NrParticipanti(Proba proba)
        {
            return inscriereRepository.NrParticipanti(proba);
        }

        public IEnumerable<Participant> GetInscrisi(Proba proba)
        {
            return inscriereRepository.GetInscrisi(proba);
        }

        public IEnumerable<Proba> GetInscrieri(Participant participant)
        {
            return inscriereRepository.GetInscrieri(participant);
        }
    }
}