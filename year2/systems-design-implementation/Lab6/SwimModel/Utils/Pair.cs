using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace SwimModel.Utils
{
    [Serializable]
    public class Pair<E1, E2>
    {

        public Pair(E1 e11, E2 e21)
        {
            this.e1 = e11;
            this.e2 = e21;
        }

        public E1 e1 { get; set; }
        public E2 e2 { get; set; }

        public override string ToString()
        {
            return "" + e1 + "," + e2;
        }
    }
}
