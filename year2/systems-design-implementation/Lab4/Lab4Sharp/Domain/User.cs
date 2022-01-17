using Lab2Sharp.Utils;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Lab2Sharp.Domain
{
    public class User : Entity<int>
    {
        public User(int id, string username, string password)
        {
            base.id = id;
            this.username = username;
            this.password = password;
        }

        public String username { get; set; }
        public String password { get; set; }

        public override string ToString()
        {
            return base.ToString() + " " + username + ", " + password;
        }
    }
}
