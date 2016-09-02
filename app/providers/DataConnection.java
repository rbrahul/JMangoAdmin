package providers;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.event.*;
import play.Logger;

import java.util.ArrayList;

/**
 * := Coded with love by Sakib Sami on 9/2/16.
 * := s4kibs4mi@gmail.com
 * := www.sakib.ninja
 * := Coffee : Dream : Code
 */

public class DataConnection implements ServerListener, ServerMonitorListener {
    private int connectionTimeout = 15;
    private boolean isServerConnected;

    private MongoClient client;

    public boolean connect() {
        try {
            MongoClientOptions clientOptions = new MongoClientOptions.Builder()
                    .connectTimeout(connectionTimeout)
                    .addServerListener(this)
                    .addServerMonitorListener(this)
                    .build();

            if (Configuration.getServerAddresses().isEmpty()) {
                Configuration.addServerAddress("localhost", "27017");
            }

            if (Configuration.getMongoCredentials().isEmpty()) {
                client = new MongoClient(Configuration.getServerAddresses(), clientOptions);
            } else {
                client = new MongoClient(Configuration.getServerAddresses(), Configuration.getMongoCredentials(), clientOptions);
            }

            return true;
        } catch (Exception ex) {
            client = null;
        }
        return false;
    }

    public boolean isConnected() {
        try {
            return isServerConnected;
        } catch (Exception e) {
            return false;
        }
    }

    public void createDatabase(String databaseName) {
        client.getDB(databaseName);
    }

    public MongoDatabase getDatabase(String databaseName) {
        return client.getDatabase(databaseName);
    }

    public ArrayList<String> getDatabases() {
        ArrayList<String> databases = new ArrayList<>();
        MongoCursor mongoCursor = client.listDatabaseNames().iterator();
        while (mongoCursor.hasNext()) {
            databases.add((String) mongoCursor.next());
        }
        return databases;
    }

    public void disconnect() {
        if (client != null)
            client.close();
    }

    @Override
    public void serverOpening(ServerOpeningEvent serverOpeningEvent) {
        Logger.info(this.getClass().getCanonicalName() + " := Server Opening");
    }

    @Override
    public void serverClosed(ServerClosedEvent serverClosedEvent) {
        Logger.info(this.getClass().getCanonicalName() + " := Server Closed");
    }

    @Override
    public void serverDescriptionChanged(ServerDescriptionChangedEvent serverDescriptionChangedEvent) {
        Logger.info(this.getClass().getCanonicalName() + " := Server Description Changed");
    }

    @Override
    public void serverHearbeatStarted(ServerHeartbeatStartedEvent serverHeartbeatStartedEvent) {
        Logger.info(this.getClass().getCanonicalName() + " := Server Heartbeat Start");
    }

    @Override
    public void serverHeartbeatSucceeded(ServerHeartbeatSucceededEvent serverHeartbeatSucceededEvent) {
        isServerConnected = true;
        Logger.info(this.getClass().getCanonicalName() + " := Server Heartbeat Success");
    }

    @Override
    public void serverHeartbeatFailed(ServerHeartbeatFailedEvent serverHeartbeatFailedEvent) {
        isServerConnected = false;
        Logger.info(this.getClass().getCanonicalName() + " := Server Heartbeat Failed");
    }
}
