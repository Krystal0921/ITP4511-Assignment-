package ass.utils;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import ass.db.DataBase;

public class IDUtil {

    public static String genID(String table, String header, DataBase db) {
        Connection con = null;
        PreparedStatement pstsmt = null;
        String sql = "SELECT COUNT(*) FROM " + table + " ;";
        String result = "";

        try {
            con = db.getConn();
            pstsmt = con.prepareStatement(sql);
            ResultSet rs = pstsmt.executeQuery();

            if (rs.next()) {
                int hLenght = header.length();
                int countWidth = 8 - hLenght;
                result = String.format("%s%0" + countWidth + "d", header, rs.getInt(1) + 1);
            }

        } catch (SQLException e) {
            System.out.println("SQL Error :");
            e.printStackTrace();
            System.out.println("SQL Error End");
        } finally {
            try {
                con.close();
                pstsmt.close();
            } catch (SQLException e) {
                System.out.println("SQL Error :");
                e.printStackTrace();
                System.out.println("SQL Error End");
            }

        }
        return result;
    }

    public static String getUserCampusId(String id, DataBase db) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String campusId = null;
        try {
            connection = db.getConn();
            String query = "SELECT campus_id FROM user WHERE user_id = ? ;";
            statement = connection.prepareStatement(query);
            statement.setString(1, id);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                campusId = resultSet.getString("campus_id");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            db.closeCon(statement, connection, resultSet);
        }
        return campusId;
    }
}