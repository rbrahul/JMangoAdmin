package providers;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;

/**
 * := Coded with love by Sakib Sami on 9/3/16.
 * := s4kibs4mi@gmail.com
 * := www.sakib.ninja
 * := Coffee : Dream : Code
 */

public class DatabaseProvider {
    private MongoDatabase database;

    public DatabaseProvider(MongoDatabase database) {
        this.database = database;
    }

    public ArrayList<String> getCollections() {
        ArrayList<String> collections = new ArrayList<>();
        MongoCursor<String> cursor = database.listCollectionNames().iterator();
        while (cursor.hasNext()) {
            collections.add(cursor.next());
        }

        return collections;
    }

    public MongoCollection<Document> getCollection(String name) {
        return database.getCollection(name);
    }
}
