<%-- 
    Document   : admin_MainPage
    Created on : 2024年4月12日, 下午2:13:45
    Author     : LYF00
--%>

<%@page import="java.util.ArrayList"%>
<%@page import="ass.bean.Item"%>
<%@page import="ass.db.DataBase , ass.db.ItemDB"%>

<%
    if( !ServletUtil.checkLogin(session)){
        response.sendRedirect("index.jsp");
        return;
    }
    if(session.getAttribute("msg") != null){
        out.println("<script> alert( ' " + session.getAttribute("msg") + " ' )</script>");
        session.removeAttribute("msg");
    }
    ItemDB db = new ItemDB(new DataBase());
    ArrayList<Item> itemList = db.getAllItems();
%>

<!DOCTYPE html>
<html>
    <head>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Inventory Record</title>
        <link rel="stylesheet" href="../style/item.css">
    </head>

    <body class="bg-light">

        <%@ include file="../component/header.jsp" %>

        <div class="container">
            <h2 class="mt-3">Item List</h2>
            <a href="createItemForm.jsp">
                <button class="btn btn-secondary">+ Create Item</button>
            </a>
 
            <table class="blue">
                <thead>
                    <tr>
                        <th>Equipment ID</th>
                        <th>Equipment Name</th>
                        <th>Type</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody>

                    <%
                        if(itemList.size() == 0){
                            out.println("<tr><td colspan='4' class='text-center'>No Item Found</td></tr>");
                        }   
                        for (Item item : itemList) {
                            String eId = item.getItem_id();
                            String editUrl = "editItemForm.jsp?eId=" + eId;
                    %>
                    <tr>
                        <td><%= item.getItem_id()%></td>
                        <td><%= item.getItem_name()%></td>
                        <td><%= item.getItem_type()%></td>

                        <td>
                             <form  method="POST" action="editItemForm.jsp">
                                <input type="hidden" name="eId" value="<%= eId %>">
                                <button class="btn btn-primary w-100" >Edit</button>
                            </form>
                            
                            <form method="POST" action="Item">
                                <input type="hidden" name="action" value="delete">
                                <input type="hidden" name="itemId" value="<%= eId%>">
                                <button class="btn btn-danger w-100 mt-3" >Delete</button>
                            </form>
                        </td>
                    </tr>
                    <%}%>
                </tbody>
            </table>
        </div>
    </body>
</html>