package accounts;
import java.sql.*;
import model.dao.DataConnection;
import utility.GLogger;
public class Accounts {

    public static void main(String[] args) {
        try {
            DataConnection.getConnection();
            DataConnection.closeConnection();
        } catch (Exception e) {
            GLogger.prepareLog(e);
        } 
    }

}
