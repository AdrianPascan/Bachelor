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

namespace Lab1
{
    public partial class Form1 : Form
    {
        SqlConnection connection; 
        SqlDataAdapter daDesigner, daCollection;
        DataSet ds; 
        SqlCommandBuilder cb; 
        BindingSource bsDesigner, bsCollection;
        public Form1()
        {
            InitializeComponent();

            connection = new SqlConnection(@"Data Source = DESKTOP-JC39FI8\SQLEXPRESS; Initial Catalog=ClothingStore; Integrated Security=True");
            connection.Open();
            ds = new DataSet();
            daDesigner = new SqlDataAdapter("select * from Designer", connection);
            daCollection = new SqlDataAdapter("select * from Collection", connection);
            SqlCommandBuilder commandBuilderCollection = new SqlCommandBuilder(daCollection);
            cb = new SqlCommandBuilder(daDesigner);

            daDesigner.Fill(ds, "Designer");
            daCollection.Fill(ds, "Collection");

            DataRelation dr = new DataRelation("FK__Collection__Did__398D8EEE",
                                                ds.Tables["Designer"].Columns["Did"],
                                                ds.Tables["Collection"].Columns["Did"]);
            ds.Relations.Add(dr);

            bsDesigner = new BindingSource();
            bsDesigner.DataSource = ds;
            bsDesigner.DataMember = "Designer";

            bsCollection = new BindingSource();
            bsCollection.DataSource = bsDesigner;
            bsCollection.DataMember = "FK__Collection__Did__398D8EEE";

            dgvDesigner.DataSource = bsDesigner;
            dgvCollection.DataSource = bsCollection;
        }

        private void btnUpdate_Click(object sender, EventArgs e)
        {
            daCollection.Update(ds, "Collection");
        }

        ~Form1()
        {
            connection.Close();
        }
    }
}
