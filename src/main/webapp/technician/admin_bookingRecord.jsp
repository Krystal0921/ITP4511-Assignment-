<%-- 
    Document   : admin_bookingRecord
    Created on : 2024年4月12日, 下午4:01:04
    Author     : LYF00
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList"%>
<%@page import="ass.db.RecordDB , ass.db.DataBase"%>
<%@page import="ass.bean.UserBean , ass.bean.RecordBean"%>

<%
    if( !ServletUtil.checkLogin(session)){
        response.sendRedirect("../index.jsp");
        return;
    }
    if(session.getAttribute("msg") != null){
        out.println("<script> alert( ' " + session.getAttribute("msg") + " ' )</script>");
        session.removeAttribute("msg");
    }
    RecordDB db = new RecordDB(new DataBase());
    UserBean u = (UserBean) session.getAttribute("user");
    ArrayList<RecordBean> recordList = db.getCRecords(u.getId());

%>
<!DOCTYPE html>
<html>
    <head>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="../style/item.css">
    </head>

    <body class="bg-light">
        <%@include file="../component/header.jsp" %>
        <div class="container">
            <h2>Booking Record</h2>
            <table class="blue" >
                <thead>
                    <tr>
                        <th>User ID</th>
                        <th>Pick Up Date</th>
                        <th>Return Date</th>
                        <th colspan="2">Status</th>
                        <th >Action</th>
                    </tr>
                </thead>
                <tbody>

                    <%
                        if(recordList.size() == 0){
                            out.println("<tr><td colspan='7' class='text-center'>No record found</td></tr>");
                            return;
                        }
                        for(RecordBean record : recordList){
                    %>
                    <tr>
                        <form action="record" method="post">
                            <input type="hidden" name="bId" value="<%=record.getRecordId()%>">
                            <td><%=record.getId()%></td>
                            <td><%=record.getPickUpDate()%></td>
                            <td><%=record.getReturnDate()%></td>
                            
                            <td>    
                                <select name="status" <%if(record.getStatus().equalsIgnoreCase("complete")||record.getStatus().equalsIgnoreCase("waiting"))out.print("disabled");%>>
                                    <option value="pending" <% if(record.getStatus().equalsIgnoreCase("pending"))out.print("selected"); %>>Pending</option>
                                    <option value="approved" <% if(record.getStatus().equalsIgnoreCase("approved"))out.print("selected"); %>>Approved</option>
                                    <% if (record.getStatus().equalsIgnoreCase("waiting")){%>
                                    <option value="waiting" <% if(record.getStatus().equalsIgnoreCase("waiting"))out.print("selected"); %>>Waiting for delivery</option>
                                    <%}%>
                                    <option value="pickup" <% if(record.getStatus().equalsIgnoreCase("pickup"))out.print("selected"); %>>Waiting for pickup</option>
                                    <option value="return" <% if(record.getStatus().equalsIgnoreCase("return"))out.print("selected"); %>>Waiting for return</option>
                                    <option value="complete" <% if(record.getStatus().equalsIgnoreCase("complete"))out.print("selected"); %>>Complete</option>
                                </select>
                            </td>
                            <td>
                                <button class="btn btn-primary" name = "action" value="update"  <%if(record.getStatus().equalsIgnoreCase("complete")||record.getStatus().equalsIgnoreCase("waiting"))out.print("disabled");%>>Update</button>
                            </td>
                            <td ><button class="btn btn-secondary" name ="action" value="view">view</button></td>

                        </form>
                    </tr>
                    <%}%>
                </tbody>
            </table>
        </div>
    </body>
</html>