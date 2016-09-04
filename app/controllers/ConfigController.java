package controllers;

import config.Config;
import play.data.DynamicForm;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import providers.DataProvider;
import utils.Utils;

/**
 * := Coded with love by Sakib Sami on 9/3/16.
 * := s4kibs4mi@gmail.com
 * := www.sakib.ninja
 * := Coffee : Dream : Code
 */

public class ConfigController extends Controller {

    public static Result addServer() {
        if (Utils.isPostMethod(request())) {
            DynamicForm data = Form.form().bindFromRequest();
            if (Utils.hasValidParam(data.get("server-address")) && Utils.hasValidParam("server-port")) {
                Config.addServerAddress(data.get("server-address"), data.get("server-port"));

                DataProvider.reInitiate();
                return ok(views.html.addserver.render("Server info added successfully."));
            } else return ok(views.html.addserver.render("Invalid server info."));
        } else {
            return ok(views.html.addserver.render(""));
        }
    }

    public static Result addCredential() {
        if (Utils.isPostMethod(request())) {
            DynamicForm data = Form.form().bindFromRequest();
            if (Utils.hasValidParam(data.get("user-name")) && Utils.hasValidParam(data.get("user-password")) &&
                    Utils.hasValidParam(data.get("database-name"))) {
                Config.addCredential(data.get("user-name"), data.get("user-password"), data.get("database-name"));

                DataProvider.reInitiate();
                return ok(views.html.addcredential.render("Credential added successfully."));
            } else return ok(views.html.addcredential.render("Invalid Credential."));
        } else return ok(views.html.addcredential.render(""));
    }

    public static Result createDatabase() {
        return ok(views.html.createdatabase.render(""));
    }
}
