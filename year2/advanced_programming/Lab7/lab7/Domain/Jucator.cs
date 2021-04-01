using System;
using System.Collections.Generic;
using System.Text;

namespace lab7.Domain
{
    class Jucator : Elev
    {
        public Echipa echipa { get; set; }
        public override string ToString()
        {
            return base.ToString() + " (" + echipa + ")";
        }
    }
}
