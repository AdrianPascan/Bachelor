using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace lab8_AspNet.Models
{
    public class Channel
    {
        public int id { get; set; }

        public int ownerId { get; set; }

        public string name { get; set; }

        public string description { get; set; }

        public string subscribers { get; set; }

        public Channel()
        {
            id = -1;
            ownerId = -1;
        }

        public Channel(int id, int ownerId, string name, string description, string subscribers)
        {
            this.id = id;
            this.ownerId = ownerId;
            this.name = name;
            this.description = description;
            this.subscribers = subscribers;
        }
    }
}