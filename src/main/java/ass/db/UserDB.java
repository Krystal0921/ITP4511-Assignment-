package ass.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import ass.bean.User;
import ass.bean.UserBean;

public class UserDB {
    private DataBase db;
    private UserBean userDB;
    private String pwd;
    private String email;
    private String active;

    public UserDB(DataBase db) {
        this.db = db;
    }

    public Boolean getUser(String name) {
        String sql = "SELECT username ,user_id, role, password, active FROM user WHERE user_id = ? OR  username = ?;";
        PreparedStatement pstsmt = null;
        Connection con = null;
        Boolean haveUser = false;
        ResultSet rs = null;

        try {
            con = db.getConn();
            pstsmt = con.prepareStatement(sql);
            pstsmt.setString(1, name);
            pstsmt.setString(2, name);
            rs = pstsmt.executeQuery();

            if (rs.next()) {
                this.email = rs.getString("username");
                this.pwd = rs.getString("password");
                this.active = rs.getString("active");
                String id = rs.getString("user_id");
                String role = rs.getString("role");

                userDB = new UserBean(id, role);
                haveUser = true;
            }

        } catch (Exception e) {
            System.out.println("Exception Get ============");
            e.printStackTrace();
            System.out.println("Exception End ============");
        } finally {
            db.closeCon(pstsmt, con, null);
        }

        return haveUser;
    }

    public boolean checkActive() {
        return this.active.equals("1");
    }

    public boolean checkPassword(String password) {
        return this.pwd.equals(password);
    }

    public boolean checkEmail(String email) {
        return this.email.equals(email);
    }

    public UserBean getUserDB() {
        return this.userDB;
    }

    public boolean updatePassword(String password) {
        String sql = "UPDATE user SET password = ? WHERE user_id = ? ;";
        PreparedStatement pstsmt = null;
        Connection con = null;
        Boolean updated = false;

        try {
            con = db.getConn();
            pstsmt = con.prepareStatement(sql);
            pstsmt.setString(1, password);
            pstsmt.setString(2, userDB.getId());
            if (pstsmt.executeUpdate() != 0) {
                updated = true;
            }
        } catch (Exception e) {
            System.out.println("Exception Get ============");
            e.printStackTrace();
            System.out.println("Exception End ============");
        } finally {
            db.closeCon(pstsmt, con, null);
        }
        return updated;
    }

    public boolean updateEmail(String email) {
        String sql = "UPDATE user SET username = ? WHERE user_id = ? ;";
        PreparedStatement pstsmt = null;
        Connection con = null;
        Boolean updated = false;

        try {
            con = db.getConn();
            pstsmt = con.prepareStatement(sql);
            pstsmt.setString(1, email);
            pstsmt.setString(2, userDB.getId());
            if (pstsmt.executeUpdate() != 0) {
                updated = true;
            }
        } catch (Exception e) {
            System.out.println("Exception Get ============");
            e.printStackTrace();
            System.out.println("Exception End ============");
        } finally {
            db.closeCon(pstsmt, con, null);
        }
        return updated;
    }

    public boolean addUser(String userId, String username, String password, String role, String campusId,
            String active) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;

        boolean isSuccess = false;

        try {
            cnnct = db.getConn();
            String preQueryStatement = "INSERT INTO USER VALUES (?,?,?,?,?,?)";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setString(1, userId);
            pStmnt.setString(2, username);
            pStmnt.setString(3, password);
            pStmnt.setString(4, role);
            pStmnt.setString(5, campusId);
            pStmnt.setString(6, active);

            int rowCount = pStmnt.executeUpdate();
            if (rowCount >= 1) {
                isSuccess = true;

            }

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

    public ArrayList<User> getAllUsers() {
        ArrayList<User> userList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = db.getConn();
            String query = "SELECT u.*, c.campus_name FROM user u JOIN campus c ON u.campus_id = c.campus_id ";
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String user_id = resultSet.getString("user_id");
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");
                String role = resultSet.getString("role");
                String campusId = resultSet.getString("campus_id");
                String campusName = resultSet.getString("campus_name");
                String active = resultSet.getString("active");

                User user = new User(user_id, username, password, role, campusId, campusName, active);
                userList.add(user);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            db.closeCon(statement, connection, resultSet);
        }
        return userList;
    }

    public User queryUserByID(String id) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;

        User u = null;

        try {
            cnnct = db.getConn();
            String preQueryStatement = "SELECT u.* , c.campus_name FROM user u JOIN campus c ON u.campus_id = c.campus_id WHERE user_id =?";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setString(1, id);
            ResultSet rs = null;
            rs = pStmnt.executeQuery();
            if (rs.next()) {
                u = new User();
                u.setUser_id(rs.getString("user_id"));
                u.setUsername(rs.getString("username"));
                u.setPassword(rs.getString("password"));
                u.setRole(rs.getString("role"));
                u.setCampus_id(rs.getString("campus_id"));
                u.setCampus_name(rs.getString("campus_name"));
                u.setActive(rs.getString("active"));

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
        return u;
    }

    public boolean updateUser(String userId, String username, String password, String role, String campusId) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        boolean success = false;

        try {
            cnnct = db.getConn();
            String updateStatement = "UPDATE user SET username=?, password=?, role=?, campus_id=? WHERE user_id=?";
            pStmnt = cnnct.prepareStatement(updateStatement);

            pStmnt.setString(1, username);
            pStmnt.setString(2, password);
            pStmnt.setString(3, role);
            pStmnt.setString(4, campusId);
            pStmnt.setString(5, userId);

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

    public boolean deleteUser(String id) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        boolean success = false;

        try {
            cnnct = db.getConn();
            String updateStatement = "UPDATE user SET active='0' WHERE user_id=?";
            pStmnt = cnnct.prepareStatement(updateStatement);

            pStmnt.setString(1, id);

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
