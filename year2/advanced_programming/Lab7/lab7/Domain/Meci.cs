using System;
using System.Collections.Generic;
using System.Text;

namespace lab7.Domain
{
    class Meci : Entity<int>
    {
        public Echipa echipa1 { get; set; }
        public Echipa echipa2 { get; set; }
        public DateTime data { get; set; }
        public override string ToString()
        {
            return ID + ": " + echipa1 + " vs " + echipa2 + " (" + data + ")";
        }
    }
}
