package ass.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import ass.bean.UserBean;
import ass.db.DataBase;
import ass.db.UserDB;
import ass.utils.ServletParam;
import ass.utils.ServletUtil;

@WebServlet(name = "loginController", urlPatterns = { "/Login" })
public class LoginController extends HttpServlet {
    DataBase db;
    UserDB userDB;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        HttpSession session = req.getSession(true);
        ServletParam<Boolean> isLogout = null;
        if (session.getAttribute("user") == null) {
            ServletUtil.redirectTo(this, res, "");
            return;
        }
        isLogout = ServletUtil.getBoolParam(req, "logout");
        if (!isLogout.getValue())
            return;
        session.removeAttribute("user");
        session.setAttribute("msg", "Logout Success");
        ServletUtil.redirectTo(this, res, "index.jsp");
        return;

    }

    @SuppressWarnings("unchecked")
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        HttpSession session = req.getSession(true);

        if (session.getAttribute("user") != null) {
            UserBean u = (UserBean) session.getAttribute("user");
            ServletUtil.redirectTo(this, res, u.getRole() + "/index.jsp");
            return;
        }

        ServletParam<String> username = ServletUtil.getStringParam(req, "username");
        ServletParam<String> password = ServletUtil.getStringParam(req, "password");

        if (ServletUtil.checkEmptyParams(res, username, password)) {
            if (!userDB.getUser(username.getValue())) {
                session.setAttribute("msg", "No user Found");
                ServletUtil.forwardTo(this, req, res, "/index.jsp");
                return;
            }

            if (!userDB.checkPassword(password.getValue())) {
                session.setAttribute("msg", "Invalid username or password");
                ServletUtil.forwardTo(this, req, res, "/index.jsp");
                return;
            }
            if (!userDB.checkActive()) {
                session.setAttribute("msg", "User is not available");
                ServletUtil.forwardTo(this, req, res, "/index.jsp");
                return;
            }

            UserBean user = userDB.getUserDB();
            session.removeAttribute("user");
            session.setAttribute("user", user);
            session.setAttribute("msg", "login success");
            String role = user.getRole();
            if (role.equalsIgnoreCase("staff"))
                role = "user";
            if (role.equalsIgnoreCase("admin"))
                role = "technician";
            ServletUtil.redirectTo(this, res, role + "/index.jsp");
            return;
        }
    }

    public void init() {
        db = new DataBase();
        userDB = new UserDB(db);
    }
}
