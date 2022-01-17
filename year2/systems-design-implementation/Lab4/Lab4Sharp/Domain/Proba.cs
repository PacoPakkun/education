using Lab2Sharp.Utils;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Lab2Sharp.Domain
{
    public class Proba : Entity<int>
    {
        public Proba(int id, int distanta, string stil)
        {
            base.id = id;
            this.distanta = distanta;
            this.stil = stil;
        }

        public String stil { get; set; }
        public int distanta { get; set; }

        public override string ToString()
        {
            return stil + " " + distanta + "m";
        }
    }
}
