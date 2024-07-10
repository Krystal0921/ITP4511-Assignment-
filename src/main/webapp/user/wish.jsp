<%@ page import="ass.utils.ServletUtil , ass.bean.ItemBean"%>
<%@ page import="java.util.ArrayList" %>

<jsp:useBean id="db" class="ass.db.DataBase"/>
<jsp:useBean id="itemDB" class="ass.db.UserItemDB"/>
<jsp:setProperty name="itemDB" property="db" value="<%=db %>" />

<%
    if( !ServletUtil.checkLogin(session)){
        response.sendRedirect("index.jsp");
        return;
    }

    if(session.getAttribute("msg") != null){
        out.println("<script> alert( ' " + session.getAttribute("msg") + " ' )</script>");
        session.removeAttribute("msg");
    }
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
    <link rel="stylesheet" href="../style/item.css">
    <title>HKITI Booking Equipment System</title>
</head>
<body>
    <body class="bg-dark">
        <%@ include file="../component/header.jsp" %>
        <div class="container">
            <h2 class="text-white text-center mt-3">Wish List</h2>

            <form action="item" method ="post">
                <input type="hidden" name="action" value="removeWish"/>
                <table>
                    <thead>
                        <td></td>
                        <th>Name</th>
                        <th>Type</th>
                        <th>Action</th>
                        <th></th>
                    </thead>
                    <tbody class="text-white">
                        <%
                            ArrayList<ItemBean> items = itemDB.getWishItem(session);
                            if(items.size() == 0){
                                out.println("<tr><td colspan='7' class='text-center'>No record found</td></tr>");
                                return;
                            }
                            for(ItemBean item : items){
                        %>
                                <!-- out.println(item.toString()); -->
                                <tr>
                                    <td></td>
                                    <td><%=item.getName()%></td>
                                    <td><%=item.getType()%></td>
                                    <td>
                                        <button type="submit" name="id" class="btn btn-danger" value="<%=item.getId()%>">Remove</button>
                                    </td>
                                    <td></td>
                                </tr>
                        <%
                            }
                        %>
                    </tbody>
                </table>
            </form>
        </div>
    </body>
</body>
</html>