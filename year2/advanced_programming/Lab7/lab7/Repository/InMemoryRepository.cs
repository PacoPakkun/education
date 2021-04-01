using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using lab7.Domain;
using lab7.Domain.Validator;

namespace lab7.Repository
{
    class InMemoryRepository<ID, E> : Repository<ID, E> where E : Entity<ID>
    {
        protected Validator<E> vali;

        protected IDictionary<ID, E> entities = new Dictionary<ID, E>();

        public InMemoryRepository(Validator<E> vali)
        {
            this.vali = vali;
        }

        public E Delete(ID id)
        {
            throw new NotImplementedException();
        }

        public IEnumerable<E> FindAll()
        {
            return entities.Values.ToList<E>();
        }

        public E FindOne(ID id)
        {
            throw new NotImplementedException();
        }

        public E Save(E entity)
        {
            if (entity == null)
                throw new ArgumentNullException("entity must not be null");
            this.vali.Validate(entity);
            if (this.entities.ContainsKey(entity.ID))
            {
                return entity;
            }
            this.entities[entity.ID] = entity;
            return default(E);
        }

        public E Update(E entity)
        {
            throw new NotImplementedException();
        }
    }
}
