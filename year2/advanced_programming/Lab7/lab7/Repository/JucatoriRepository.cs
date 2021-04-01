using lab7.Domain;
using lab7.Domain.Validator;
using System;
using System.Collections.Generic;
using System.IO;
using System.Text;

namespace lab7.Repository
{
    class JucatoriRepository : InFileRepository<int, Jucator>
    {

        public JucatoriRepository(Validator<Jucator> vali, string filename) : base(vali, filename, null)
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
                    string nume = fields[1];
                    string scoala = fields[2];
                    Echipa e = echipe.Find(x => x.ID.Equals(Int32.Parse(fields[3])));
                    Jucator jucator = new Jucator()
                    {
                        ID = id,
                        nume = nume,
                        scoala = scoala,
                        echipa = e
                    };
                    base.entities[jucator.ID] = jucator;
                }
            }
        }

    }

}
