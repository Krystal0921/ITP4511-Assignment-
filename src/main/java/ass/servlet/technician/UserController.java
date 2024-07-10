/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ass.servlet.technician;

import ass.db.UserDB;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ass.db.DataBase;

/**
 *
 * @author LYF00
 */
@WebServlet(name = "UserController", urlPatterns = { "/technician/User" })
public class UserController extends HttpServlet {

    private UserDB userDB;
    private DataBase db;

    @Override
    public void init() {
        db = new DataBase();
        userDB = new UserDB(db);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {

            String action = request.getParameter("action");

            if (null != action) {
                switch (action) {
                    case "create":
                        doCreateUser(request, response);
                        break;
                    case "edit":
                        doEditUser(request, response);
                        break;
                    case "delete":
                        doDeleteUser(request, response);
                        break;
                    default:
                        break;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(ItemController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void doCreateUser(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String role = request.getParameter("role");
        String campus = request.getParameter("campus");

        String userId = ass.utils.IDUtil.genID("user", "u", db);
        boolean inserted = userDB.addUser(userId, username, password, role, campus, "1");

        if (inserted) {
            String successMessage = "Create user success";
            String redirectURL = "admin_userAcc.jsp";
            String script = "<script>alert('" + successMessage + "'); window.location.href='" + redirectURL
                    + "';</script>";
            response.getWriter().println(script);
        } else {
            String successMessage = "Cannot user item, please try again!";
            String redirectURL = "userAccountForm.jsp";
            String script = "<script>alert('" + successMessage + "'); window.location.href='" + redirectURL
                    + "';</script>";
            response.getWriter().println(script);
        }

    }

    private void doEditUser(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String role = request.getParameter("role");
        String campus = request.getParameter("campus");

        String userId = request.getParameter("userId");

        boolean edited = userDB.updateUser(userId, username, password, role, campus);
        if (edited) {
            String successMessage = "Edit user success";
            String redirectURL = "admin_userAcc.jsp";
            String script = "<script>alert('" + successMessage + "'); window.location.href='" + redirectURL
                    + "';</script>";
            response.getWriter().println(script);
        } else {
            String successMessage = "Cannot user item, please try again!";
            String redirectURL = "admin_userAcc.jsp";
            String script = "<script>alert('" + successMessage + "'); window.location.href='" + redirectURL
                    + "';</script>";
            response.getWriter().println(script);
        }
    }

    private void doDeleteUser(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {

        String userId = request.getParameter("userId");

        boolean deleted = userDB.deleteUser(userId);

        if (deleted) {
            String successMessage = "Delete user success";
            String redirectURL = "admin_userAcc.jsp";
            String script = "<script>alert('" + successMessage + "'); window.location.href='" + redirectURL
                    + "';</script>";
            response.getWriter().println(script);
        } else {
            String successMessage = "Cannot delete user, please try again!";
            String redirectURL = "admin_userAcc.jsp";
            String script = "<script>alert('" + successMessage + "'); window.location.href='" + redirectURL
                    + "';</script>";
            response.getWriter().println(script);
        }
    }
}
