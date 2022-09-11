using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace lab8_AspNet.Models
{
    public class Asset
    {
        public int id { get; set; }

        public int userId { get; set; }

        public string name { get; set; }

        public string description { get; set; }

        public int value { get; set; }

        public Asset()
        {
            id = -1;
            userId = -1;
        }

        public Asset(int id, int userId, string name, string description, int value)
        {
            this.id = id;
            this.userId = userId;
            this.name = name;
            this.description = description;
            this.value = value;
        }
    }
}
