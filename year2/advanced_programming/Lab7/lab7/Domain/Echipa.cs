using System;
using System.Collections.Generic;
using System.Text;

namespace lab7.Domain
{
    class Echipa : Entity<int>
    {
        public String nume { get; set; }
        public override string ToString()
        {
            return nume;
        }
    }
}
