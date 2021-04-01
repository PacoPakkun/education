using System;
using System.Collections.Generic;
using System.Text;

namespace lab7.Domain
{
    enum Tip
    {
        Rezerva, Participant
    }
    class JucatorActiv : Entity<Tuple<int, int>>
    {
        public int nrPuncte { get; set; }
        public Tip tip { get; set; }
        public override string ToString()
        {
            return ID + ": " + tip + ", " + nrPuncte;
        }
    }
}
