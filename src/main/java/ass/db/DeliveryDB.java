package ass.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import ass.bean.DeliveryBean;

public class DeliveryDB {
    private DataBase db;

    public DeliveryDB() {
    }

    public DeliveryDB(DataBase db) {
        this.db = db;
    }

    public void setDb(DataBase db) {
        this.db = db;
    }

    public ArrayList<DeliveryBean> getReadyList() {
        ArrayList<DeliveryBean> list = new ArrayList<>();
        String sql = "SELECT deliver_id, status, campus_name FROM deliver_list d INNER JOIN campus c ON c.campus_id = d.des_campus_id WHERE d.`status` = ? AND d.user_id IS NULL ;";
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            con = db.getConn();
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, "ready");
            rs = pstmt.executeQuery();
            if (rs != null) {
                while (rs.next()) {
                    DeliveryBean d = new DeliveryBean();
                    String id = rs.getString("deliver_id");
                    String status = rs.getString("status");
                    String desCampus = rs.getString("campus_name");
                    d.setId(id);
                    d.setStatus(status);
                    d.setCampus(desCampus);
                    list.add(d);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.closeCon(pstmt, con, rs);
        }

        return list;

    }

    public ArrayList<DeliveryBean> getAssignedList(String uId) {
        ArrayList<DeliveryBean> list = new ArrayList<>();
        String sql = "SELECT deliver_id, status, campus_name FROM deliver_list d INNER JOIN campus c ON c.campus_id = d.des_campus_id WHERE d.user_id = ? ;";
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            con = db.getConn();
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, uId);
            rs = pstmt.executeQuery();
            if (rs != null) {
                while (rs.next()) {
                    DeliveryBean d = new DeliveryBean();
                    String id = rs.getString("deliver_id");
                    String status = rs.getString("status");
                    String desCampus = rs.getString("campus_name");
                    d.setId(id);
                    d.setStatus(status);
                    d.setCampus(desCampus);
                    list.add(d);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.closeCon(pstmt, con, rs);
        }

        return list;

    }

    public String getBorrowId(String id) {
        String bId = "";
        String sql = "SELECT borrow_id FROM deliver_list WHERE deliver_id = ?;";
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
                    bId = rs.getString("borrow_id");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.closeCon(pstmt, con, rs);
        }

        return bId;
    }

    public void updateBorrowStatus(String id, String status) {
        String bId = getBorrowId(id);
        String sql = "UPDATE borrow_list SET status = ? WHERE borrow_id = ?;";
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            con = db.getConn();
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, status);
            pstmt.setString(2, bId);
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.closeCon(pstmt, con, null);
        }
    }

    public boolean updateStatus(String id, String status) {
        boolean isUpdate = false;

        String sql = "UPDATE deliver_list SET status = ? WHERE deliver_id = ?;";
        Connection con = null;
        PreparedStatement pstmt = null;
        if (status.equals("delivering")) {
            updateBorrowStatus(id, "waiting");
        }

        if (status.equals("arrived")) {
            updateBorrowStatus(id, "pickup");
        }

        try {
            con = db.getConn();
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, status);
            pstmt.setString(2, id);

            int result = pstmt.executeUpdate();
            if (result > 0) {
                isUpdate = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.closeCon(pstmt, con, null);
        }

        return isUpdate;
    }

    public ArrayList<DeliveryBean> getDetail(String id) {
        ArrayList<DeliveryBean> list = new ArrayList<>();
        String sql = "SELECT dli.campus_item_id , item_name , c.campus_name FROM deliver_list_detail dli " +
                "INNER JOIN campus_item ci ON dli.campus_item_id = ci.campus_item_id " +
                "INNER JOIN campus c ON ci.campus_id = c.campus_id " +
                "INNER JOIN item i on ci.item_id = i.item_id WHERE dli.deliver_id = ? ;";
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            con = db.getConn();
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, id);
            System.out.println(pstmt.toString());
            rs = pstmt.executeQuery();
            if (rs != null) {
                while (rs.next()) {
                    DeliveryBean d = new DeliveryBean();
                    String cId = rs.getString("campus_item_id");
                    String itemName = rs.getString("item_name");
                    String campus = rs.getString("campus_name");
                    d.setItemName(itemName);
                    d.setCampus(campus);
                    d.setId(cId);
                    list.add(d);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.closeCon(pstmt, con, rs);
        }

        return list;

    }

    public boolean assignCourier(String dId, String uId) {
        boolean isAssign = false;

        String sql = "UPDATE deliver_list SET user_id = ? , status = ? WHERE deliver_id = ? AND user_id IS NULL AND status = ?;";
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            con = db.getConn();
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, uId);
            pstmt.setString(2, "assigned");
            pstmt.setString(3, dId);
            pstmt.setString(4, "ready");

            int result = pstmt.executeUpdate();
            if (result > 0) {
                isAssign = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.closeCon(pstmt, con, null);
        }

        return isAssign;
    }
}
