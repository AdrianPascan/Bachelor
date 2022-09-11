using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using lab8_AspNet.DataAbstractionLayer;
using lab8_AspNet.Models;
using System.Web.Script.Serialization;
using System.Diagnostics;

namespace lab8_AspNet.Controllers
{
    public class MainController : Controller
    {
        // GET: Main
        public ActionResult Index()
        {
            return View("Exam");
        }

        public string Test()
        {
            return "It's working!";
        }

        public int AuthenticatePerson()
        {
            string name = Request.Params["name"];

            Person person = new Person(-1, name);

            DAL dal = new DAL();
            int id = dal.AuthenticatePerson(person);
            person.id = id;

            ViewData["person"] = person;

            return id;
        }

        public ActionResult GetPersonChannels()
        {
            int ownerId = int.Parse(Request.Params["ownerid"]);

            DAL dal = new DAL();
            List<Channel> channels = dal.GetPersonChannels(ownerId);

            ViewData["channels"] = channels;

            return Json(channels);
        }

        public ActionResult GetPersonSubscriptions()
        {
            int id = int.Parse(Request.Params["id"]);
            string name = Request.Params["name"];

            DAL dal = new DAL();
            List<Channel> subscriptions = dal.GetPersonSubscriptions(id, name);

            ViewData["subscriptions"] = subscriptions;

            return Json(subscriptions);
        }

        public bool SubscribePersonToChannel()
        {
            int id = int.Parse(Request.Params["id"]);
            string name = Request.Params["name"];

            DAL dal = new DAL();
            bool success = dal.SubscribePersonToChannel(id, name);

            return success;
        }
    }
}