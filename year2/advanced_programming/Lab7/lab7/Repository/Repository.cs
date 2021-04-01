using lab7.Domain;
using System;
using System.Collections.Generic;
using System.Text;

namespace lab7.Repository
{
    interface Repository<ID, E> where E : Entity<ID>
    {
        E FindOne(ID id);
        IEnumerable<E> FindAll();
        E Save(E entity);
        E Delete(ID id);
        E Update(E entity);
    }
}
