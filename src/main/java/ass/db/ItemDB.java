/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ass.db;

import ass.bean.Item;
import ass.utils.IDUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author LYF00
 */
public class ItemDB {

    private DataBase db;

    public ItemDB(DataBase db) {
        this.db = db;
    }

    public boolean createItem(String itemId, String itemName, String itemType, String forStaff,
            String active) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;

        boolean isSuccess = false;

        try {
            cnnct = db.getConn();
            String preQueryStatement = "INSERT INTO ITEM(item_id , item_name , item_type , for_staff , active) VALUES (?,?,?,?,?)";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setString(1, itemId);
            pStmnt.setString(2, itemName);
            pStmnt.setString(3, itemType);
            pStmnt.setString(4, forStaff);
            pStmnt.setString(5, active);

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

    public ArrayList<Item> getAllItems() {
        ArrayList<Item> itemList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = db.getConn();
            String query = "SELECT * FROM item WHERE active='1'";
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String itemId = resultSet.getString("item_id");
                String itemName = resultSet.getString("item_name");
                String itemType = resultSet.getString("item_type");
                String userType = resultSet.getString("for_staff");
                String active = resultSet.getString("active");
                Item item = new Item(itemId, itemName, itemType, userType, active);
                itemList.add(item);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            db.closeCon(statement, connection, resultSet);
        }

        return itemList;
    }

    public ArrayList<String> getCItemIds(String id){
        ArrayList<String> itemIds = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String campusId = IDUtil.getUserCampusId(id,db);

        try {
            connection = db.getConn();
            String query = "SELECT campus_item_id FROM campus_item WHERE campus_id = ?";
            statement = connection.prepareStatement(query);
            statement.setString(1, campusId);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String itemId = resultSet.getString("campus_item_id");
                itemIds.add(itemId);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            db.closeCon(statement, connection, resultSet);
        }
        return itemIds;
    }



    public Item queryItemByID(String id) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;

        Item i = null;

        try {
            cnnct = db.getConn();
            String preQueryStatement = "SELECT * FROM item WHERE item_id = ? ;";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setString(1, id);
            ResultSet rs = null;
            rs = pStmnt.executeQuery();
            if (rs.next()) {
                i = new Item();
                i.setItem_id(rs.getString("item_id"));
                i.setItem_name(rs.getString("item_name"));
                i.setItem_type(rs.getString("item_type"));
                i.setUserType(rs.getString("for_staff"));
               

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
        return i;
    }
    public boolean updateItem(String itemId, String itemName, String type, String userType) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        boolean success = false;

        try {
            cnnct = db.getConn();
            String updateStatement = "UPDATE item SET item_name=?, item_type=?, for_staff=?  WHERE item_id=?";
            pStmnt = cnnct.prepareStatement(updateStatement);

            pStmnt.setString(1, itemName);
            pStmnt.setString(2, type);
            pStmnt.setString(3, userType);
            pStmnt.setString(4, itemId);

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

    public boolean deleteItem(String itemId) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        boolean success = false;

        try {
            cnnct = db.getConn();
            String updateStatement = "UPDATE item SET active='0' WHERE item_id=?";
            pStmnt = cnnct.prepareStatement(updateStatement);

            pStmnt.setString(1, itemId);

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
