package model.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.to.AccountInfoTO;
import utility.GLogger;

public class AccountInfoDAO extends DAO {

    public boolean insertRecord(AccountInfoTO record) {
        try {
            String query = "insert into accountinfo ";
            query += " (accountname,openingbalance,creationdate,currentbalance) ";
            query += " values(?,?,CURRENT_TIMESTAMP,?)";
            PreparedStatement stmt = DataConnection.getPrepareStatement(query);
            stmt.setString(1, record.getAccountname());
            stmt.setFloat(2, record.getOpeningbalance());
            stmt.setFloat(3, record.getCurrentbalance());
            boolean result = stmt.executeUpdate() > 0;
            stmt.close();
            return result;
        } catch (Exception ex) {
            GLogger.prepareLog(ex);
            errorMessage = ex.toString();
            return false;
        }
    }
    public boolean updateAmountafterdelete(int accountid,float amount) {
        try {
            String query = "update accountinfo ";
            query += " set currentbalance = ? ";
            query += " where accountid = ?";
            PreparedStatement stmt = DataConnection.getPrepareStatement(query);
            stmt.setFloat(1, amount);
            stmt.setFloat(2, accountid);
            boolean result = stmt.executeUpdate() > 0;
            stmt.close();
            return result;
        } catch (Exception ex) {
            GLogger.prepareLog(ex);
            errorMessage = ex.toString();
            return false;
        }
    }
    public boolean updateAmount(int accountid,float amount) {
        try {
            String query = "update accountinfo ";
            query += " set currentbalance = currentbalance + ? ";
            query += " where accountid = ?";
            PreparedStatement stmt = DataConnection.getPrepareStatement(query);
            stmt.setFloat(1, amount);
            stmt.setFloat(2, accountid);
            boolean result = stmt.executeUpdate() > 0;
            stmt.close();
            return result;
        } catch (Exception ex) {
            GLogger.prepareLog(ex);
            errorMessage = ex.toString();
            return false;
        }
    }
    
    public List<AccountInfoTO> getAllRecord() {
        try {
            String query = "select accountid,accountname,openingbalance,creationdate,currentbalance ";
            query += " from accountinfo ";
            PreparedStatement stmt = DataConnection.getPrepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            List<AccountInfoTO> result = null;
            if(rs.next()){
                result = new ArrayList<>();
                do{
                    AccountInfoTO ait = new AccountInfoTO();
                    ait.setAccountid(rs.getInt("accountid"));
                    ait.setAccountname(rs.getString("accountname"));
                    ait.setOpeningbalance(rs.getFloat("openingbalance"));
                    ait.setCreationdate(rs.getTimestamp("creationdate"));
                    ait.setCurrentbalance(rs.getFloat("currentbalance"));
                    result.add(ait);
                }while(rs.next());
            }
            rs.close();
            stmt.close();
            return result;
        } catch (Exception ex) {
            GLogger.prepareLog(ex);
            errorMessage = ex.toString();
            return null;
        }
    }
}
