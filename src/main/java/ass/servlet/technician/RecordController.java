package ass.servlet.technician;

import ass.bean.Record;
import ass.db.DataBase;
import ass.db.RecordDB;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ass.bean.ItemBean;

import ass.utils.ServletParam;
import ass.utils.ServletUtil;

/**
 *
 * @author LYF00
 */
@SuppressWarnings("unchecked")
@WebServlet(name = "ReportRecordController", urlPatterns = {"/technician/record"})
public class RecordController extends HttpServlet {

    private RecordDB db;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        ServletUtil.redirectTo(this, res, "user/record.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        ServletParam<String> action = ServletUtil.getStringParam(req, "action");
        if (!ServletUtil.checkEmptyParams(res, action)) {
            return;
        }
        switch (action.getValue()) {
            case "view":
                doGetDetail(req, res);
                break;
            case "update":
                doUpdate(req, res);
                break;
            case "venue":
                System.out.println("doVenue");
                dogetVenueRecord(req, res);
            default:
                break;
        }

    }

    public void doGetDetail(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        ServletParam<String> id = ServletUtil.getStringParam(req, "bId");
        if (!ServletUtil.checkEmptyParams(res, id)) {
            return;
        }
        ArrayList<ItemBean> items = db.getListItems(id.getValue());
        req.setAttribute("items", items);
        ServletUtil.forwardTo(this, req, res, "/technician/recordDetail.jsp");
    }

    public void doUpdate(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        HttpSession session = req.getSession();
        ServletParam<String> id = ServletUtil.getStringParam(req, "bId");
        if (!ServletUtil.checkEmptyParams(res, id)) {
            return;
        }

        ServletParam<String> status = ServletUtil.getStringParam(req, "status");
        if (!ServletUtil.checkEmptyParams(res, status)) {
            return;
        }

        if (!db.updateRecord(id.getValue(), status.getValue())) {
            session.setAttribute("msg", "Server Busy, Please try again later");
            ServletUtil.redirectTo(this, res, "technician/admin_bookingRecord.jsp");
            return;
        }
        session.setAttribute("msg", "Update Success");
        ServletUtil.redirectTo(this, res, "technician/admin_bookingRecord.jsp");
    }

    public void dogetVenueRecord(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

    String campusId = request.getParameter("venueId");
    String monthYear = request.getParameter("month");
    String month = monthYear.split("-")[1];
    String year = monthYear.split("-")[0];
    ArrayList<Record> record = db.getVenueRecord(campusId, month, year);
    int recordCount = record.size();

    response.setContentType("text/html");
    PrintWriter out = response.getWriter();
    out.println("<head>");
    out.println("<style>");
    out.println(".venue-table {");
    out.println("    border-collapse: collapse;");
    out.println("    width: 100%;");
    out.println("}");
    out.println(".venue-table th {");
    out.println("    background-color: #f2f2f2;");
    out.println("    font-weight: bold;");
    out.println("    padding: 8px;");
    out.println("    border: 1px solid #ccc;");
    out.println("}");
    out.println(".venue-table td {");
    out.println("    padding: 8px;");
    out.println("    border: 1px solid #ccc;");
    out.println("}");
    out.println("</style>");
    out.println("</head>");
    out.println("<body>");

    if (recordCount > 0) {
        out.println("<h1>The Booking Rate is " + recordCount + "</h1>");
        out.println("<table class=\"venue-table\">");

        out.println("<tr>");
        out.println("<th>Borrow number</th>");
        out.println("<th>User</th>");
        out.println("<th>Campus Name</th>");
        out.println("<th>Item Id</th>");
        out.println("<th>Item Name</th>");
        out.println("<th>Date</th>");
        out.println("<th>Status</th>");
        out.println("</tr>");

        for (Record r : record) {
            out.println("<tr>");
            out.println("<td>" + r.getBorrow_id() + "</td>");
            out.println("<td>" + r.getUser_id() + "</td>");
            out.println("<td>" + r.getCampusName() + "</td>");
            out.println("<td>" + r.getCampus_item_id() + "</td>");
            out.println("<td>" + r.getItem_name() + "</td>");
            out.println("<td>" + r.getPick_up_date() + "</td>");
            out.println("<td>" + r.getStatus() + "</td>");
            out.println("</tr>");
        }

        out.println("</table>");
    } else {
        out.println("<h1>No results found!</h1>");
    }
    out.println("<a href=\"bookingReport.jsp\">Go Back</a>");
    
    out.println("</body>");
    out.close();
}

    public void init() {
        db = new RecordDB(new DataBase());
        System.out.println("RecordController init");
    }
}
