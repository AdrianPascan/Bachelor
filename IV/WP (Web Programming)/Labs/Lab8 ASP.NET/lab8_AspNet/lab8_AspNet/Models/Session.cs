using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace lab8_AspNet.Models
{
    public class Session
    {
        public int id { get; set; }

        public int userId { get; set; }

        public int startTime { get; set; }

        public int sessionTime { get; set; }

        public Session()
        {
            this.id = -1;
            this.userId = -1;
        }

        public Session(int id, int userId, int startTime, int sessionTime)
        {
            this.id = id;
            this.userId = userId;
            this.startTime = startTime;
            this.sessionTime = sessionTime;
        }
    }
}