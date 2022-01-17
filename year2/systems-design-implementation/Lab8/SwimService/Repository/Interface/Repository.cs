using SwimModel.Utils;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace SwimServer.Repository.Interface
{
    public interface Repository<ID, E> where E : Entity<ID>
    {
        E FindOne(ID id);
        IEnumerable<E> FindAll();
        E Add(E entity);
        E Delete(ID id);
        E Update(E entity);
    }
}
