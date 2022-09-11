namespace Lab1
{
    partial class Form1
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.dgvDesigner = new System.Windows.Forms.DataGridView();
            this.dgvCollection = new System.Windows.Forms.DataGridView();
            this.btnUpdate = new System.Windows.Forms.Button();
            ((System.ComponentModel.ISupportInitialize)(this.dgvDesigner)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.dgvCollection)).BeginInit();
            this.SuspendLayout();
            // 
            // dgvDesigner
            // 
            this.dgvDesigner.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.dgvDesigner.Location = new System.Drawing.Point(12, 12);
            this.dgvDesigner.Name = "dgvDesigner";
            this.dgvDesigner.RowHeadersWidth = 51;
            this.dgvDesigner.RowTemplate.Height = 24;
            this.dgvDesigner.Size = new System.Drawing.Size(620, 210);
            this.dgvDesigner.TabIndex = 0;
            // 
            // dgvCollection
            // 
            this.dgvCollection.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.dgvCollection.Location = new System.Drawing.Point(12, 228);
            this.dgvCollection.Name = "dgvCollection";
            this.dgvCollection.RowHeadersWidth = 51;
            this.dgvCollection.RowTemplate.Height = 24;
            this.dgvCollection.Size = new System.Drawing.Size(620, 210);
            this.dgvCollection.TabIndex = 1;
            // 
            // btnUpdate
            // 
            this.btnUpdate.Location = new System.Drawing.Point(638, 201);
            this.btnUpdate.Name = "btnUpdate";
            this.btnUpdate.Size = new System.Drawing.Size(150, 50);
            this.btnUpdate.TabIndex = 3;
            this.btnUpdate.Text = "Update Database";
            this.btnUpdate.UseVisualStyleBackColor = true;
            this.btnUpdate.Click += new System.EventHandler(this.btnUpdate_Click);
            // 
            // Form1
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(8F, 16F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(800, 450);
            this.Controls.Add(this.btnUpdate);
            this.Controls.Add(this.dgvCollection);
            this.Controls.Add(this.dgvDesigner);
            this.Name = "Form1";
            this.Text = "Form1";
            ((System.ComponentModel.ISupportInitialize)(this.dgvDesigner)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.dgvCollection)).EndInit();
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.DataGridView dgvDesigner;
        private System.Windows.Forms.DataGridView dgvCollection;
        private System.Windows.Forms.Button btnUpdate;
    }
}

