<%@ page import="ass.utils.ServletUtil , ass.bean.ItemBean , ass.bean.UserBean" %>
<%@ page import="ass.db.DataBase , ass.db.UserItemDB" %>
<%@ page import="java.util.ArrayList" %>

<%  
    if( !ServletUtil.checkLogin(session)){
        response.sendRedirect("index.jsp");
        return;
    }

    if(session.getAttribute("msg") != null){
        out.println("<script> alert( ' " + session.getAttribute("msg") + " ' )</script>");
        session.removeAttribute("msg");
    }
    UserItemDB db = new UserItemDB(new DataBase());

    ServletParam<String> type = ServletUtil.getStringParam(request,"type","all");
    //if(!ServletUtil.checkEmptyParams(response,type))return;

    UserBean u = (UserBean) session.getAttribute("user");
    ArrayList<String> types = db.getTypes();
    ArrayList<ItemBean> items = db.getTypeItems(type.getValue(),session);
%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>HKITI Booking Equipment System</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
        <link rel="stylesheet" href="../style/item.css">
        <script src="../js/item.js"></script>
    </head>
    <body class="bg-dark">

        <%@ include file="../component/header.jsp" %>
        <div class="container">
            <h2 class="text-white text-center mt-3">Equipment List</h2>
            

            <form action="item" method="post">
                <table >
                    <thead>
                        <td></td>
                        <th class="th-name">Name</th>
                        <th class="th-type">Type</th>
                        <th class="th-action">Action</th>
                        <th>
                            <div class="row">
                                <div class="col">
                                    <select class="mt-1" id="type">
                                        <% if(types!=null){
                                        for(String typeString : types){ %>
                                            <option value="<%=typeString%>"<%if(type.getValue().equalsIgnoreCase(typeString))out.print("selected");%>><%=typeString%></option>
                                            <% }
                                        } %>
                                            <option value="all" <%if(type.getValue().equalsIgnoreCase("all"))out.print("selected");%> >All</option>
                                    </select>
                                </div>
                                <div class="col">
                                    <button type="button" class="btn btn-primary btn-sm" onclick="changeType()">Search</button>
                                </div>
                            </div>

                        </th>
                    </thead>
                    <tbody class="text-white">
                        <% 
                            if(items.size() == 0){
                                out.println("<tr><td colspan='7' class='text-center'>No item found</td></tr>");
                                return;
                            } 
                            for(ItemBean item : items){ 
                                String isActive = db.checkInWish(item.getId(),u.getId())?"fa-solid text-white":"fa-regular";
                        %>
                            <tr>
                                <td></td>
                                <%=item %>
                                <td>
                                    <button type="submit" name="id" class="btn btn-secondary" value="<%=item.getId()%>">Add</button>
                                </td>
                                <td>
                                    <button type="submit" name="toWish" class="btn btn-warning" value="<%=item.getId()%>"><i class=" fa-star <%=isActive %>"></i>
                                    </button>
                                </td>
                            </tr>
                        <%
                            } 
                        %>
                        
                    </tbody>
                    <input type="hidden" name="action" value="addCart"/>
                </table>
            </form>
        </div>
    </body>
</html>