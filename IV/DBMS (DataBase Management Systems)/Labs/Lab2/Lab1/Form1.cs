using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Configuration;
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
        SqlDataAdapter daParent, daChild;
        DataSet ds; 
        SqlCommandBuilder cb; 
        BindingSource bsParent, bsChild;
        public Form1()
        {
            InitializeComponent();

            string connectionString = ConfigurationManager.AppSettings.Get("connectionString");
            connection = new SqlConnection(@connectionString);
            connection.Open();

            this.btnFirst_Click(null, null);
        }

        private void btnFirst_Click(object sender, EventArgs e)
        {
            string parentName = ConfigurationManager.AppSettings.Get("parentTable");
            string childName = ConfigurationManager.AppSettings.Get("childTable");

            ConfigurationManager.AppSettings.Set("currentChildTable", childName);

            ds = new DataSet();
            daParent = new SqlDataAdapter("select * from " + parentName, connection);
            daChild = new SqlDataAdapter("select * from " + childName, connection);
            SqlCommandBuilder commandBuilderCollection = new SqlCommandBuilder(daChild);
            cb = new SqlCommandBuilder(daParent);

            daParent.Fill(ds, parentName);
            daChild.Fill(ds, childName);

            string relationName = ConfigurationManager.AppSettings.Get("relationName");
            string relationParentAttributeName = ConfigurationManager.AppSettings.Get("relationParentAttributeName");
            string relationChildAttributeName = ConfigurationManager.AppSettings.Get("relationChildAttributeName");
            DataRelation dr = new DataRelation(relationName,
                                                ds.Tables[parentName].Columns[relationParentAttributeName],
                                                ds.Tables[childName].Columns[relationChildAttributeName]);
            ds.Relations.Add(dr);

            bsParent = new BindingSource();
            bsParent.DataSource = ds;
            bsParent.DataMember = parentName;

            bsChild = new BindingSource();
            bsChild.DataSource = bsParent;
            bsChild.DataMember = relationName;

            dgvParent.DataSource = bsParent;
            dgvChild.DataSource = bsChild;
        }

        private void btnSecond_Click(object sender, EventArgs e)
        {
            string parentName = ConfigurationManager.AppSettings.Get("parentTable2");
            string childName = ConfigurationManager.AppSettings.Get("childTable2");

            ConfigurationManager.AppSettings.Set("currentChildTable", childName);

            ds = new DataSet();
            daParent = new SqlDataAdapter("select * from " + parentName, connection);
            daChild = new SqlDataAdapter("select * from " + childName, connection);
            SqlCommandBuilder commandBuilderCollection = new SqlCommandBuilder(daChild);
            cb = new SqlCommandBuilder(daParent);

            daParent.Fill(ds, parentName);
            daChild.Fill(ds, childName);

            string relationName = ConfigurationManager.AppSettings.Get("relationName2");
            string relationParentAttributeName = ConfigurationManager.AppSettings.Get("relationParentAttributeName2");
            string relationChildAttributeName = ConfigurationManager.AppSettings.Get("relationChildAttributeName2");
            DataRelation dr = new DataRelation(relationName,
                                                ds.Tables[parentName].Columns[relationParentAttributeName],
                                                ds.Tables[childName].Columns[relationChildAttributeName]);
            ds.Relations.Add(dr);

            bsParent = new BindingSource();
            bsParent.DataSource = ds;
            bsParent.DataMember = parentName;

            bsChild = new BindingSource();
            bsChild.DataSource = bsParent;
            bsChild.DataMember = relationName;

            dgvParent.DataSource = bsParent;
            dgvChild.DataSource = bsChild;
        }

        private void btnUpdate_Click(object sender, EventArgs e)
        {
            string childName = ConfigurationManager.AppSettings.Get("currentChildTable");
            daChild.Update(ds, childName);
        }

        ~Form1()
        {
            connection.Close();
        }
    }
}
