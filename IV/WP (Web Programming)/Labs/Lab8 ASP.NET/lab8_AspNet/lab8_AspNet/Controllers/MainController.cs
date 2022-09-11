using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using lab8_AspNet.DataAbstractionLayer;
using lab8_AspNet.Models;

namespace lab8_AspNet.Controllers
{
    public class MainController : Controller
    {
        // GET: Main
        public ActionResult Index()
        {
            return View("MyBookStore");
        }

        public string Test()
        {
            return "It's working";
        }

        public int AuthenticateUser()
        {
            string name = Request.Params["name"];
            string password = Request.Params["password"];

            User user = new User(-1, name, password);

            DAL dal = new DAL();
            int id = dal.AuthenticateUser(user);

            ViewData["user"] = user;

            return id;
        }

        public void AddSession()
        {
            int userId = int.Parse(Request.Params["id"]);
            int startTime = int.Parse(Request.Params["start"]);
            int sessionTime = int.Parse(Request.Params["time"]);

            Session session = new Session(-1, userId, startTime, sessionTime);

            DAL dal = new DAL();
            dal.AddSession(session);

            ViewData["session"] = session;
        }

        public ActionResult GetBooksFilteredByCathegory()
        {
            string cathegory = Request.Params["cathegory"];

            DAL dal = new DAL();
            List<Book> books = dal.GetBooksFilteredByCathegory(cathegory);

            ViewData["books"] = books;

            return Json(books);
        }

        public ActionResult GetCartItems()
        {
            DAL dal = new DAL();
            List<CartItem> cartItems = dal.GetCartItems();

            ViewData["cartItems"] = cartItems;

            return Json(cartItems);
        }

        public void AddCartItem()
        {
            int bookId = int.Parse(Request.Params["id"]);
            int quantity = int.Parse(Request.Params["quantity"]);

            CartItem cartItem = new CartItem(-1, bookId, quantity);

            DAL dal = new DAL();
            dal.AddCartItem(cartItem);
        }

        public void DeleteCartItem()
        {
            int cartItemId = int.Parse(Request.Params["id"]);

            DAL dal = new DAL();
            dal.DeleteCartItem(cartItemId);
        }

        public void EmptyCart()
        {
            DAL dal = new DAL();
            dal.EmptyCart();
        }
    }
}