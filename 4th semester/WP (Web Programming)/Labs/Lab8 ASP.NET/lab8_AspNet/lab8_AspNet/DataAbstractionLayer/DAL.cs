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
            string connectionString = "server=localhost;uid=root;pwd=;database=wp;";

            try
            {
                connection = new MySqlConnection();
                connection.ConnectionString = connectionString;
                connection.Open();

                MySqlCommand command = new MySqlCommand();
                command.Connection = connection;
                command.CommandText = String.Format("SELECT * FROM users WHERE name='{0}' AND password='{1}'", user.name, user.password);

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

        internal void AddSession(Session session)
        {
            MySqlConnection connection;
            string connectionString = "server=localhost;uid=root;pwd=;database=wp;";

            try
            {
                connection = new MySqlConnection();
                connection.ConnectionString = connectionString;
                connection.Open();

                MySqlCommand command = new MySqlCommand();
                command.Connection = connection;
                command.CommandText = String.Format("INSERT INTO sessions(userid, start, time) VALUES ({0}, {1}, {2})", session.userId, session.startTime, session.sessionTime);
                command.ExecuteNonQuery();
            }
            catch (MySqlException ex)
            {
                Console.Write(ex.ToString());
            }
        }

        public List<Book> GetBooksFilteredByCathegory(string cathegory="")
        {
            MySqlConnection connection;
            string connectionString = "server=localhost;uid=root;pwd=;database=wp;";

            List<Book> books = new List<Book>();

            try
            {
                connection = new MySqlConnection();
                connection.ConnectionString = connectionString;
                connection.Open();

                MySqlCommand command = new MySqlCommand();
                command.Connection = connection;
                command.CommandText = "SELECT * FROM products";
                if (cathegory != null && cathegory.Length > 0)
                {
                    command.CommandText += String.Format(" WHERE cathegory='{0}'", cathegory);
                }

                MySqlDataReader reader = command.ExecuteReader();
                while (reader.Read())
                {
                    Book book = new Book
                    {
                        id = reader.GetInt32("id"),
                        title = reader.GetString("title"),
                        author = reader.GetString("author"),
                        cathegory = reader.GetString("cathegory"),
                        price = reader.GetFloat("price")
                    };

                    books.Add(book);
                }
                reader.Close();
            }
            catch (MySqlException ex)
            {
                Console.Write(ex.StackTrace);
            }

            return books;
        }

        public List<CartItem> GetCartItems()
        {
            MySqlConnection connection;
            string connectionString = "server=localhost;uid=root;pwd=;database=wp;";

            List<CartItem> cartItems = new List<CartItem>();

            try
            {
                connection = new MySqlConnection();
                connection.ConnectionString = connectionString;
                connection.Open();

                MySqlCommand command = new MySqlCommand();
                command.Connection = connection;
                command.CommandText = "SELECT * FROM cart";

                MySqlDataReader reader = command.ExecuteReader();
                while (reader.Read())
                {
                    CartItem cartItem = new CartItem
                    {
                        id = reader.GetInt32("id"),
                        bookId = reader.GetInt32("pid"),
                        quantity = reader.GetInt32("quantity")
                    };

                    cartItems.Add(cartItem);
                }
                reader.Close();
            }
            catch (MySqlException ex)
            {
                Console.Write(ex.StackTrace);
            }

            return cartItems;
        }

        public void AddCartItem(CartItem cartItem)
        {
            MySqlConnection connection;
            string connectionString = "server=localhost;uid=root;pwd=;database=wp;";

            try
            {
                connection = new MySqlConnection();
                connection.ConnectionString = connectionString;
                connection.Open();

                MySqlCommand command = new MySqlCommand();
                command.Connection = connection;
                command.CommandText = String.Format("SELECT * FROM cart WHERE pid={0}", cartItem.bookId);

                MySqlDataReader reader = command.ExecuteReader();
                if (reader.Read())
                {
                    command.CommandText = String.Format(
                        "UPDATE cart SET pid={1}, quantity={2} WHERE id={0}", reader.GetInt32("id"), cartItem.bookId, cartItem.quantity + reader.GetInt32("quantity"));
                } 
                else
                {
                    command.CommandText = String.Format(
                        "INSERT INTO cart(pid, quantity) VALUES ({0}, {1})", cartItem.bookId, cartItem.quantity);
                }
                reader.Close();
                command.ExecuteNonQuery();
            }
            catch (MySqlException ex)
            {
                Console.Write(ex.ToString());
            }
        }

        public void DeleteCartItem(int cartItemId)
        {
            MySqlConnection connection;
            string connectionString = "server=localhost;uid=root;pwd=;database=wp;";

            try
            {
                connection = new MySqlConnection();
                connection.ConnectionString = connectionString;
                connection.Open();

                MySqlCommand command = new MySqlCommand();
                command.Connection = connection;
                command.CommandText = String.Format("DELETE FROM cart WHERE id={0}", cartItemId);

                command.ExecuteNonQuery();
            }
            catch (MySqlException ex)
            {
                Console.Write(ex.StackTrace);
            }
        }

        public void EmptyCart()
        {
            MySqlConnection connection;
            string connectionString = "server=localhost;uid=root;pwd=;database=wp;";

            try
            {
                connection = new MySqlConnection();
                connection.ConnectionString = connectionString;
                connection.Open();

                MySqlCommand command = new MySqlCommand();
                command.Connection = connection;
                command.CommandText = "DELETE FROM cart";

                command.ExecuteNonQuery();
            }
            catch (MySqlException ex)
            {
                Console.Write(ex.StackTrace);
            }
        }
    }
}