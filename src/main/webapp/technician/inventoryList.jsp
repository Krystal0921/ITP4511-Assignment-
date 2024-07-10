<%@page import="java.util.ArrayList"%>
<%@page import="ass.bean.ItemBean , ass.bean.UserBean"%>
<%@page import="ass.db.DataBase , ass.db.InventoryDB"%>
<%
    if( !ServletUtil.checkLogin(session)){
        response.sendRedirect("index.jsp");
        return;
    }
    if(session.getAttribute("msg") != null){
        out.println("<script> alert( ' " + session.getAttribute("msg") + " ' )</script>");
        session.removeAttribute("msg");
    }
    UserBean u = (UserBean) session.getAttribute("user");
    InventoryDB db = new InventoryDB(new DataBase());
    ArrayList<ItemBean>items = db.getInventory(u.getId());
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="../style/item.css">
    <title>List</title>

</head>
<body class="bg-light">
    <%@ include file="../component/header.jsp" %>
    <div class="container">
        <h2 class="mt-3">Item List</h2>
        <a href="addInventory.jsp">
            <button class="btn btn-secondary">+ Add Inventory</button>
        </a>
        <table class="blue">
            <thead>
                <tr>
                    <th>Equipment ID</th>
                    <th>Equipment Name</th>
                    <th>Type</th>
                    <th>Avaliable</th>
                </tr>
            </thead>
            <tbody>

               <%
                if(items.size() == 0){
                    out.println("<tr><td colspan='4' class='text-center'>No Item Found</td></tr>");
                }
                for(ItemBean item :items){
                %>
                <tr>
                    <td><%=item.getId()%></td>
                    <td><%=item.getName()%></td>
                    <td><%=item.getType()%></td>
                    <td><%=item.getIsAva() ? "Avaliable" : "Unavaliable"%></td>
                </tr>
                <%}%>
            </tbody>
        </table>
    </div>

    
</body>
</html>