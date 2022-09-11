using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace lab8_AspNet.Models
{
    public class Book
    {
        public int id { get; set; }
        public string title { get; set; }
        public string author { get; set; }
        public string cathegory { get; set; }
        public float price { get; set; }

        public Book()
        {
            this.id = -1;
        }

        public Book(int id, string title, string author, string cathegory, float price)
        {
            this.id = id;
            this.title = title;
            this.author = author;
            this.cathegory = cathegory;
            this.price = price;
        }
    }
}