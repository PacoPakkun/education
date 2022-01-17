using log4net;
using SwimModel.Domain;
using SwimServer.Repository.Interface;
using System;
using System.Collections.Generic;
using System.Data.SQLite;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace SwimServer.Repository.Database
{
    public class UserRepositoryDB : UserRepository
    {
        private string url;
        private static readonly ILog log = LogManager.GetLogger("ParticipantRepository");

        public UserRepositoryDB(string url)
        {
            this.url = url;
        }

        public User FindOne(int id)
        {
            string sqlCom = "select * from Useri where id=@id";
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
                            String username = data.GetString(1);
                            String password = data.GetString(2);
                            User p = new User(id, username, password);
                            return p;
                        }
                    }
                }
            }
            return null;
        }
        public IEnumerable<User> FindAll()
        {
            log.InfoFormat("Entering findAll");
            IList<User> list = new List<User>();
            string sqlCom = "select * from Useri";
            using (var conn = new SQLiteConnection(url))
            {
                conn.Open();
                var cmd = new SQLiteCommand(sqlCom, conn);
                using (var data = cmd.ExecuteReader())
                {
                    while (data.Read())
                    {
                        int id = data.GetInt32(0);
                        String username = data.GetString(1);
                        String password = data.GetString(2);
                        User p = new User(id, username, password);
                        log.InfoFormat("Found {0}", p);
                        list.Add(p);
                    }
                }
            }
            log.InfoFormat("Exiting findAll");
            return list;
        }
        public User Add(User entity)
        {
            User p = entity;
            string sqlCom = "insert into Useri (username, password) values (@username,@password)";
            using (var conn = new SQLiteConnection(url))
            {
                conn.Open();
                using (var cmd = new SQLiteCommand(sqlCom, conn))
                {
                    var username = cmd.CreateParameter();
                    username.ParameterName = "@username";
                    username.Value = entity.username;
                    cmd.Parameters.Add(username);
                    var password = cmd.CreateParameter();
                    password.ParameterName = "@password";
                    password.Value = entity.password;
                    cmd.Parameters.Add(password);
                    if (cmd.ExecuteNonQuery() > 0)
                        p = null;
                }
            }
            return p;
        }
        public User Delete(int id)
        {
            User p = FindOne(id);
            string sqlCom = "delete from Useri where id=@id";
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
        public User Update(User entity)
        {
            User p = FindOne(entity.id);
            string sqlCom = "update Useri set username=@username,password=@password where id=@id";
            using (var conn = new SQLiteConnection(url))
            {
                conn.Open();
                using (var cmd = new SQLiteCommand(sqlCom, conn))
                {
                    var id = cmd.CreateParameter();
                    id.ParameterName = "@id";
                    id.Value = entity.id;
                    cmd.Parameters.Add(id);
                    var username = cmd.CreateParameter();
                    username.ParameterName = "@username";
                    username.Value = entity.username;
                    cmd.Parameters.Add(username);
                    var password = cmd.CreateParameter();
                    password.ParameterName = "@password";
                    password.Value = entity.password;
                    cmd.Parameters.Add(password);
                    cmd.ExecuteNonQuery();
                }
            }
            return p;
        }
    }
}
