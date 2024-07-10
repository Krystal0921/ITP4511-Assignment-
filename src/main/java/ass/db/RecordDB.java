package ass.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.mysql.fabric.xmlrpc.base.Array;

import ass.bean.ItemBean;
import ass.bean.Record;
import ass.bean.RecordBean;
import ass.utils.IDUtil;

public class RecordDB {

    private DataBase db;

    public RecordDB(DataBase db) {
        this.db = db;
    }

    public ArrayList<RecordBean> getRecord(String id) {
        ArrayList<RecordBean> records = new ArrayList<>();
        String sql = "SELECT borrow_id, status, pick_up_date, return_date FROM borrow_list WHERE user_id = ?";
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            con = db.getConn();
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, id);
            rs = pstmt.executeQuery();
            if (rs != null) {
                while (rs.next()) {
                    RecordBean record = new RecordBean();
                    String bId = rs.getString("borrow_id");
                    String status = rs.getString("status");
                    String returnDate = rs.getString("return_date");
                    String pickUpDate = rs.getString("pick_up_date");
                    record.setId(bId);
                    record.setStatusString(status);
                    record.setPickUpDate(pickUpDate);
                    record.setReturnDate(returnDate);
                    records.add(record);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.closeCon(pstmt, con, rs);
        }

        return records;
    }

    public ArrayList<RecordBean> getCRecords(String id) {
        ArrayList<RecordBean> records = new ArrayList<>();
        String campusID = IDUtil.getUserCampusId(id, db);
        String sql = "SELECT u.user_id, borrow_id, status, pick_up_date, return_date ,c.campus_name " +
                "FROM borrow_list b " +
                "INNER JOIN user u ON b.user_id = u.user_id " +
                "INNER JOIN campus c ON c.campus_id = u.campus_id " +
                "WHERE c.campus_id= ? ;";
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            con = db.getConn();
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, campusID);
            rs = pstmt.executeQuery();
            if (rs != null) {
                while (rs.next()) {
                    RecordBean record = new RecordBean();
                    String bId = rs.getString("user_id");
                    String status = rs.getString("status");
                    String returnDate = rs.getString("return_date");
                    String pickUpDate = rs.getString("pick_up_date");
                    String campusName = rs.getString("campus_name");
                    String bid = rs.getString("borrow_id");
                    record.setId(bId);
                    record.setStatus(status);
                    record.setPickUpDate(pickUpDate);
                    record.setReturnDate(returnDate);
                    record.setCampusName(campusName);
                    record.setRecordId(bid);
                    records.add(record);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.closeCon(pstmt, con, rs);
        }

        return records;
    }

    public boolean updateRecord(String id, String status) {
        boolean isUpdated = false;
        String sql = "UPDATE borrow_list SET status = ? WHERE borrow_id = ?";
        Connection con = null;
        PreparedStatement pstmt = null;
        if (status.equalsIgnoreCase("complete")) {
            changeItemsStatus(id);
        }
        try {
            con = db.getConn();
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, status);
            pstmt.setString(2, id);
            pstmt.executeUpdate();
            isUpdated = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.closeCon(pstmt, con, null);
        }
        if(status.equalsIgnoreCase("approved")) updateDeliver(id);
        return isUpdated;
    }
    
    public void updateDeliver(String id){
        String sql = "UPDATE deliver_list SET status = ? WHERE borrow_id = ?";
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            con = db.getConn();
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, "ready");
            pstmt.setString(2, id);
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.closeCon(pstmt, con, null);
        }
    }


    public void changeItemsStatus(String id) {
        String sql = "SELECT campus_item_id FROM borrow_list_detail WHERE borrow_id = ?";
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            con = db.getConn();
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, id);
            rs = pstmt.executeQuery();
            if (rs != null) {
                while (rs.next()) {
                    String bId = rs.getString("campus_item_id");
                    String sql2 = "UPDATE campus_item SET status = 'Available' WHERE campus_item_id = ?";
                    PreparedStatement pstmt2 = con.prepareStatement(sql2);
                    pstmt2.setString(1, bId);
                    pstmt2.executeUpdate();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.closeCon(pstmt, con, rs);
        }
    }

    public ArrayList<ItemBean> getListItems(String id) {

        ArrayList<String> CItemIDs = getCItemIds(id);
        ArrayList<String> itemIDs = getItemId(CItemIDs);
        ArrayList<ItemBean> items = getItems(itemIDs);

        return items;
    }

    public ArrayList<String> getCItemIds(String id) {
        ArrayList<String> itemID = new ArrayList<>();
        String sql = "SELECT campus_item_id FROM borrow_list_detail WHERE borrow_id = ?";
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            con = db.getConn();
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, id);
            rs = pstmt.executeQuery();
            if (rs != null) {
                while (rs.next()) {
                    String bId = rs.getString("campus_item_id");
                    itemID.add(bId);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.closeCon(pstmt, con, rs);
        }
        return itemID;
    }

    public ArrayList<String> getItemId(ArrayList<String> CItemID) {
        ArrayList<String> itemID = new ArrayList<>();
        String sql = "SELECT item_id FROM campus_item WHERE campus_item_id = ?";
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            con = db.getConn();
            pstmt = con.prepareStatement(sql);
            for (String id : CItemID) {
                pstmt.setString(1, id);
                rs = pstmt.executeQuery();
                if (rs != null) {
                    while (rs.next()) {
                        String iId = rs.getString("item_id");
                        itemID.add(iId);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.closeCon(pstmt, con, rs);
        }
        return itemID;
    }

    // get item detail by item id
    public ArrayList<ItemBean> getItems(ArrayList<String> itemID) {
        ArrayList<ItemBean> items = new ArrayList<>();
        String sql = "SELECT item_name, item_type FROM item WHERE item_id = ?";
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            con = db.getConn();
            pstmt = con.prepareStatement(sql);
            for (String id : itemID) {
                pstmt.setString(1, id);
                rs = pstmt.executeQuery();
                if (rs != null) {
                    while (rs.next()) {
                        ItemBean item = new ItemBean();
                        String iName = rs.getString("item_name");
                        String iType = rs.getString("item_type");
                        item.setName(iName);
                        item.setType(iType);
                        items.add(item);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.closeCon(pstmt, con, rs);
        }
        return items;
    }

    public ArrayList<Record> getVenueRecord(String campusId, String month, String year) {
        ArrayList<Record> records = new ArrayList<>();
        String sql = "SELECT bl.borrow_id, bl.user_id, bl.pick_up_date, bl.status, bd.campus_item_id, i.item_name,\n"
                + "c.campus_name\n "
                + "FROM itp4511_db.borrow_list AS bl "
                + "JOIN itp4511_db.borrow_list_detail AS bd ON bl.borrow_id = bd.borrow_id "
                + "JOIN itp4511_db.campus_item AS ci ON bd.campus_item_id = ci.campus_item_id "
                + "JOIN itp4511_db.item AS i ON ci.item_id = i.item_id "
                + "JOIN itp4511_db.campus  c on c.campus_id  = ci.campus_id "
                + "WHERE EXTRACT(MONTH FROM pick_up_date) = ? "
                + "AND EXTRACT(YEAR FROM pick_up_date) = ? "
                + "AND ci.campus_id = ?";

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            con = db.getConn();
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, month);
            pstmt.setString(2, year);
            pstmt.setString(3, campusId);
            rs = pstmt.executeQuery();
            if (rs != null) {
                while (rs.next()) {
                    Record record = new Record();
                    String bId = rs.getString("borrow_id");
                    String userId = rs.getString("user_id");
                    String status = rs.getString("status");
                    String pickUpDate = rs.getString("pick_up_date");
                    String itemId = rs.getString("campus_item_id");
                    String itemName = rs.getString("item_name");
                    String campusName = rs.getString("campus_name");

                    record.setBorrow_id(bId);
                    record.setCampus_id(campusId);
                    record.setCampus_item_id(itemId);
                    record.setItem_name(itemName);
                    record.setPick_up_date(pickUpDate);
                    record.setStatus(status);
                    record.setUser_id(userId);
                    record.setCampusName(campusName);

                    records.add(record);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.closeCon(pstmt, con, rs);
        }

        return records;
    }
}
