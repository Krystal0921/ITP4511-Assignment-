package ass.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import ass.bean.ItemBean;
import ass.utils.IDUtil;

public class InventoryDB {
    private DataBase db;

    public InventoryDB() {
    }

    public InventoryDB(DataBase db) {
        this.db = db;
    }

    public boolean addInventory(String campusId, String id, int qty) {
        boolean isAdded = false;
        Connection con = null;
        PreparedStatement pstmt = null;
        String sql = "INSERT INTO campus_item(campus_id , campus_item_id , item_id , status) VALUES(?,?,?,?)";
        int updated = 0;
        try {
            con = db.getConn();
            for (int i = 0; i < qty; i++) {
                String cItemId = IDUtil.genID("campus_item", "ci", db);
                pstmt = con.prepareStatement(sql);
                pstmt.setString(1, campusId);
                pstmt.setString(2, cItemId);
                pstmt.setString(3, id);
                pstmt.setString(4, "Available");
                updated += pstmt.executeUpdate();
            }
            if(updated==qty){
                isAdded=true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            isAdded = false;
        } finally {
            db.closeCon(pstmt, con, null);
        }

        return isAdded;
    }

    public ArrayList<ItemBean> getInventory(String id) {
        ArrayList<ItemBean> items = new ArrayList<>();
        String campusId = IDUtil.getUserCampusId(id, db);
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = "SELECT campus_item_id , item_id , status FROM campus_item WHERE campus_id = ? ;";
        try {
            con = db.getConn();
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, campusId);
            rs = pstmt.executeQuery();
            if (rs != null) {
                while (rs.next()) {
                    ItemBean item = new ItemBean();
                    item.setId(rs.getString("campus_item_id"));
                    item.setName(getItemName(rs.getString("item_id")));
                    boolean isAva = rs.getString("status").equals("Available");
                    item.setIsAva(isAva);
                    item.setType(getType(rs.getString("item_id")));
                    items.add(item);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.closeCon(pstmt, con, rs);
        }
        return items;
    }

    public String getItemName(String id) {
        String name = "";
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = "SELECT item_name FROM item WHERE item_id = ? ;";
        try {
            con = db.getConn();
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, id);
            rs = pstmt.executeQuery();
            if (rs != null) {
                if (rs.next()) {
                    name = rs.getString("item_name");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.closeCon(pstmt, con, rs);
        }
        return name;
    }

    public String getType(String id) {

        String type = "";
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = "SELECT item_type FROM item WHERE item_id = ? ;";
        try {
            con = db.getConn();
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, id);
            rs = pstmt.executeQuery();
            if (rs != null) {
                if (rs.next()) {
                    type = rs.getString("item_type");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.closeCon(pstmt, con, rs);
        }
        return type;

    }
}
