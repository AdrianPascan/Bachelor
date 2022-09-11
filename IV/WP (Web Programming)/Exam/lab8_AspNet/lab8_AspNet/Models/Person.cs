using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace lab8_AspNet.Models
{
    public class Person
    {
        public int id { get; set; }
        public string name { get; set; }

        public Person()
        {
            id = -1;
        }

        public Person(int id, string name)
        {
            this.id = id;
            this.name = name;
        }
    }
}