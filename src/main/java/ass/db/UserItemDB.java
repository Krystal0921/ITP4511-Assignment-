
package ass.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import ass.bean.ItemBean;
import ass.bean.UserBean;
import ass.utils.IDUtil;

public class UserItemDB {
    private DataBase db;
    private ArrayList<String> types;

    public UserItemDB(DataBase db) {
        this.db = db;
        setTypes();
    }

    public UserItemDB() {
    }

    public void setDb(DataBase db) {
        this.db = db;
    }

    public boolean checkExist(String item_id, String table) {
        boolean isExist = false;
        String sql = "SELECT item_id FROM " + table + " WHERE item_id = ? LIMIT 1;";
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            con = db.getConn();
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, item_id);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                isExist = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.closeCon(pstmt, con, rs);
        }
        return isExist;
    }

    public String[] checkInCampus(String cItemId, String campusId) {
        String[] detail = new String[2];
        String sql = "SELECT campus_id FROM campus_item WHERE campus_item_id = ? ;";
        System.out.println(campusId + " " + cItemId);
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            con = db.getConn();
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, cItemId);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                System.out.println("have result");
                String campus_id = rs.getString("campus_id");
                if (!campus_id.equals(campusId)) {
                    detail[0] = campus_id;
                    detail[1] = "true";
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.closeCon(pstmt, con, rs);
        }
        return detail;
    }

    public boolean checkIn(String item_id, String user_id, String table) {
        boolean isExist = false;
        String sql = "SELECT item_id FROM " + table + " WHERE item_id = ? AND user_id = ? ;";
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            con = db.getConn();
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, item_id);
            pstmt.setString(2, user_id);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                isExist = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.closeCon(pstmt, con, rs);
        }
        return isExist;

    }

    public boolean checkInCart(String item_id, String user_id) {
        return checkIn(item_id, user_id, "cart");
    }

    public boolean checkInWish(String item_id, String user_id) {
        return checkIn(item_id, user_id, "wish_list");
    }

    public boolean checkAva(String item_id) {
        boolean isAva = false;
        String sql = "SELECT status FROM campus_item WHERE item_id = ? AND status=? LIMIT 1;";
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            con = db.getConn();
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, item_id);
            pstmt.setString(2, "Available");
            rs = pstmt.executeQuery();
            if (rs.next()) {
                isAva = true;
                System.out.println(isAva);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.closeCon(pstmt, con, rs);
        }

        return isAva;
    }

    public boolean addToWish(String item_id, String user_id) {
        return addTo(item_id, user_id, "wish_list");
    }

    public boolean addToCart(String item_id, String user_id) {
        return addTo(item_id, user_id, "cart");
    }

    public boolean addTo(String item_id, String user_id, String table) {
        boolean isAdded = false;
        String sql = "INSERT INTO " + table + " (item_id, user_id) VALUES (?, ?) ;";

        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            con = db.getConn();
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, item_id);
            pstmt.setString(2, user_id);
            int update = pstmt.executeUpdate();
            if (update > 0) {
                isAdded = true;

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.closeCon(pstmt, con, null);
        }
        return isAdded;
    }

    public boolean removeItem(String item_id, String user_id, String table) {
        boolean isRemoved = false;
        String sql = "DELETE FROM " + table + " WHERE item_id = ? AND user_id = ? ;";
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            con = db.getConn();
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, item_id);
            pstmt.setString(2, user_id);
            int result = pstmt.executeUpdate();
            if (result > 0) {
                isRemoved = true;
            }
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            db.closeCon(pstmt, con, null);
        }

        return isRemoved;
    }

    public boolean removeWishItem(String item_id, String user_id) {
        return removeItem(item_id, user_id, "wish_list");
    }

    public boolean removeCartItem(String item_id, String user_id) {
        return removeItem(item_id, user_id, "cart");
    }

    public boolean checkOut(List<String> item_ids, String user_id, String pickUpTime, String retunTime) {
        boolean isCheckOut = true;
        ArrayList<ItemBean> campusItems = new ArrayList<>();
        String campusId = IDUtil.getUserCampusId(user_id, db);
        String sql = "SELECT campus_item_id FROM campus_item WHERE item_id = ? AND status = ? LIMIT 1;";
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            con = db.getConn();
            pstmt = con.prepareStatement(sql);
            for (String item_id : item_ids) {
                ItemBean item = new ItemBean();
                item.setId(item_id);

                pstmt.setString(1, item_id);
                pstmt.setString(2, "Available");
                rs = pstmt.executeQuery();
                if (rs.next()) {
                    String campus_item_id = rs.getString("campus_item_id");
                    item.setId(item_id);
                    item.setCItemId(campus_item_id);
                    item.setIsAva(true);
                    item.setInCampus(checkInCampus(campus_item_id, campusId));
                    campusItems.add(item);
                    continue;
                }
                item.setIsAva(false);
                campusItems.add(item);
                if (isCheckOut) {
                    isCheckOut = false;
                }
            }
        } catch (Exception e) {
            // e.printStackTrace();
        } finally {
            db.closeCon(pstmt, con, rs);
        }
        String borrowId = IDUtil.genID("borrow_list", "b", db);
        String deliverId = IDUtil.genID("deliver_list", "d", db);

        sql = "INSERT INTO borrow_list (borrow_id, user_id,status ,pick_up_date, return_date) VALUES (?, ?, ?, ?, ?) ;";
        try {
            con = db.getConn();
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, borrowId);
            pstmt.setString(2, user_id);
            pstmt.setString(3, "pending");
            pstmt.setString(4, pickUpTime);
            pstmt.setString(5, retunTime);
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.closeCon(pstmt, con, null);
        }
        createDeliverList(deliverId, campusId, campusItems, borrowId);
        sql = "INSERT INTO borrow_list_detail (borrow_id, campus_item_id) VALUES (?, ?) ;";
        try {
            con = db.getConn();
            pstmt = con.prepareStatement(sql);
            for (ItemBean item : campusItems) {
                if (!item.getIsAva() && item.getCItemId() == null)
                    continue;
                pstmt.setString(1, borrowId);
                pstmt.setString(2, item.getCItemId());
                pstmt.executeUpdate();
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.closeCon(pstmt, con, null);
        }

        sql = "UPDATE campus_item SET status = ? WHERE campus_item_id = ? ;";
        try {
            con = db.getConn();
            pstmt = con.prepareStatement(sql);
            for (ItemBean item : campusItems) {
                if (!item.getIsAva())
                    continue;
                pstmt.setString(1, "Unavailable");
                pstmt.setString(2, item.getCItemId());
                pstmt.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.closeCon(pstmt, con, null);
        }
        sql = "DELETE FROM cart WHERE item_id = ? AND user_id = ? ;";
        try {
            con = db.getConn();
            pstmt = con.prepareStatement(sql);
            for (ItemBean item : campusItems) {
                if (!item.getIsAva())
                    continue;
                pstmt.setString(1, item.getId());
                pstmt.setString(2, user_id);
                pstmt.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.closeCon(pstmt, con, null);
        }

        return isCheckOut;
    }

    public void createDeliverList(String deliverId, String campus_id, ArrayList<ItemBean> campusItems,
            String borrowId) {
        String sql = "INSERT INTO deliver_list (deliver_id, status , des_campus_id , borrow_id) VALUES (?, ?, ?, ?) ;";
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            con = db.getConn();
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, deliverId);
            pstmt.setString(2, "pending");
            pstmt.setString(3, campus_id);
            pstmt.setString(4, borrowId);

            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.closeCon(pstmt, con, null);
        }

        sql = "INSERT INTO deliver_list_detail (deliver_id, campus_item_id ,from_campus_id) VALUES (?, ?, ?) ;";
        try {
            con = db.getConn();
            pstmt = con.prepareStatement(sql);
            for (ItemBean item : campusItems) {
                if (!item.getInCampus())
                    continue;
                pstmt.setString(1, deliverId);
                pstmt.setString(2, item.getCItemId());
                pstmt.setString(3, item.getCampusId());
                pstmt.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.closeCon(pstmt, con, null);
        }
    }

    public void setTypes() {
        ArrayList<String> types = new ArrayList<>();
        String sql = "SELECT DISTINCT item_type FROM item ; ";
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            con = db.getConn();
            pstmt = con.prepareStatement(sql);
            rs = pstmt.executeQuery();
            if (rs != null) {
                try {
                    while (rs.next()) {
                        types.add(rs.getString("item_type"));
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.closeCon(pstmt, con, rs);
        }
        if (types.size() > 0) {
            this.types = types;
        }

    }

    public ArrayList<String> getTypes() {
        return types;
    }

    public ArrayList<ItemBean> getTypeItems(String type, HttpSession session) throws SQLException {
        UserBean u = (UserBean) session.getAttribute("user");
        boolean isStaff = u.getRole().equalsIgnoreCase("staff") ? true : false;
        ArrayList<ItemBean> items = new ArrayList<>();
        if (types == null)
            return items;
        int indexOfType = types.indexOf(type);
        String isVaildType = indexOfType > -1 ? type : "";
        String sql = "SELECT item_id , item_name , item_type FROM item WHERE ";

        // not for staff
        if (!isStaff) {
            sql += "for_staff = \"N\" ";
        }
        if (!isStaff && !isVaildType.isEmpty()) {
            sql += "AND ";
        }

        if (!isVaildType.isEmpty()) {
            sql += "item_type = ? AND ";
        }

        if (!isStaff && isVaildType.isEmpty()) {
            sql += "AND ";
        }

        sql += "active= '1' ;";
        System.out.println(sql);
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            con = db.getConn();
            pstmt = con.prepareStatement(sql);
            if (!isVaildType.isEmpty()) {
                pstmt.setString(1, isVaildType);
            }
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ItemBean item = new ItemBean();
                item.setId(rs.getString("item_id"));
                item.setName(rs.getString("item_name"));
                item.setType(rs.getString("item_type"));
                items.add(item);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.closeCon(pstmt, con, rs);
        }
        return items;
    }

    public ArrayList<ItemBean> getItems(String user_id, String table) {
        ArrayList<ItemBean> items = new ArrayList<>();
        ArrayList<String> item_ids = new ArrayList<>();
        String sql = "SELECT item_id FROM " + table + " WHERE user_id = ? ;";
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            con = db.getConn();
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, user_id);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                item_ids.add(rs.getString("item_id"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.closeCon(pstmt, con, rs);
        }

        for (String item_id : item_ids) {
            ItemBean item = new ItemBean();
            sql = "SELECT item_name, item_type FROM item WHERE item_id = ? ;";
            try {
                con = db.getConn();
                pstmt = con.prepareStatement(sql);
                pstmt.setString(1, item_id);
                rs = pstmt.executeQuery();
                if (rs.next()) {
                    item.setId(item_id);
                    item.setName(rs.getString("item_name"));
                    item.setType(rs.getString("item_type"));
                    items.add(item);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                db.closeCon(pstmt, con, rs);
            }
        }
        return items;
    }

    public ArrayList<ItemBean> getWishItem(HttpSession session) {
        UserBean u = (UserBean) session.getAttribute("user");
        return getItems(u.getId(), "wish_list");

    }

    public ArrayList<ItemBean> getCartItem(HttpSession session) {
        UserBean u = (UserBean) session.getAttribute("user");
        return getItems(u.getId(), "cart");

    }
}
