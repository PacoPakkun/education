using SwimModel.Domain;
using SwimModel.Utils;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace SwimServer.Repository.Interface
{
    public interface InscriereRepository : Repository<Pair<int, int>, Inscriere>
    {
        IEnumerable<Participant> GetInscrisi(Proba proba);
        IEnumerable<Proba> GetInscrieri(Participant participant);
        int NrParticipanti(Proba proba);
    }
}
