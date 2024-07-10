package ass.servlet.user;

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

@SuppressWarnings("unchecked")
@WebServlet(name = "personalDataController", urlPatterns = { "/common/update" })
public class PersonalDataController extends HttpServlet {
    private UserBean u;
    private UserDB userDB;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        System.out.println(this.getServletContext().getContextPath());
        HttpSession session = req.getSession(true);
        if (!ServletUtil.checkLogin(session)) {
            ServletUtil.redirectTo(this, res, "index.jsp");
            return;
        }
        u = (UserBean) session.getAttribute("user");
        if (!userDB.getUser(u.getId())) {
            session.removeAttribute("user");
            session.setAttribute("msg", "User not found");
            ServletUtil.redirectTo(this, res, "index.jsp");
            return;
        }
        ServletParam<String> action = ServletUtil.getStringParam(req, "action");
        switch (action.getValue()) {
            case "updatePassword":
                handleUpdatePassword(session, req, res);
                break;
            case "updateEmail":
                handleUpdateEmail(session, req, res);
                break;

            default:
                session.setAttribute("msg", "invalid action");
                ServletUtil.redirectTo(this, res, "");
                break;
        }
    }

    public void handleUpdatePassword(HttpSession session, HttpServletRequest req, HttpServletResponse res) {

        ServletParam<String> oldPassword = ServletUtil.getStringParam(req, "cur-password");
        ServletParam<String> newPassword = ServletUtil.getStringParam(req, "new-password");
        ServletParam<String> conPassword = ServletUtil.getStringParam(req, "con-password");
        if (!ServletUtil.checkEmptyParams(res, oldPassword, newPassword, conPassword))
            return;

        if (!newPassword.getValue().equals(conPassword.getValue())) {
            session.setAttribute("msg", "New password not match");
            ServletUtil.redirectTo(this, res, "user/setting.jsp");
            return;
        }
        if (oldPassword.getValue().equals(newPassword.getValue())) {
            session.setAttribute("msg", "New password can not be the same as the origin");
            ServletUtil.redirectTo(this, res, "user/setting.jsp");
            return;
        }

        if (!userDB.checkPassword(oldPassword.getValue())) {
            session.setAttribute("msg", "Invalid password");
            ServletUtil.redirectTo(this, res, "user/setting.jsp");
            return;
        }
        if (!userDB.updatePassword(newPassword.getValue().trim())) {
            session.setAttribute("msg", "Update password failed");
            ServletUtil.redirectTo(this, res, "user/setting.jsp");
            return;
        }
        session.setAttribute("msg", "Update password success");
        session.removeAttribute("user");
        ServletUtil.redirectTo(this, res, "index.jsp");
        return;

    }

    public void handleUpdateEmail(HttpSession session, HttpServletRequest req, HttpServletResponse res) {
        ServletParam<String> id = ServletUtil.getStringParam(req, "user-id");
        ServletParam<String> oldEmail = ServletUtil.getStringParam(req, "curr-email");
        ServletParam<String> newEmail = ServletUtil.getStringParam(req, "email");
        ServletParam<String> conEmail = ServletUtil.getStringParam(req, "con-email");
        ServletParam<String> password = ServletUtil.getStringParam(req, "password");
        if (!ServletUtil.checkEmptyParams(res, id, newEmail, conEmail, password)) {
            return;
        }
        UserBean u = (UserBean) session.getAttribute("user");
        if(!u.getId().equals(id.getValue())) {
            session.setAttribute("msg", "Invalid user id");
            ServletUtil.redirectTo(this, res, "user/setting.jsp");
            return;
        }

        if (!newEmail.getValue().equals(conEmail.getValue())) {
            session.setAttribute("msg", "Email/Username not match");
            ServletUtil.redirectTo(this, res, "common/setting.jsp");
            return;
        }
        if (oldEmail.getValue().equals(newEmail.getValue())) {
            session.setAttribute("msg", "Email/Username can not be the same as old one");
            ServletUtil.redirectTo(this, res, "common/setting.jsp");
            return;
        }
        if(!userDB.checkEmail(oldEmail.getValue())) {
            session.setAttribute("msg", "Invalid password or email");
            ServletUtil.redirectTo(this, res, "common/setting.jsp");
            return;
        }   
        if (!userDB.checkPassword(password.getValue())) {
            session.setAttribute("msg", "Invalid password or email");
            ServletUtil.redirectTo(this, res, "common/setting.jsp");
            return;
        }
        if (!userDB.updateEmail(newEmail.getValue().trim())) {
            session.setAttribute("msg", "Update email/username failed");
            ServletUtil.redirectTo(this, res, "common/setting.jsp");
            return;
        }
        session.setAttribute("msg", "Update email/username success");
        session.removeAttribute("user");
        ServletUtil.redirectTo(this, res, "index.jsp");
        return;

    }

    public void init() {
        DataBase db = new DataBase();
        userDB = new UserDB(db);
    }
}
