using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Runtime;

using lab8_AspNet.Models;
using MySql.Data.MySqlClient;

namespace lab8_AspNet.DataAbstractionLayer
{
    public class DAL
    {
        public int AuthenticatePerson(Person person)
        {
            int id = -1;

            MySqlConnection connection;
            string connectionString = "server=localhost;uid=root;pwd=;database=exam;";

            try
            {
                connection = new MySqlConnection();
                connection.ConnectionString = connectionString;
                connection.Open();

                MySqlCommand command = new MySqlCommand();
                command.Connection = connection;
                command.CommandText = String.Format("SELECT * FROM persons WHERE name='{0}'", person.name);

                MySqlDataReader reader = command.ExecuteReader();
                if (reader.Read())
                {
                    id = reader.GetInt32("id");
                }
                reader.Close();
            }
            catch (MySqlException ex)
            {
                Console.Write(ex.ToString());
            }

            return id;
        }

        public List<Channel> GetPersonChannels(int ownerId)
        {
            MySqlConnection connection;
            string connectionString = "server=localhost;uid=root;pwd=;database=exam;";

            List<Channel> channels = new List<Channel>();

            try
            {
                connection = new MySqlConnection();
                connection.ConnectionString = connectionString;
                connection.Open();

                MySqlCommand command = new MySqlCommand();
                command.Connection = connection;
                command.CommandText = String.Format("SELECT * FROM channels WHERE ownerid={0}", ownerId);

                MySqlDataReader reader = command.ExecuteReader();
                while (reader.Read())
                {
                    Channel channel = new Channel
                    {
                        id = reader.GetInt32("id"),
                        ownerId = reader.GetInt32("ownerid"),
                        name = reader.GetString("name"),
                        description = reader.GetString("description"),
                        subscribers = reader.GetString("subscribers"),
                    };

                    channels.Add(channel);
                }
                reader.Close();
            }
            catch (MySqlException ex)
            {
                Console.Write(ex.StackTrace);
            }

            return channels;
        }

        public List<Channel> GetPersonSubscriptions(int id, string name)
        {
            MySqlConnection connection;
            string connectionString = "server=localhost;uid=root;pwd=;database=exam;";

            List<Channel> channels = new List<Channel>();

            try
            {
                connection = new MySqlConnection();
                connection.ConnectionString = connectionString;
                connection.Open();

                MySqlCommand command = new MySqlCommand();
                command.Connection = connection;
                command.CommandText = String.Format("SELECT * FROM channels WHERE LOCATE('{0}', subscribers)", name);// LIKE '%{{1}}%'", id, name);

                MySqlDataReader reader = command.ExecuteReader();
                while (reader.Read())
                {
                    Channel channel = new Channel
                    {
                        id = reader.GetInt32("id"),
                        ownerId = reader.GetInt32("ownerid"),
                        name = reader.GetString("name"),
                        description = reader.GetString("description"),
                        subscribers = reader.GetString("subscribers"),
                    };

                    channels.Add(channel);
                }
                reader.Close();
            }
            catch (MySqlException ex)
            {
                Console.Write(ex.StackTrace);
            }

            return channels;
        }

        public bool SubscribePersonToChannel(int id, string name)
        {
            MySqlConnection connection;
            string connectionString = "server=localhost;uid=root;pwd=;database=exam;";

            try
            {
                connection = new MySqlConnection();
                connection.ConnectionString = connectionString;
                connection.Open();

                MySqlCommand command = new MySqlCommand();
                command.Connection = connection;
                command.CommandText = "UPDATE channels SET subscribers=CONCAT(subscribers, ';', '" + name + "', ':', '" +
                    DateTime.UtcNow.Date.ToString("yyyy-MM-dd") + "') WHERE id=" + id; 
                command.ExecuteNonQuery();

                return true;
            }
            catch (MySqlException ex)
            {
                Console.Write(ex.ToString());
                return false;
            }
        }
    }
}