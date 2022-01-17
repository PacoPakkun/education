using Lab2Sharp.Utils;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Lab2Sharp.Domain
{
    class Proba : Entity<int>
    {
        public String stil { get; set; }
        public int distanta { get; set; }

        public override string ToString()
        {
            return base.ToString() + "proba " + stil + ", " + distanta + "m";
        }
    }
}
