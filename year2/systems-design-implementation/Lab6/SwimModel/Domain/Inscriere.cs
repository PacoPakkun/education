using SwimModel.Utils;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace SwimModel.Domain
{
    [Serializable]
    public class Inscriere : Entity<Pair<int, int>>
    {
        public Participant participant { get; set; }
        public Proba proba { get; set; }
        public Inscriere(int e1, int e2)
        {
            base.id = new Pair<int, int>(e1, e2);
            participant = null;
            proba = null;
        }
        public Inscriere(int e1, int e2, Participant pa, Proba pr)
        {
            base.id = new Pair<int, int>(e1, e2);
            participant = pa;
            proba = pr;
        }
    }
}
