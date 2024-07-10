package ass.servlet.user;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ass.bean.ItemBean;
import ass.db.DataBase;
import ass.db.RecordDB;
import ass.utils.ServletParam;
import ass.utils.ServletUtil;

@WebServlet(name = "RecordController", urlPatterns = { "/user/record" })
@SuppressWarnings("unchecked")
public class RecordController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req , HttpServletResponse res) throws ServletException, IOException {
        ServletUtil.redirectTo(this, res, "user/record.jsp");
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        ServletParam<String> id = ServletUtil.getStringParam(req, "id");
        if(!ServletUtil.checkEmptyParams(res, id))return;
        RecordDB db = new RecordDB(new DataBase());
        ArrayList<ItemBean> items = db.getListItems(id.getValue());
        req.setAttribute("items", items);
        ServletUtil.forwardTo(this, req, res, "/user/recordDetail.jsp");
    }
}
