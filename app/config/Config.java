package config;

import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import play.Logger;
import utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * := Coded with love by Sakib Sami on 9/3/16.
 * := s4kibs4mi@gmail.com
 * := www.sakib.ninja
 * := Coffee : Dream : Code
 */

public class Config {
    private static List<ServerAddress> serverAddresses = new ArrayList<>();
    private static List<MongoCredential> mongoCredentials = new ArrayList<>();

    public static void addServerAddress(String host, String port) {
        boolean isExits = false;
        for (ServerAddress address : serverAddresses) {
            Logger.info(address.getHost() + ":" + address.getPort());

            if (Utils.isEqual(address.getHost(), host)) {
                isExits = true;
                break;
            }
        }

        if (!isExits)
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
