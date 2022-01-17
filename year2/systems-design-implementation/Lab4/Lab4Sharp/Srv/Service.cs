using Lab2Sharp.Domain;
using Lab2Sharp.Repo.Interfaces;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Lab2Sharp.Srv
{
    public class Service
    {
        private ParticipantRepository participantRepository;
        private ProbaRepository probaRepository;
        private InscriereRepository inscriereRepository;
        private UserRepository userRepository;

        public Service(ParticipantRepository repo1, ProbaRepository repo2, InscriereRepository repo3, UserRepository repo4)
        {
            participantRepository = repo1;
            probaRepository = repo2;
            inscriereRepository = repo3;
            userRepository = repo4;
        }

        public IEnumerable<User> FindAllUseri()
        {
            return userRepository.FindAll();
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

        public Inscriere AddInscriere(Inscriere inscriere)
        {
            return inscriereRepository.Add(inscriere);
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
