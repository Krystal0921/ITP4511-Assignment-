<%-- 
    Document   : admin_damageReport
    Created on : 2024年4月12日, 下午4:13:47
    Author     : LYF00
--%>


<%@page import="ass.bean.Report"%>
<%@page import="java.util.ArrayList"%>
<%@page import="ass.db.ReportDB"%>
<%@page import="ass.db.DataBase"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    if( !ServletUtil.checkLogin(session)){
        response.sendRedirect("../index.jsp");
        return;
    }
    if(session.getAttribute("msg") != null){
        out.println("<script> alert( ' " + session.getAttribute("msg") + " ' )</script>");
        session.removeAttribute("msg");
    }
    ReportDB db = new ReportDB(new DataBase());
    ArrayList<Report> reportList = db.getAllReport();
%>

<!DOCTYPE html>
<html>
    <head>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Damage Report</title>
        <link rel="stylesheet" href="../style/item.css">
        <link rel="stylesheet" href="../style/damageReport.css">

            
    </head>

    <body class="bg-light">
        <%@include file="../component/header.jsp" %>
        <div class="container">
            <h2>Damage Report</h2>
            <table class="blue">
                <thead>
                    <tr>
                        <th>Report ID</th>
                        <th>Equipment ID</th>
                        <th>Equipment Name</th>
                        <th>Create By</th>
                        <th>Status</th>
                        <th>    </th>
                    </tr>
                </thead>
                <tbody>
                 
                    <%
                        if(reportList.size() == 0){
                            out.println("<tr><td colspan='6' class='text-center'>No Report Found</td></tr>");
                        }
                        for (Report report : reportList) {
                            String rId = report.getReport_id();
                          
                    %>
                    <tr>
                        <td><%= report.getReport_id()%></td>
                        <td><%= report.getItem_id()%></td>
                        <td><%= report.getItem_name()%></td>
                        <td><%= report.getUser_id()%></td>
                        <td><%= report.getStatus()%></td>


                        <td>
                            <form  method="POST" action="damageReportDetail.jsp">
                                <input type="hidden" name="rId" value="<%= rId%>">
                                <button>Detail</button>
                            </form>
                    </tr>
                    <% }%>
                </tbody>
            </table>
            <a href="damageReportForm.jsp">
                <button class="button button1">Create Report</button>
            </a>
        </div>
    </body>
</html>
