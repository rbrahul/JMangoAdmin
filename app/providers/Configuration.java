package providers;

import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;

import java.util.ArrayList;
import java.util.List;

/**
 * := Coded with love by Sakib Sami on 9/3/16.
 * := s4kibs4mi@gmail.com
 * := www.sakib.ninja
 * := Coffee : Dream : Code
 */

public class Configuration {
    private static List<ServerAddress> serverAddresses = new ArrayList<>();
    private static List<MongoCredential> mongoCredentials = new ArrayList<>();

    public static void addServerAddress(String host, String port) {
        serverAddresses.add(new ServerAddress(host, Integer.parseInt(port)));
    }

    public static List<ServerAddress> getServerAddresses() {
        return serverAddresses;
    }

    public static void addCredential(String userName, String password, String dbname) {
        mongoCredentials.add(MongoCredential.createCredential(userName, dbname, password.toCharArray()));
    }

    public static List<MongoCredential> getMongoCredentials() {
        return mongoCredentials;
    }
}
