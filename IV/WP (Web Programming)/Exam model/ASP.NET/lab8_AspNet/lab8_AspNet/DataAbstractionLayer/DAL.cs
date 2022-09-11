using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

using lab8_AspNet.Models;
using MySql.Data.MySqlClient;

namespace lab8_AspNet.DataAbstractionLayer
{
    public class DAL
    {
        public int AuthenticateUser(User user)
        {
            int id = -1;

            MySqlConnection connection;
            string connectionString = "server=localhost;uid=root;pwd=;database=exam_model;";

            try
            {
                connection = new MySqlConnection();
                connection.ConnectionString = connectionString;
                connection.Open();

                MySqlCommand command = new MySqlCommand();
                command.Connection = connection;
                command.CommandText = String.Format("SELECT * FROM users WHERE username='{0}' AND password='{1}'", user.name, user.password);

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

        public List<Asset> GetUserAssets(int userId)
        {
            MySqlConnection connection;
            string connectionString = "server=localhost;uid=root;pwd=;database=exam_model;";

            List<Asset> assets = new List<Asset>();

            try
            {
                connection = new MySqlConnection();
                connection.ConnectionString = connectionString;
                connection.Open();

                MySqlCommand command = new MySqlCommand();
                command.Connection = connection;
                command.CommandText = String.Format("SELECT * FROM assets WHERE userid={0}", userId);

                MySqlDataReader reader = command.ExecuteReader();
                while (reader.Read())
                {
                    Asset asset = new Asset
                    {
                        id = reader.GetInt32("id"),
                        userId = reader.GetInt32("userid"),
                        name = reader.GetString("name"),
                        description = reader.GetString("description"),
                        value = reader.GetInt32("value"),
                    };

                    assets.Add(asset);
                }
                reader.Close();
            }
            catch (MySqlException ex)
            {
                Console.Write(ex.StackTrace);
            }

            return assets;
        }

        public bool AddUserAssets(List<Asset> assets)
        {
            MySqlConnection connection;
            string connectionString = "server=localhost;uid=root;pwd=;database=exam_model;";

            try
            {
                connection = new MySqlConnection();
                connection.ConnectionString = connectionString;
                connection.Open();

                MySqlCommand command = new MySqlCommand();
                command.Connection = connection;
                command.CommandText = String.Format("INSERT INTO assets(userid, name, description, value) VALUES");
                foreach(Asset asset in assets) {
                    command.CommandText += String.Format("({0}, '{1}', '{2}', {3}),",
                        asset.userId, asset.name, asset.description, asset.value);
                }
                command.CommandText = command.CommandText.Remove(command.CommandText.Length - 1);

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