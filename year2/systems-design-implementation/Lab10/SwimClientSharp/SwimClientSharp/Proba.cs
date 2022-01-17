using System;

namespace SwimClientSharp
{
    public class Proba
    {
        public Proba()
        {
        }

        public Proba(int id, int distanta, string stil)
        {
            this.id = id;
            this.distanta = distanta;
            this.stil = stil;
        }
        
        public Proba(int distanta, string stil)
        {
            this.distanta = distanta;
            this.stil = stil;
        }

        public int id { get; set; }
        public String stil { get; set; }
        public int distanta { get; set; }

        public override string ToString()
        {
            return "[" + id + "] " + stil + " " + distanta + "m";
        }
    }
}