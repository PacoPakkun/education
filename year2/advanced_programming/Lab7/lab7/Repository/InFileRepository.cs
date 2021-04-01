using lab7.Domain;
using lab7.Domain.Validator;
using System;
using System.Collections.Generic;
using System.Text;

namespace lab7.Repository
{
    public delegate E CreateEntity<E>(string line);

    abstract class InFileRepository<ID, E> : InMemoryRepository<ID, E> where E : Entity<ID>
    {
        protected string fileName;
        protected CreateEntity<E> createEntity;

        public InFileRepository(Validator<E> vali, String fileName, CreateEntity<E> createEntity) : base(vali)
        {
            this.fileName = fileName;
            this.createEntity = createEntity;
            if (createEntity != null)
                loadFromFile();
        }

        protected virtual void loadFromFile()
        {
            List<E> list = DataReader.ReadData(fileName, createEntity);
            list.ForEach(x => entities[x.ID] = x);
        }


    }
}
