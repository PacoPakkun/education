using lab7.Domain;
using lab7.Domain.Validator;
using System;
using System.Collections.Generic;
using System.IO;
using System.Text;

namespace lab7.Repository
{
    class MeciuriRepository : InFileRepository<int, Meci>
    {

        public MeciuriRepository(Validator<Meci> vali, string filename) : base(vali, filename, null)
        {
            loadFromFile();
        }

        private new void loadFromFile()
        {


            List<Echipa> echipe = DataReader.ReadData<Echipa>("..\\..\\..\\Data\\echipe.txt", EntityToFile.CreateEchipa);

            using (StreamReader sr = new StreamReader(fileName))
            {
                string line;
                while ((line = sr.ReadLine()) != null)
                {
                    string[] fields = line.Split(',');
                    int id = Int32.Parse(fields[0]);
                    Echipa echipa1 = echipe.Find(x => x.ID.Equals(Int32.Parse(fields[1])));
                    Echipa echipa2 = echipe.Find(x => x.ID.Equals(Int32.Parse(fields[2])));
                    DateTime data = DateTime.Parse(fields[3]);
                    Meci meci = new Meci()
                    {
                        ID = id,
                        echipa1 = echipa1,
                        echipa2 = echipa2,
                        data = data
                    };
                    base.entities[meci.ID] = meci;
                }
            }
        }

    }
}
