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
    public class InscriereRepositoryDB : InscriereRepository
    {
        private string url;
        private static readonly ILog log = LogManager.GetLogger("ParticipantRepository");

        public InscriereRepositoryDB(string url)
        {
            this.url = url;
        }

        public Inscriere FindOne(Pair<int, int> id)
        {
            string sqlCom = "select P.nume, P.varsta, PR.stil, PR.distanta from Participanti P inner join Inscrieri I on P.id=I.id_participant inner join Probe PR on PR.id=I.id_proba where I.id_participant=@id1 and I.id_proba=@id2";
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
                            String nume = data.GetString(0);
                            int varsta = data.GetInt32(1);
                            Participant participant = new Participant(id.e1, nume, varsta);
                            String stil = data.GetString(2);
                            int distanta = data.GetInt32(3);
                            Proba proba = new Proba(id.e2, distanta, stil);
                            Inscriere p = new Inscriere(id.e1, id.e2, participant, proba);
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
            string sqlCom = "select I.id_participant, P.nume, P.varsta, I.id_proba, PR.stil, PR.distanta from Participanti P inner join Inscrieri I on P.id=I.id_participant inner join Probe PR on PR.id=I.id_proba";
            using (var conn = new SQLiteConnection(url))
            {
                conn.Open();
                var cmd = new SQLiteCommand(sqlCom, conn);
                using (var data = cmd.ExecuteReader())
                {
                    while (data.Read())
                    {
                        int id1 = data.GetInt32(0);
                        String nume = data.GetString(1);
                        int varsta = data.GetInt32(2);
                        Participant participant = new Participant(id1, nume, varsta);
                        int id2 = data.GetInt32(3);
                        String stil = data.GetString(4);
                        int distanta = data.GetInt32(5);
                        Proba proba = new Proba(id2, distanta, stil);
                        Inscriere p = new Inscriere(id1, id2, participant, proba);
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
        public IEnumerable<Proba> GetInscrieri(Participant participant)
        {
            IList<Proba> list = new List<Proba>();
            string sqlCom = "select P.id, P.distanta, P.stil from Inscrieri I inner join Probe P on I.id_proba=P.id where I.id_participant=@id";
            using (var conn = new SQLiteConnection(url))
            {
                conn.Open();
                using (var cmd = new SQLiteCommand(sqlCom, conn))
                {
                    var id = cmd.CreateParameter();
                    id.ParameterName = "@id";
                    id.Value = participant.id;
                    cmd.Parameters.Add(id);

                    using (var data = cmd.ExecuteReader())
                    {
                        while (data.Read())
                        {
                            int idd = data.GetInt32(0);
                            int distanta = data.GetInt32(1);
                            String stil = data.GetString(2);
                            Proba p = new Proba(idd, distanta, stil);
                            list.Add(p);
                        }
                    }
                }
            }

            return list;
        }
        public int NrParticipanti(Proba proba)
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
