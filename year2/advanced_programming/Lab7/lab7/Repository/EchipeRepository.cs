using lab7.Domain;
using lab7.Domain.Validator;
using System;
using System.Collections.Generic;
using System.Text;

namespace lab7.Repository
{
    class EchipeRepository : InFileRepository<int, Echipa>
    {

        public EchipeRepository(Validator<Echipa> vali, string filename) : base(vali, filename, EntityToFile.CreateEchipa)
        {

        }

    }
}
