package controllers;

import play.data.DynamicForm;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import utils.Utils;

/**
 * := Coded with love by Sakib Sami on 9/3/16.
 * := s4kibs4mi@gmail.com
 * := www.sakib.ninja
 * := Coffee : Dream : Code
 */

public class SetupController extends Controller {
    public static Result index() {
        if (Utils.isPostMethod(request())) {
            DynamicForm params = Form.form().bindFromRequest();
            if (params != null) {
                String server = params.get("server-address");
                String port = params.get("server-port");
                String username = params.get("user-name");
                String userpassword = params.get("user-password");

                if (server == null || server.isEmpty())
                    response().setCookie("server-address", "localhost");
                else response().setCookie("server-address", server);

                if (port == null || server.isEmpty())
                    response().setCookie("server-port", "27017");
                else response().setCookie("server-port", port);

                if (username != null && !username.isEmpty())
                    response().setCookie("user-name", username);

                if (userpassword != null && !userpassword.isEmpty())
                    response().setCookie("user-password", userpassword);

                return redirect(routes.IndexController.index());
            }
        }
        return ok(views.html.setup.render());
    }
}
