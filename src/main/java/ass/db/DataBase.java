package ass.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

// import com.mysql.jdbc.PreparedStatement;

public class DataBase {
    private String dbUrl, dbUser, dbPassword;

    public DataBase() {
        this.dbUrl = "jdbc:mysql://localhost:3306/itp4511_db?useSSL=false";
        this.dbUser = "root";
        this.dbPassword = "";
    }

    public Connection getConn() throws SQLException {

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return DriverManager.getConnection(dbUrl, dbUser, dbPassword);
    }

    public void closeCon(PreparedStatement pstmt, Connection con, ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (pstmt != null) {
            try {
                pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (con != null) {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
