package controllers;

import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import play.twirl.api.Html;
import providers.DataConnection;
import providers.DataProvider;
import providers.DatabaseProvider;

import java.util.ArrayList;

/**
 * := Coded with love by Sakib Sami on 9/2/16.
 * := s4kibs4mi@gmail.com
 * := www.sakib.ninja
 * := Coffee : Dream : Code
 */

public class IndexController extends Controller {
    public static Result index() {
        DataProvider dataProvider = DataProvider.getInstance();
        DataConnection dataConnection = dataProvider.getDataConnection();

        if (dataConnection.isConnected()) {
            Html sidemenu;
            Http.Cookie databaseName = request().cookies().get("selected-database");
            Http.Cookie collectionName = request().cookies().get("selected-collection");

            if (databaseName != null) {
                DatabaseProvider databaseProvider = new DatabaseProvider(dataConnection.getDatabase(databaseName.value()));
                sidemenu = views.html.sidemenu.render(dataConnection.getDatabases(), databaseProvider.getCollections());

                if (collectionName != null) {

                }
            } else {
                sidemenu = views.html.sidemenu.render(dataConnection.getDatabases(), new ArrayList<>());
            }
            return ok(views.html.index.render(sidemenu));
        } else {
            return redirect(routes.IndexController.onError("Connection to server failed."));
        }
    }

    public static Result setDatabase(String databaseName) {
        response().setCookie("selected-database", databaseName);
        return redirect(routes.IndexController.index());
    }

    public static Result setCollection(String collectionName) {
        response().setCookie("selected-collection", collectionName);
        return redirect(routes.IndexController.index());
    }

    public static Result onError(String error) {
        Html sideMenu = views.html.sidemenu.render(new ArrayList<>(), new ArrayList<>());
        return ok(views.html.error.render(sideMenu, error));
    }
}
