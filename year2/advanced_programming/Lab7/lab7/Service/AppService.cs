using lab7.Domain;
using lab7.Repository;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace lab7.Service
{
    class AppService
    {
        private Repository<int, Echipa> echipe;
        private Repository<int, Jucator> jucatori;
        private Repository<int, Meci> meciuri;
        private Repository<Tuple<int, int>, JucatorActiv> jucatoriActivi;

        public AppService(Repository<int, Echipa> echipe, Repository<int, Jucator> jucatori, Repository<int, Meci> meciuri, Repository<Tuple<int, int>, JucatorActiv> jucatoriActivi)
        {
            this.echipe = echipe;
            this.jucatori = jucatori;
            this.meciuri = meciuri;
            this.jucatoriActivi = jucatoriActivi;
        }

        public List<Echipa> FindAllEchipe()
        {
            return echipe.FindAll().ToList();
        }
        public List<Jucator> FindAllJucatori()
        {
            return jucatori.FindAll().ToList();
        }
        public List<Meci> FindAllMeciuri()
        {
            return meciuri.FindAll().ToList();
        }
        public List<JucatorActiv> FindAllJucatoriActivi()
        {
            return jucatoriActivi.FindAll().ToList();
        }

        // Sa se afiseze toti jucatorii unei echipe date
        public List<Jucator> FindJucatori(String nume)
        {
            IEnumerable<Jucator> j = from jucator in jucatori.FindAll()
                                     where jucator.echipa.nume.Equals(nume)
                                     select jucator;
            return j.ToList();
        }

        // Sa se afiseze toti jucatorii activi ai unei echipe de la un anumit meci
        public List<JucatorActiv> FindJucatoriActivi(String echipa, int meci)
        {
            IEnumerable<JucatorActiv> jA = from jucator in jucatoriActivi.FindAll()
                                           where jucator.ID.Item1.Equals(meci)
                                           join j in jucatori.FindAll() on jucator.ID.Item2 equals j.ID
                                           join e in echipe.FindAll() on j.echipa.ID equals e.ID
                                           where e.nume.Equals(echipa)
                                           select jucator;
            return jA.ToList();
        }

        // Sa se afiseze toate meciurile dintr-o anumita perioada calendaristica 
        public List<Meci> FindMeciuri(DateTime fro, DateTime to)
        {
            IEnumerable<Meci> m = from meci in meciuri.FindAll()
                                  where meci.data.CompareTo(fro) > 0 && meci.data.CompareTo(to) < 0
                                  select meci;
            return m.ToList();
        }

        // Sa se determine si sa se afiseze scorul de la un anumit meci
        public Tuple<Double, Double> FindScor(int id)
        {
            var teams = (from meci in meciuri.FindAll()
                         where meci.ID.Equals(id)
                         select new
                         {
                             echipa1 = meci.echipa1,
                             echipa2 = meci.echipa2
                         }).ElementAt(0);
            Double scor1 = (from jA in jucatoriActivi.FindAll()
                            where jA.ID.Item1.Equals(id)
                            join j in jucatori.FindAll() on jA.ID.Item2 equals j.ID
                            join e in echipe.FindAll() on j.echipa.ID equals e.ID
                            where e.ID.Equals(teams.echipa1.ID)
                            select jA).Sum(jA => jA.nrPuncte);
            Double scor2 = (from jA in jucatoriActivi.FindAll()
                            where jA.ID.Item1.Equals(id)
                            join j in jucatori.FindAll() on jA.ID.Item2 equals j.ID
                            join e in echipe.FindAll() on j.echipa.ID equals e.ID
                            where e.ID.Equals(teams.echipa2.ID)
                            select jA).Sum(jA => jA.nrPuncte);
            return new Tuple<Double, Double>(scor1, scor2);
        }
    }
}
