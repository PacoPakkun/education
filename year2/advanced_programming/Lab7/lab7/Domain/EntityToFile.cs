using System;
using System.Collections.Generic;
using System.Text;

namespace lab7.Domain
{
    class EntityToFile
    {
        public static Echipa CreateEchipa(string line)
        {
            string[] fields = line.Split(',');
            Echipa echipa = new Echipa()
            {
                ID = Int32.Parse(fields[0]),
                nume = fields[1]
            };
            return echipa;
        }
        public static JucatorActiv CreateJucatorActiv(string line)
        {
            string[] fields = line.Split(',');
            JucatorActiv jucator = new JucatorActiv()
            {
                ID = new Tuple<int, int>(Int32.Parse(fields[0]), Int32.Parse(fields[1])),
                tip = (Tip)Enum.Parse(typeof(Tip), fields[2]),
                nrPuncte = Int32.Parse(fields[3])
            };
            return jucator;
        }
    }
}
