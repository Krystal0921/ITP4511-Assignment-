package ass.servlet.courier;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mysql.fabric.xmlrpc.base.Array;

import ass.bean.DeliveryBean;
import ass.bean.UserBean;
import ass.db.DataBase;
import ass.db.DeliveryDB;
import ass.utils.ServletParam;
import ass.utils.ServletUtil;

@WebServlet(name = "DeliverController", urlPatterns = { "/courier/delivery" })
public class DeliverController extends HttpServlet {
    private DeliveryDB db;

    @SuppressWarnings("unchecked")
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        System.out.println("doPost called");

        ServletParam<String> action = ServletUtil.getStringParam(req, "action");
        ServletParam<String> id = ServletUtil.getStringParam(req, "id");
        System.out.println(action.isValid() + "  Action=========");
        System.out.println(action.getValue() + "  Action=========");
        System.out.println(id.isValid() + "  id=========");
        if (!ServletUtil.checkEmptyParams(res, action, id)) {
            return;
        }
        switch (action.getValue()) {
            case "accept":
                System.out.println("accpet called");

                doAccpet(req, res, id.getValue());
                break;
            case "view":
                System.out.println("view called");
                doView(req, res, id.getValue());
                break;

            case "update":
                System.out.println("update called");
                doUpdate(req, res, id.getValue());
                break;

            default:
                System.out.println("default called");
                break;
        }
    }

    public void doUpdate(HttpServletRequest req, HttpServletResponse res, String id) {
        System.out.println("doUpdate called");
        ServletParam<String> status = ServletUtil.getStringParam(req, "status");
        HttpSession session = req.getSession();
        if (!status.isValid()) {
            session.setAttribute("msg", "Server Busy, Please try again later");
            ServletUtil.redirectTo(this, res, "courier/assignedList.jsp");
            return;
        }
        if (!db.updateStatus(id, status.getValue())) {
            session.setAttribute("msg", "Server Busy, Please try again later");
            ServletUtil.redirectTo(this, res, "courier/assignedList.jsp");
            return;
        }
        session.setAttribute("msg", " Updated");
        ServletUtil.redirectTo(this, res, "courier/assignedList.jsp");
        return;

    }

    public void doView(HttpServletRequest req, HttpServletResponse res, String id) {
        System.out.println("doView called");
        HttpSession session = req.getSession();
        ArrayList<DeliveryBean> list = db.getDetail(id);
        if (list.size() == 0) {
            session.setAttribute("msg", "Server Busy, Please try again later");
            ServletUtil.redirectTo(this, res, "courier/assignedList.jsp");
            return;
        }
        req.setAttribute("record", list);
        ServletUtil.forwardTo(this, req, res, "/courier/detail.jsp");
        return;

    }

    public void doAccpet(HttpServletRequest req, HttpServletResponse res, String id) {
        System.out.println("doAccpet called");
        HttpSession session = req.getSession();
        UserBean u = (UserBean) session.getAttribute("user");
        String uId = u.getId();
        if (!db.assignCourier(id, uId)) {
            session.setAttribute("msg", "Server Busy, Please try again later");
            ServletUtil.redirectTo(this, res, "courier/orderList.jsp");
            return;
        }
        session.setAttribute("msg", " Accepted");
        ServletUtil.redirectTo(this, res, "courier/orderList.jsp");
        return;

    }

    public void init() {
        db = new DeliveryDB(new DataBase());
    }
}
