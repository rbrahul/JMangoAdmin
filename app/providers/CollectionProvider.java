package providers;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.LinkedList;
import java.util.List;

/**
 * := Coded with love by Sakib Sami on 9/3/16.
 * := s4kibs4mi@gmail.com
 * := www.sakib.ninja
 * := Coffee : Dream : Code
 */

public class CollectionProvider {
    private DatabaseProvider databaseProvider;
    private MongoCollection<Document> collection;

    public CollectionProvider(DatabaseProvider databaseProvider) {
        this.databaseProvider = databaseProvider;
    }

    public void setCollection(String name) {
        collection = databaseProvider.getCollection(name);
    }

    public void addDocument(Document document) {
        collection.insertOne(document);
    }

    public List<Document> getDocuments() {
        List<Document> documents = new LinkedList<>();
        MongoCursor<Document> cursor = collection.find().iterator();
        while (cursor.hasNext()) {
            documents.add(cursor.next());
        }

        return documents;
    }

    public Document getDocument(String docId) {
        try {
            return collection.find(new Document("_id", new ObjectId(docId))).first();
        } catch (Exception ex) {
            return null;
        }
    }

    public List<Document> findDocument() {
        List<Document> documents = new LinkedList<>();

        return documents;
    }
}
