using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace lab8_AspNet.Models
{
    public class CartItem
    {
        public int id { get; set; }
        public int bookId { get; set; }
        public int quantity { get; set; }

        public CartItem()
        {
            this.id = -1;
            this.bookId = -1;
        }

        public CartItem(int id, int bookId, int quantity)
        {
            this.id = id;
            this.bookId = bookId;
            this.quantity = quantity;
        }
    }
}