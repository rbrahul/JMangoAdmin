package controllers;

import org.apache.commons.logging.Log;
import org.bson.Document;
import play.Logger;
import play.data.DynamicForm;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import play.twirl.api.Html;
import providers.CollectionProvider;
import providers.DataConnection;
import providers.DataProvider;
import providers.DatabaseProvider;
import utils.Utils;

import java.util.ArrayList;
import java.util.LinkedList;

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

            if (databaseName != null && !databaseName.value().isEmpty()) {
                DatabaseProvider databaseProvider = new DatabaseProvider(dataConnection.getDatabase(databaseName.value()));
                sidemenu = views.html.sidemenu.render(dataConnection.getDatabases(), databaseProvider.getCollections());

                if (collectionName != null && !collectionName.value().isEmpty()) {
                    CollectionProvider collectionProvider = new CollectionProvider(databaseProvider);
                    collectionProvider.setCollection(collectionName.value());

                    return ok(views.html.main.render(sidemenu, collectionProvider.getDocuments(), ""));
                }
            } else {
                sidemenu = views.html.sidemenu.render(dataConnection.getDatabases(), new ArrayList<>());
            }
            return ok(views.html.main.render(sidemenu, new LinkedList<>(), ""));
        } else {
            return redirect(routes.IndexController.onError("Connection to server failed."));
        }
    }

    public static Result setDatabase(String databaseName) {
        response().setCookie("selected-database", databaseName);
        response().setCookie("selected-collection", "");
        return redirect(routes.IndexController.index());
    }

    public static Result setCollection(String collectionName) {
        response().setCookie("selected-collection", collectionName);
        return redirect(routes.IndexController.index());
    }

    public static Result onEdit(String docId) {
        Http.Cookie database = request().cookies().get("selected-database");
        Http.Cookie collection = request().cookies().get("selected-collection");
        if (database != null && !database.value().isEmpty() && collection != null && !collection.value().isEmpty()) {
            DataProvider dataProvider = DataProvider.getInstance();
            DataConnection dataConnection = dataProvider.getDataConnection();
            if (dataConnection.isConnected()) {
                DatabaseProvider databaseProvider = new DatabaseProvider(dataConnection.getDatabase(database.value()));
                CollectionProvider collectionProvider = new CollectionProvider(databaseProvider);
                collectionProvider.setCollection(collection.value());

                if (Utils.isPostMethod(request())) {
                    DynamicForm data = Form.form().bindFromRequest();

                    if (Utils.hasValidParam(data.get("update-value"))) {
                        Logger.info(data.get("update-value"));
                    } else {
                        Logger.info("No Update Value");
                    }
                } else {

                    Document document = collectionProvider.getDocument(docId);
                    if (document != null) {
                        Html sidemenu = views.html.sidemenu.render(dataConnection.getDatabases(), databaseProvider.getCollections());
                        return ok(views.html.index.render(sidemenu, views.html.editor.render(document)));
                    } else {
                        return onError("Document not found");
                    }
                }
            }
        }
        return redirect(routes.IndexController.index());
    }

    public static Result onError(String error) {
        Html sideMenu = views.html.sidemenu.render(new ArrayList<>(), new ArrayList<>());
        return ok(views.html.error.render(sideMenu, error));
    }
}
