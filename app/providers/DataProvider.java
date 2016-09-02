package providers;

import play.Logger;

/**
 * := Coded with love by Sakib Sami on 9/2/16.
 * := s4kibs4mi@gmail.com
 * := www.sakib.ninja
 * := Coffee : Dream : Code
 */

public class DataProvider {
    private static DataProvider dataProvider;
    private DataConnection dataConnection;

    private DataProvider() {
        init();
    }

    public static DataProvider getInstance() {
        if (dataProvider == null)
            dataProvider = new DataProvider();

        return dataProvider;
    }

    private void init() {
        dataConnection = new DataConnection();
        if (dataConnection.connect() && dataConnection.isConnected()) {
            Logger.info(this.getClass().getCanonicalName() + " := Database Connected");
        } else {
            dataProvider = null;
            Logger.info(this.getClass().getCanonicalName() + " := Database Connection Failed");
        }
    }

    public DataConnection getDataConnection() {
        return dataConnection;
    }
}
