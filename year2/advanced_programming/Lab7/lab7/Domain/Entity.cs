using System;
using System.Collections.Generic;
using System.Text;

namespace lab7.Domain
{
    public class Entity<TID>
    {
        public TID ID { get; set; }
    }
}
