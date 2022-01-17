using SwimModel.Domain;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace SwimModel.Services
{
    public interface IService
    {
        void Login(User user, Observer client);

        void Logout(User user);

        IEnumerable<Proba> FindAllProbe();

        Proba FindProba(int id);

        Participant AddParticipant(Participant participant);

        Inscriere AddInscriere(Inscriere inscriere, User user);

        int NrParticipanti(Proba proba);

        IEnumerable<Participant> GetInscrisi(Proba proba);

        IEnumerable<Proba> GetInscrieri(Participant participant);
    }
}
