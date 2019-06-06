package model.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import model.to.AccountInfoTO;
import model.to.EntryInfoTO;
import utility.GLogger;

public class EntryInfoDAO extends DAO {

    public boolean insertRecord(EntryInfoTO record) {
        try {
            String query = "insert into entryinfo ";
            query += " (accountid,particulars,entrydate,amount,entrytype) ";
            query += " values(?,?,?,?,?)";
            PreparedStatement stmt = DataConnection.getPrepareStatement(query);
            stmt.setInt(1, record.getAccountid());
            stmt.setString(2, record.getParticulars());
            stmt.setDate(3, record.getEntrydate());
            stmt.setFloat(4, record.getAmount());
            stmt.setString(5, record.getEntrytype());
            boolean result = stmt.executeUpdate() > 0;
            stmt.close();
            return result;
        } catch (Exception ex) {
            GLogger.prepareLog(ex);
            errorMessage = ex.toString();
            return false;
        }
    }

    public boolean updateRecord(EntryInfoTO record) {
        try {
            String query = "update entryinfo ";
            query += " set accountid = ? , particulars = ? ,entrydate = ? ,amount = ? ,entrytype = ? , entryid = ? ";
            query += "where entryid = ? ";
            PreparedStatement stmt = DataConnection.getPrepareStatement(query);
            stmt.setInt(1, record.getAccountid());
            stmt.setString(2, record.getParticulars());
            stmt.setDate(3, record.getEntrydate());
            stmt.setFloat(4, record.getAmount());
            stmt.setString(5, record.getEntrytype());
            stmt.setInt(6, record.getEntryid());
            stmt.setInt(7, record.getEntryid());
            boolean result = stmt.executeUpdate() > 0;
            stmt.close();
            System.out.println(result);
            return result;
        } catch (Exception ex) {
            GLogger.prepareLog(ex);
            errorMessage = ex.toString();
            System.out.println(ex);
            return false;
        }
    }

    public float getOverallDebitCredit(AccountInfoTO ai) {
        try {
            float total_value = 0;
            String query = "select * from entryinfo where accountid = ?";
            PreparedStatement stmt = DataConnection.getPrepareStatement(query);
            stmt.setInt(1, ai.getAccountid());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                do {

                    if (rs.getString("entrytype").equals("Credit")) {
                        total_value+=rs.getFloat("amount");
                    } else {
                        total_value-=rs.getFloat("amount");
                    }
                } while (rs.next());
            }
            rs.close();
            stmt.close();
            return total_value;
        } catch (Exception ex) {
            GLogger.prepareLog(ex);
            errorMessage = ex.toString();
            return 0;
        }
    }

    public boolean deleterecord(int entryid) {
        boolean result;
        try {
            String query = "delete from entryinfo where entryid=?";
            PreparedStatement stmt = DataConnection.getPrepareStatement(query);
            stmt.setInt(1, entryid);
            result = stmt.executeUpdate() > 0;
            stmt.close();
            return result;
        } catch (Exception e) {
            errorMessage = e.toString().trim();
            return false;
        }
    }
    public LinkedList<EntryInfoTO> get_records_according_to_financial_year(String epochp , String epochn)
    {
        try {
            String query = "select * from entryinfo where entrydate > ? and entrydate < ?";
            PreparedStatement stmt = DataConnection.getPrepareStatement(query);
            stmt.setString(1,epochp);
            stmt.setString(2,epochn);
            ResultSet rs = stmt.executeQuery();
            LinkedList<EntryInfoTO> result = null;
            if (rs.next()) {
                result = new LinkedList<>();
                do {
                    EntryInfoTO rec = new EntryInfoTO();
                    rec.setEntryid(rs.getInt("entryid"));
                    rec.setAccountid(rs.getInt("accountid"));
                    rec.setParticulars(rs.getString("particulars"));
                    rec.setAmount(rs.getFloat("amount"));
                    rec.setEntrydate(rs.getDate("entrydate"));
                    rec.setEntrytype(rs.getString("entrytype"));
                    result.add(rec);
                } while (rs.next());
            }
            rs.close();
            stmt.close();
            System.out.println("query completed");
            return result;
        } catch (Exception ex) {
            GLogger.prepareLog(ex);
            errorMessage = ex.toString();
            System.out.println(errorMessage);
            return null;
        }
    }
    public List<EntryInfoTO> getAllRecord() {
        try {
            String query = "select entryid,accountid,particulars,entrydate,amount,entrytype ";
            query += " from entryinfo ";
            PreparedStatement stmt = DataConnection.getPrepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            List<EntryInfoTO> result = null;
            if (rs.next()) {
                result = new ArrayList<>();
                do {
                    EntryInfoTO rec = new EntryInfoTO();
                    rec.setEntryid(rs.getInt("entryid"));
                    rec.setAccountid(rs.getInt("accountid"));
                    rec.setParticulars(rs.getString("particulars"));
                    rec.setAmount(rs.getFloat("amount"));
                    rec.setEntrydate(rs.getDate("entrydate"));
                    rec.setEntrytype(rs.getString("entrytype"));
                    result.add(rec);
                } while (rs.next());
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

    public LinkedList<EntryInfoTO> getAllRecord(int accountid) {
        try {
            String query = "select entryid,accountid,particulars,entrydate,amount,entrytype ";
            query += " from entryinfo ";
            query += " where accountid=?";
            query += " order by entryid asc";
            PreparedStatement stmt = DataConnection.getPrepareStatement(query);
            stmt.setInt(1, accountid);
            ResultSet rs = stmt.executeQuery();
            LinkedList<EntryInfoTO> result = null;
            if (rs.next()) {
                result = new LinkedList<>();
                do {
                    EntryInfoTO rec = new EntryInfoTO();
                    rec.setEntryid(rs.getInt("entryid"));
                    rec.setAccountid(rs.getInt("accountid"));
                    rec.setParticulars(rs.getString("particulars"));
                    rec.setAmount(rs.getFloat("amount"));
                    rec.setEntrydate(rs.getDate("entrydate"));
                    rec.setEntrytype(rs.getString("entrytype"));
                    result.add(rec);
                } while (rs.next());
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
