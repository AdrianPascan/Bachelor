using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using System.Data.SqlClient;

// !!! CHANGE NAME !!!
namespace _2b
{
    public partial class Form1 : Form
    {
        SqlConnection connection;
        SqlDataAdapter daProducers, daMovies;
        DataSet ds;
        SqlCommandBuilder cbMovies;
        BindingSource bsProducers, bsMovies;

        private void btnUpdateDB_Click(object sender, EventArgs e)
        {
            daMovies.Update(ds, "Movies");
        }

        public Form1()
        {
            InitializeComponent();
        }

        ~Form1()
        {
            connection.Close();
        }

        private void Form1_Load(object sender, EventArgs e)
        {
            // instantiate the binding sources
            bsProducers = new BindingSource();
            bsMovies = new BindingSource();

            // bind the data grid views to the corresponding resources
            dgvProducers.DataSource = bsProducers;
            dgvMovies.DataSource = bsMovies;

            // establish the connection to the FilmFestival database
            connection = new SqlConnection(@"Data Source = DESKTOP-JC39FI8\SQLEXPRESS; Initial Catalog=FilmFestival; Integrated Security=True");

            // instantiate the data set
            ds = new DataSet();

            // instantiate the data adapters and bind them to the data records stored into the corresponding tables
            daProducers = new SqlDataAdapter("SELECT * FROM Producers", connection);
            daMovies = new SqlDataAdapter("Select * FROM Movies", connection);

            // instantiate the command builder for the data adapter of the Movies table
            cbMovies = new SqlCommandBuilder(daMovies);

            // fetch the data records from to the source tables into the data adapters
            daProducers.Fill(ds, "Producers");
            daMovies.Fill(ds, "Movies");

            // specify the foreign key relation between the Producers and Movies tables and add it to the data set
            DataRelation dr = new DataRelation(
                "ProducerMovies",
                ds.Tables["Producers"].Columns["ProducerID"],
                ds.Tables["Movies"].Columns["ProducerID"]
                );
            ds.Relations.Add(dr);


            // set the binding source of the producers to the Producers table
            bsProducers.DataSource = ds;
            bsProducers.DataMember = "Producers";

            // set the binding source of the movies to the binding source of the producers based on the relation defined above
            bsMovies.DataSource = bsProducers;
            bsMovies.DataMember = "ProducerMovies";
        }
    }
}
