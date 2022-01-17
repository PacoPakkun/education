using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Lab2Sharp.Utils
{
    public class Pair<E1, E2>
    {
        public E1 e1 { get; set; }
        public E2 e2 { get; set; }

        public override string ToString()
        {
            return "" + e1 + "," + e2;
        }
    }
}
