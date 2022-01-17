using Lab2Sharp.Domain;
using Lab2Sharp.Repo.Interfaces;
using log4net;
using System;
using System.Collections.Generic;
using System.Data.SQLite;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Lab2Sharp.Repo.Database
{
    class ProbaRepositoryDB<ID, E> : ProbaRepository<int, Proba>
    {
        private string url;
        private static readonly ILog log = LogManager.GetLogger("ParticipantRepository");

        public ProbaRepositoryDB(string url)
        {
            this.url = url;
        }

        public Proba FindOne(int id)
        {
            string sqlCom = "select * from Probe where id=@id";
            using (var conn = new SQLiteConnection(url))
            {
                conn.Open();
                using (var cmd = new SQLiteCommand(sqlCom, conn))
                {
                    var idd = cmd.CreateParameter();
                    idd.ParameterName = "@id";
                    idd.Value = id;
                    cmd.Parameters.Add(idd);
                    using (var data = cmd.ExecuteReader())
                    {
                        if (data.Read())
                        {
                            String stil = data.GetString(2);
                            int distanta = data.GetInt32(1);
                            Proba p = new Proba(id, distanta, stil);
                            return p;
                        }
                    }
                }
            }
            return null;
        }
        public IEnumerable<Proba> FindAll()
        {
            log.InfoFormat("Entering findAll");
            IList<Proba> list = new List<Proba>();
            string sqlCom = "select * from Probe";
            using (var conn = new SQLiteConnection(url))
            {
                conn.Open();
                var cmd = new SQLiteCommand(sqlCom, conn);
                using (var data = cmd.ExecuteReader())
                {
                    while (data.Read())
                    {
                        int id = data.GetInt32(0);
                        String stil = data.GetString(2);
                        int distanta = data.GetInt32(1);
                        Proba p = new Proba(id, distanta, stil);
                        log.InfoFormat("Found {0}", p);
                        list.Add(p);
                    }
                }
            }
            log.InfoFormat("Exiting findAll");
            return list;
        }
        public Proba Add(Proba entity)
        {
            Proba p = entity;
            string sqlCom = "insert into Probe (distanta, stil) values (@distanta,@stil)";
            using (var conn = new SQLiteConnection(url))
            {
                conn.Open();
                using (var cmd = new SQLiteCommand(sqlCom, conn))
                {
                    var distanta = cmd.CreateParameter();
                    distanta.ParameterName = "@distanta";
                    distanta.Value = entity.distanta;
                    cmd.Parameters.Add(distanta);
                    var stil = cmd.CreateParameter();
                    stil.ParameterName = "@stil";
                    stil.Value = entity.stil;
                    cmd.Parameters.Add(stil);
                    if (cmd.ExecuteNonQuery() > 0)
                        p = null;
                }
            }
            return p;
        }
        public Proba Delete(int id)
        {
            Proba p = FindOne(id);
            string sqlCom = "delete from Probe where id=@id";
            using (var conn = new SQLiteConnection(url))
            {
                conn.Open();
                using (var cmd = new SQLiteCommand(sqlCom, conn))
                {
                    var idd = cmd.CreateParameter();
                    idd.ParameterName = "@id";
                    idd.Value = id;
                    cmd.Parameters.Add(idd);
                    cmd.ExecuteNonQuery();
                }
            }
            return p;
        }
        public Proba Update(Proba entity)
        {
            Proba p = FindOne(entity.id);
            string sqlCom = "update Probe set distanta=@distanta,stil=@stil where id=@id";
            using (var conn = new SQLiteConnection(url))
            {
                conn.Open();
                using (var cmd = new SQLiteCommand(sqlCom, conn))
                {
                    var id = cmd.CreateParameter();
                    id.ParameterName = "@id";
                    id.Value = entity.id;
                    cmd.Parameters.Add(id);
                    var distanta = cmd.CreateParameter();
                    distanta.ParameterName = "@distanta";
                    distanta.Value = entity.distanta;
                    cmd.Parameters.Add(distanta);
                    var stil = cmd.CreateParameter();
                    stil.ParameterName = "@stil";
                    stil.Value = entity.stil;
                    cmd.Parameters.Add(stil);
                    cmd.ExecuteNonQuery();
                }
            }
            return p;
        }
    }
}
