using Lab2Sharp.Domain;
using Lab2Sharp.Utils;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Lab2Sharp.Repo.Interfaces
{
    interface InscriereRepository<ID, E> : Repository<Pair<int, int>, Inscriere>
    {
        IEnumerable<Participant> GetInscrisi(Proba proba);
        int nrParticipanti(Proba proba);
    }
}
