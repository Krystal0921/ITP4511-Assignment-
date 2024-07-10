<%@ page import="ass.utils.ServletUtil" %>
<%@ page import="ass.bean.DeliveryBean , ass.bean.UserBean" %>
<%@ page import="ass.db.DataBase , ass.db.DeliveryDB"%>
<%@ page import="java.util.ArrayList" %>


<!DOCTYPE html>
<html lang="en">
    <head>
        <%
            if( !ServletUtil.checkLogin(session)){
                response.sendRedirect("../");
                return;
            }
            if(session.getAttribute("msg") != null){
                out.println("<script> alert( ' " + session.getAttribute("msg") + " ' )</script>");
                session.removeAttribute("msg");
            }
            DeliveryDB db = new DeliveryDB(new DataBase());
            ArrayList<DeliveryBean> records = db.getReadyList();
            
        %>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="../style/header.css" />
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
    <link rel="stylesheet" href="../style/item.css">
    <title>HKITI Booking Equipment System</title>
    </head>
    <body class="bg-secondary">
        <%@ include file="../component/header.jsp" %>
        <div class="container">
            <h2 class="text-white text-center mt-3">Delivery Pending List</h2>
        </div>
        <form action="delivery" method="post">
            <input type="hidden" name="action" value="accept">
            <table >
                <thead>
                    <td></td>
                    <th>ID</th>
                    <th>Status</th>
                    <th>Destination</th>
                    <th>Action</th>
                </thead>
                <tbody class="text-white">
                    <% 
                    if(records.size() == 0){
                        out.println("<tr><td colspan='5' class='text-center'>No record found</td></tr>");
                        return;
                    }
                        for(DeliveryBean record : records){ 
                    %>
                        <tr>
                            <td></td>
                            <td><%=record.getId()%></td>
                            <td><%=record.getStatus()%></td>
                            <td><%=record.getCampus()%></td>
                            <td>
                                <button type="submit" name="id" class="btn btn-light" value="<%=record.getId()%>">Accept</button>
                            </td>
                        </tr>
                    <% } %>
                </tbody>
            </table>
        </form>
    </body>
</html>