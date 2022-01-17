using SwimModel.Utils;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace SwimModel.Domain
{
    [Serializable]
    public class User : Entity<int>
    {
        public User(int id, string username, string password)
        {
            base.id = id;
            this.username = username;
            this.password = password;
        }

        public User(string username, string password)
        {
            base.id = -1;
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
