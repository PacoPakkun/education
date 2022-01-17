using Lab2Sharp.Utils;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Lab2Sharp.Domain
{
    class Inscriere : Entity<Pair<int, int>>
    {

        public Inscriere(int e1, int e2)
        {
            base.id = new Pair<int, int>(e1, e2);
        }
    }
}
