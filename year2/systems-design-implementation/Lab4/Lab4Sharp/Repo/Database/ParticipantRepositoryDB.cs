using Lab2Sharp.Domain;
using Lab2Sharp.Repo.Interfaces;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Data.SQLite;
using log4net;
using Lab2Sharp.Domain.Validator;

namespace Lab2Sharp.Repo.Database
{
    public class ParticipantRepositoryDB: ParticipantRepository
    {
        private string url;
        private static readonly ILog log = LogManager.GetLogger("ParticipantRepository");
        private ParticipantValidator validator;

        public ParticipantRepositoryDB(string url, ParticipantValidator validator)
        {
            log.Info("Creating ParticipantRepository");
            this.url = url;
            this.validator = validator;
        }

        public Participant FindOne(int id)
        {
            log.InfoFormat("Entering findOne with value {0}", id);
            string sqlCom = "select * from Participanti where id=@id";
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
                            String nume = data.GetString(1);
                            int varsta = data.GetInt32(2);
                            Participant p = new Participant(id, nume, varsta);
                            log.InfoFormat("Exiting findOne with value {0}", p);
                            return p;
                        }
                    }
                }
            }
            log.InfoFormat("Exiting findOne with value {0}", null);
            return null;
        }
        public IEnumerable<Participant> FindAll()
        {
            log.InfoFormat("Entering findAll");
            IList<Participant> list = new List<Participant>();
            string sqlCom = "select * from Participanti";
            using (var conn = new SQLiteConnection(url))
            {
                conn.Open();
                var cmd = new SQLiteCommand(sqlCom, conn);
                using (var data = cmd.ExecuteReader())
                {
                    while (data.Read())
                    {
                        int id = data.GetInt32(0);
                        String nume = data.GetString(1);
                        int varsta = data.GetInt32(2);
                        Participant p = new Participant(id, nume, varsta);
                        log.InfoFormat("Found {0}", p);
                        list.Add(p);
                    }
                }
            }
            log.InfoFormat("Exiting findAll");
            return list;
        }
        public Participant Add(Participant entity)
        {
            validator.validate(entity);
            Participant p = null;
            string sqlCom = "insert into Participanti (nume, varsta) values (@nume,@varsta)"; 
            using (var conn = new SQLiteConnection(url)) { 
                conn.Open();
                using (var cmd = new SQLiteCommand(sqlCom, conn))
                {
                    var nume = cmd.CreateParameter();
                    nume.ParameterName = "@nume";
                    nume.Value = entity.nume;
                    cmd.Parameters.Add(nume);
                    var varsta = cmd.CreateParameter();
                    varsta.ParameterName = "@varsta";
                    varsta.Value = entity.varsta;
                    cmd.Parameters.Add(varsta);
                    if (cmd.ExecuteNonQuery() > 0)
                        p = entity;
                }
                sqlCom = "select max(id) as id from Participanti";
                using (var cmd = new SQLiteCommand(sqlCom, conn))
                {
                    using (var data = cmd.ExecuteReader())
                    {
                        if (data.Read())
                        {
                            p.id = data.GetInt32(0);
                        }
                    }
                }
            }
            return p;
        }
        public Participant Delete(int id)
        {
            Participant p = FindOne(id);
            string sqlCom = "delete from Participanti where id=@id";
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
        public Participant Update(Participant entity)
        {
            validator.validate(entity);
            Participant p = FindOne(entity.id);
            string sqlCom = "update Participanti set nume=@nume,varsta=@varsta where id=@id";
            using (var conn = new SQLiteConnection(url))
            {
                conn.Open();
                using (var cmd = new SQLiteCommand(sqlCom, conn))
                {
                    var id = cmd.CreateParameter();
                    id.ParameterName = "@id";
                    id.Value = entity.id;
                    cmd.Parameters.Add(id);
                    var nume = cmd.CreateParameter();
                    nume.ParameterName = "@nume";
                    nume.Value = entity.nume;
                    cmd.Parameters.Add(nume);
                    var varsta = cmd.CreateParameter();
                    varsta.ParameterName = "@varsta";
                    varsta.Value = entity.varsta;
                    cmd.Parameters.Add(varsta);
                    cmd.ExecuteNonQuery();
                }
            }
            return p;
        }
    }
}
