using Lab2Sharp.Utils;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Lab2Sharp.Domain
{
    public class Participant : Entity<int>
    {
        public Participant(string nume, int varsta)
        {
            this.nume = nume;
            this.varsta = varsta;
        }
        public Participant(int id, string nume, int varsta)
        {
            base.id = id;
            this.nume = nume;
            this.varsta = varsta;
        }

        public String nume { get; set; }
        public int varsta { get; set; }

        public override string ToString()
        {
            return base.ToString() + " " + nume + ", " + varsta;
        }
    }
}
