/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ass.servlet.technician;

import java.io.IOException;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ass.db.DataBase;
import ass.db.ReportDB;

/**
 *
 * @author LYF00
 */
@WebServlet(name = "ReportController", urlPatterns = { "/technician/Report" })
public class ReportController extends HttpServlet {

    private ReportDB reportDB;
    private DataBase db;

    @Override
    public void init() {
        db = new DataBase();
        reportDB = new ReportDB(db);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {

            String action = request.getParameter("action");

            if (null != action) {
                switch (action) {
                    case "create":
                        doCreateReport(request, response);
                        break;
                    case "confrim":
                        doUpdateReport(request, response);
                        break;

                    default:
                        break;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(ItemController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void doCreateReport(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        String equipmentId = request.getParameter("equipmentId");
        String createBy = request.getParameter("createBy");
        String description = request.getParameter("description");
        LocalDateTime localDateTime = LocalDateTime.now();
        String date = localDateTime.toString();

        String reportId = ass.utils.IDUtil.genID("damage_report", "r", db);
        boolean inserted = reportDB.addReport(reportId, equipmentId, description, createBy, date);

        if (inserted) {
            String successMessage = "Create report success";
            String redirectURL = "admin_damageReport.jsp";
            String script = "<script>alert('" + successMessage + "'); window.location.href='" + redirectURL
                    + "';</script>";
            response.getWriter().println(script);
        } else {
            String successMessage = "Cannot add report, please try again!";
            String redirectURL = "damageReportForm.jsp";
            String script = "<script>alert('" + successMessage + "'); window.location.href='" + redirectURL
                    + "';</script>";
            response.getWriter().println(script);
        }

    }
    
    
       private void doUpdateReport(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        String reportId = request.getParameter("rId");
        
        boolean confrim = reportDB.updateReport(reportId);

        if (confrim) {
            String successMessage = "Confrim report success";
            String redirectURL = "admin_damageReport.jsp";
            String script = "<script>alert('" + successMessage + "'); window.location.href='" + redirectURL
                    + "';</script>";
            response.getWriter().println(script);
        } else {
            String successMessage = "Cannot confrim report, please try again!";
            String redirectURL = "damageReportForm.jsp";
            String script = "<script>alert('" + successMessage + "'); window.location.href='" + redirectURL
                    + "';</script>";
            response.getWriter().println(script);
        }

    }

}
