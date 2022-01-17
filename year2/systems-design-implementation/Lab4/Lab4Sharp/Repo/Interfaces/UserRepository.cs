using Lab2Sharp.Domain;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Lab2Sharp.Repo.Interfaces
{
    public interface UserRepository : Repository<int, User>
    {
    }
}
