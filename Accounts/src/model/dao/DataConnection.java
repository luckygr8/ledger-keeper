package model.dao;

import config.DatabaseConfiguration;
import java.sql.*;
import java.util.Properties;
import org.sqlite.SQLiteConfig;
import org.sqlite.SQLiteConfig.Pragma;
import utility.GLogger;

public class DataConnection {

    private static Connection con = null;

    public static Connection getConnection() throws Exception {
        if (con == null) {
            SQLiteConfig sqLiteConfig = new SQLiteConfig();
            Properties properties = sqLiteConfig.toProperties();
            properties.setProperty(Pragma.DATE_STRING_FORMAT.pragmaName, "yyyy-MM-dd HH:mm:ss");
            con = DriverManager.getConnection(DatabaseConfiguration.CONNECTION_URL, properties);
            Statement stmt = con.createStatement();
            stmt.execute(DatabaseConfiguration.ACCOUNT_TABLE_QUERY);
            stmt.execute(DatabaseConfiguration.ENTRYINFO_TABLE_QUERY);
            stmt.execute(DatabaseConfiguration.CLIENTINFO_TABLE_QUERY);
            stmt.close();
        }
        return con;
    }

    public static PreparedStatement getPrepareStatement(String query) throws Exception {
        return getConnection().prepareStatement(query);
    }

    public static void closeConnection() {
        try {
            if (con != null) {
                con.close();
            }
        } catch (Exception ex) {
            GLogger.prepareLog(ex);
        }
    }
}
