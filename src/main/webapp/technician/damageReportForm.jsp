<%-- 
    Document   : damageReportForm
    Created on : 2024年4月12日, 下午5:59:45
    Author     : LYF00
--%>

<%@page import="java.util.ArrayList"%>
<%@page import="ass.db.ItemDB"%>
<%@page import="ass.bean.Item"%>
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
    UserBean u = (UserBean) session.getAttribute("user"); 
    ItemDB db = new ItemDB(new DataBase());
    ArrayList<String> itemList = db.getCItemIds(u.getId());
%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Damage Report Form</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
        <link rel="stylesheet" href="../style/damageReportForm.css">

    </head>


    <body class="bg-light">
        <%@include file="../component/header.jsp" %>
        <form class="form-style-9" method="POST" action="Report">
            <h2>Damage Report</h2>
            <input type="hidden" name="action" value="create">
            <ul>
                <li>
                    <select name="equipmentId" id="equipmentId" class="field-style field-split align-left">
                        <%for (String id: itemList) {%>
                        <option value="<%= id %>"><%= id %> </option>
                        <%}%>
                    </select>
                </li>

                <li>
                    <input type="text" name="createBy" class="field-style field-split align-left" value="<%=u.getId() %>" readonly/>
                </li>

                <li>
                    <textarea name="description" class="field-style" placeholder="Description of Damage" required></textarea>
                </li>

                <li>

                    <input type="submit" value="Submit" />
                </li>
            </ul>
        </form>
    </body>
</html>
