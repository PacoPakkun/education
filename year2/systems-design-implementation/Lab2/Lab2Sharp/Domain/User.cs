using Lab2Sharp.Utils;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Lab2Sharp.Domain
{
    class User : Entity<int>
    {

        public String username { get; set; }
        public String password { get; set; }

        public override string ToString()
        {
            return base.ToString() + " " + username + ", " + password;
        }
    }
}
