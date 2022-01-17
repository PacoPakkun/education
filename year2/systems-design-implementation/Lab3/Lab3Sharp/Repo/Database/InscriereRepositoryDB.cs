using Lab2Sharp.Domain;
using Lab2Sharp.Repo.Interfaces;
using Lab2Sharp.Utils;
using log4net;
using System;
using System.Collections.Generic;
using System.Data.SQLite;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Lab2Sharp.Repo.Database
{
    class InscriereRepositoryDB<ID, E> : InscriereRepository<Pair<int, int>, Inscriere>
    {
        private string url;
        private static readonly ILog log = LogManager.GetLogger("ParticipantRepository");

        public InscriereRepositoryDB(string url)
        {
            this.url = url;
        }

        public Inscriere FindOne(Pair<int, int> id)
        {
            string sqlCom = "select * from Inscrieri where id_participant=@id1 and id_proba=@id2";
            using (var conn = new SQLiteConnection(url))
            {
                conn.Open();
                using (var cmd = new SQLiteCommand(sqlCom, conn))
                {
                    var id1 = cmd.CreateParameter();
                    id1.ParameterName = "@id1";
                    id1.Value = id.e1;
                    cmd.Parameters.Add(id1);
                    var id2 = cmd.CreateParameter();
                    id2.ParameterName = "@id2";
                    id2.Value = id.e2;
                    cmd.Parameters.Add(id2);
                    using (var data = cmd.ExecuteReader())
                    {
                        if (data.Read())
                        {
                            Inscriere p = new Inscriere(id.e1, id.e2);
                            return p;
                        }
                    }
                }
            }
            return null;
        }
        public IEnumerable<Inscriere> FindAll()
        {
            log.InfoFormat("Entering findAll");
            IList<Inscriere> list = new List<Inscriere>();
            string sqlCom = "select * from Inscrieri";
            using (var conn = new SQLiteConnection(url))
            {
                conn.Open();
                var cmd = new SQLiteCommand(sqlCom, conn);
                using (var data = cmd.ExecuteReader())
                {
                    while (data.Read())
                    {
                        int id_participant = data.GetInt32(0);
                        int id_proba = data.GetInt32(1);
                        Inscriere p = new Inscriere(id_participant, id_proba);
                        log.InfoFormat("Found {0}", p);
                        list.Add(p);
                    }
                }
            }
            log.InfoFormat("Exiting findAll");
            return list;
        }
        public Inscriere Add(Inscriere entity)
        {
            Inscriere p = entity;
            string sqlCom = "insert into Inscrieri (id_participant, id_proba) values (@id1,@id2)";
            using (var conn = new SQLiteConnection(url))
            {
                conn.Open();
                using (var cmd = new SQLiteCommand(sqlCom, conn))
                {
                    var id1 = cmd.CreateParameter();
                    id1.ParameterName = "@id1";
                    id1.Value = entity.id.e1;
                    cmd.Parameters.Add(id1);
                    var id2 = cmd.CreateParameter();
                    id2.ParameterName = "@id2";
                    id2.Value = entity.id.e2;
                    cmd.Parameters.Add(id2);
                    if (cmd.ExecuteNonQuery() > 0)
                        p = null;
                }
            }
            return p;
        }
        public Inscriere Delete(Pair<int, int> id)
        {
            Inscriere p = FindOne(id);
            string sqlCom = "delete from Inscrieri where id_participant=@id1 and id_proba=@id2";
            using (var conn = new SQLiteConnection(url))
            {
                conn.Open();
                using (var cmd = new SQLiteCommand(sqlCom, conn))
                {

                    var id1 = cmd.CreateParameter();
                    id1.ParameterName = "@id1";
                    id1.Value = id.e1;
                    cmd.Parameters.Add(id1);
                    var id2 = cmd.CreateParameter();
                    id2.ParameterName = "@id2";
                    id2.Value = id.e2;
                    cmd.Parameters.Add(id2);
                    cmd.ExecuteNonQuery();
                }
            }
            return p;
        }
        public Inscriere Update(Inscriere entity)
        {
            Inscriere p = FindOne(entity.id);
            string sqlCom = "update Inscrieri set id_participant=@id1,id_proba=@id2 where id_participant=@id1 and id_proba=@id2";
            using (var conn = new SQLiteConnection(url))
            {
                conn.Open();
                using (var cmd = new SQLiteCommand(sqlCom, conn))
                {
                    var id1 = cmd.CreateParameter();
                    id1.ParameterName = "@id1";
                    id1.Value = entity.id.e1;
                    cmd.Parameters.Add(id1);
                    var id2 = cmd.CreateParameter();
                    id2.ParameterName = "@id2";
                    id2.Value = entity.id.e2;
                    cmd.Parameters.Add(id2);
                    cmd.ExecuteNonQuery();
                }
            }
            return p;
        }

        public IEnumerable<Participant> GetInscrisi(Proba proba)
        {
            IList<Participant> list = new List<Participant>();
            string sqlCom = "select P.id, P.nume, P.varsta from Inscrieri I inner join Participanti P on I.id_participant=P.id where I.id_proba=@id";
            using (var conn = new SQLiteConnection(url))
            {
                conn.Open();
                using (var cmd = new SQLiteCommand(sqlCom, conn))
                {
                    var id = cmd.CreateParameter();
                    id.ParameterName = "@id";
                    id.Value = proba.id;
                    cmd.Parameters.Add(id);

                    using (var data = cmd.ExecuteReader())
                    {
                        while (data.Read())
                        {
                            int idd = data.GetInt32(0);
                            String nume = data.GetString(1);
                            int varsta = data.GetInt32(2);
                            Participant p = new Participant(idd, nume, varsta);
                            list.Add(p);
                        }
                    }
                }
            }

            return list;
        }
        public int nrParticipanti(Proba proba)
        {
            string sqlCom = "select count(P.id) as nr from Inscrieri I inner join Participanti P on I.id_participant=P.id where I.id_proba=@id";
            using (var conn = new SQLiteConnection(url))
            {
                conn.Open();
                using (var cmd = new SQLiteCommand(sqlCom, conn))
                {
                    var id = cmd.CreateParameter();
                    id.ParameterName = "@id";
                    id.Value = proba.id;
                    cmd.Parameters.Add(id);
                    using (var data = cmd.ExecuteReader())
                    {
                        if (data.Read())
                        {
                            return data.GetInt32(0);
                        }
                    }
                }
            }
            return 0;
        }
    }
}
