/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ass.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import ass.bean.Report;

/**
 *
 * @author LYF00
 */
public class ReportDB {

    private DataBase db;

    public ReportDB(DataBase db) {
        this.db = db;

    }

    public ArrayList<Report> getAllReport() {
        ArrayList<Report> reportList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = db.getConn();
            String query = "SELECT d.*,c.campus_item_id, i.item_name FROM damage_report d JOIN campus_item c ON d.campus_item_id = c.campus_item_id JOIN item i ON c.item_id = i.item_id ";
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String reportId = resultSet.getString("report_id");
                String itemId = resultSet.getString("campus_item_id");
                String description = resultSet.getString("description");
                String user_id = resultSet.getString("user_id");
                String datetime = resultSet.getString("datetime");
                String status = resultSet.getString("status");
                String itemName = resultSet.getString("item_name");
                Report report = new Report(reportId, itemId, description, user_id, datetime, status, itemName);
                reportList.add(report);

            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            db.closeCon(statement, connection, resultSet);
        }

        return reportList;
    }

    public boolean changeStatusDamaged(String item_id){
        return changeStatus(item_id, "Damaged");
    }
    public boolean changeStatusAvailable(String item_id){
        return changeStatus(item_id, "Available");
    }

    public boolean changeStatus(String id,String status){
        boolean isChanged = false;
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        try {
            cnnct = db.getConn();
            String preQueryStatement = "UPDATE campus_item SET status = ? WHERE campus_item_id = ?";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setString(1, status);
            pStmnt.setString(2, id);
            int rowCount = pStmnt.executeUpdate();
            if (rowCount >= 1) {
                isChanged = true;
            }
            pStmnt.close();
            cnnct.close();
        } catch (SQLException ex) {
            while (ex != null) {
                ex.printStackTrace();
                ex = ex.getNextException();
            }
        } finally {
            db.closeCon(pStmnt, cnnct, null);
        }
        return isChanged;
    }

    public boolean addReport(String reportId, String item_id, String description, String user_id, String datetime) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        boolean isSuccess = false;
        if(!changeStatusDamaged(item_id)){
            return false;
        }
        try {
            cnnct = db.getConn();
            String preQueryStatement = "INSERT INTO DAMAGE_REPORT VALUES (?,?,?,?,?,?)";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setString(1, reportId);
            pStmnt.setString(2, item_id);
            pStmnt.setString(3, description);
            pStmnt.setString(4, user_id);
            pStmnt.setString(5, datetime);
            pStmnt.setString(6, "Pending");
            int rowCount = pStmnt.executeUpdate();
            if (rowCount >= 1) {
                isSuccess = true;
            }
            pStmnt.close();
            cnnct.close();
        } catch (SQLException ex) {
            while (ex != null) {
                ex.printStackTrace();
                ex = ex.getNextException();
            }
        } finally {
            db.closeCon(pStmnt, cnnct, null);
        }
        return isSuccess;
    }

    public Report queryReportByID(String id) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        Report r = null;
        try {
            cnnct = db.getConn();
            String preQueryStatement = "SELECT d.*, i.item_name, u.username FROM damage_report d JOIN campus_item c ON d.campus_item_id = c.campus_item_id JOIN item i ON c.item_id = i.item_id JOIN user u ON d.user_id = u.user_id WHERE d.report_id = ?";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setString(1, id);
            ResultSet rs = null;
            rs = pStmnt.executeQuery();
            if (rs.next()) {
                r = new Report();
                r.setReport_id(rs.getString("report_id"));
                r.setItem_id(rs.getString("campus_item_id"));
                r.setItem_name(rs.getString("item_name"));
                r.setUser_id(rs.getString("user_id"));
                r.setDescription(rs.getString("description"));
                r.setDatetime(rs.getString("datetime"));
                r.setStatus(rs.getString("status"));
            }
            pStmnt.close();
            cnnct.close();
        } catch (SQLException ex) {
            while (ex != null) {
                ex.printStackTrace();
                ex = ex.getNextException();
            }
        } finally {
            db.closeCon(pStmnt, cnnct, null);
        }
        return r;
    }

    public boolean updateReport(String id) {

        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        boolean success = false;
        String itemId = queryReportByID(id).getItem_id();
        if(!changeStatusAvailable(itemId)){
            return false;
        }
        try {
            cnnct = db.getConn();
            String updateStatement = "UPDATE damage_report SET status = ? WHERE report_id = ? ";
            pStmnt = cnnct.prepareStatement(updateStatement);

            pStmnt.setString(1, "Complete");
            pStmnt.setString(2, id);

            int rowsUpdated = pStmnt.executeUpdate();

            if (rowsUpdated > 0) {
                success = true;
            }

            pStmnt.close();
            cnnct.close();
        } catch (SQLException ex) {
            while (ex != null) {
                ex.printStackTrace();
                ex = ex.getNextException();
            }
        } finally {
            db.closeCon(pStmnt, cnnct, null);
        }
        return success;
    }
    
    
    
    
    
}
