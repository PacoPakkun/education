using lab7.Domain;
using lab7.Domain.Validator;
using System;
using System.Collections.Generic;
using System.Text;

namespace lab7.Repository
{

    class JucatoriActiviRepository : InFileRepository<Tuple<int, int>, JucatorActiv>
    {

        public JucatoriActiviRepository(Validator<JucatorActiv> vali, string filename) : base(vali, filename, EntityToFile.CreateJucatorActiv)
        {

        }

    }
}
