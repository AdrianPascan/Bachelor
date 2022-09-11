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
            return View("ExamModel");
        }

        public string Test()
        {
            return "It's working!";
        }

        public int AuthenticateUser()
        {
            string name = Request.Params["name"];
            string password = Request.Params["password"];

            User user = new User(-1, name, password);

            DAL dal = new DAL();
            int id = dal.AuthenticateUser(user);
            user.id = id;

            ViewData["user"] = user;
            Stopwatch stopwatch = new Stopwatch();
            stopwatch.Reset();
            stopwatch.Start();
            stopwa
            stopwatch.Stop();

            return id;
        }

        public ActionResult GetUserAssets()
        {
            int userId = int.Parse(Request.Params["userid"]);

            DAL dal = new DAL();
            List<Asset> assets = dal.GetUserAssets(userId);

            ViewData["assets"] = assets;

            return Json(assets);
        }

        public ActionResult SaveUserAssets()
        {
            int userId = int.Parse(Request.Params["userid"]);

            List<Asset> assets = new List<Asset>();
            int index = 0; 
            while(Request.Params[ string.Format("assets[{0}][name]", index) ] != null)
            {
                string name = Request.Params[string.Format("assets[{0}][name]", index)];
                string description = Request.Params[string.Format("assets[{0}][description]", index)];
                int value = int.Parse(Request.Params[string.Format("assets[{0}][value]", index)]);

                Asset asset = new Asset(-1, userId, name, description, value);
                assets.Add(asset);

                index++;
            }

            DAL dal = new DAL();
            bool success = dal.AddUserAssets(assets);

            return Json(success);
        }
    }
}