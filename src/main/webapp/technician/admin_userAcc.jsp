<%-- 
    Document   : admin_userAcc
    Created on : 2024年4月12日, 下午4:52:44
    Author     : LYF00
--%>

<%@page import="ass.bean.User"%>
<%@page import="java.util.ArrayList"%>
<%@page import="ass.db.UserDB , ass.db.DataBase"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    UserDB db = new UserDB(new DataBase());
    ArrayList<User> userList = db.getAllUsers();
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>User Account</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
        <style>
            .container h2 {
                text-align: center;
            }

            table {
                width: 100%;
                border-collapse: collapse;
                margin-top: 20px;
            }

            th, td {
                padding: 10px;
                text-align: left;
                border-bottom: 1px solid #ddd;
            }

            th {
                background-color: #f2f2f2;
            }

            tbody tr:nth-child(even) {
                background-color: #f9f9f9;
            }
            .button {
                background-color: #04AA6D;
                border: none;
                color: white;
                padding: 5px 20px;
                text-align: center;
                text-decoration: none;
                display: inline-block;
                font-size: 16px;
                margin: 4px 2px;
                transition-duration: 0.4s;
                cursor: pointer;

            }
            .button1 {
                background-color: white;
                padding: 10px 20px;
                font-size: 18px;
                color: black;
                border: 2px solid #04AA6D;

            }
            .button1:hover {
                background-color: #04AA6D;
                color: white;
            }

            .editButton {
                background-color: white;
                color: black;
                border: 2px solid #04AA6D;

            }
            .editButton:hover {
                background-color: #04AA6D;
                color: white;
            }

            .deleteButton {
                background-color: white;
                color: black;
                border: 2px solid #FF0000;

            }
            .deleteButton:hover {
                background-color: #FF0000;
                color: white;
            }
        </style>
    </head>     
    <body class="bg-light">
        <%@include file="../component/header.jsp" %>
        <div class="container">
            <h2>User Account List</h2>
            <table class="blue">
                <thead>
                    <tr>
                        <th>User ID</th>
                        <th>Username</th>
                        <th>Password</th>
                        <th>Type</th>
                        <th>Campus</th>
                        <th>Active</th>
                        <th>Action</th>
                </tr>
                </thead>
                <tbody>
                    
                    <%
                        for (User u : userList) {
                            String uId = u.getUser_id();
                            String editUrl = "editUserAccountForm.jsp?uId=" + uId;
                    %>
                    <tr>
                        <td><%= u.getUser_id()%></td>
                        <td><%= u.getUsername()%></td>
                        <td>********</td>
                        <td><%= u.getRole()%></td>
                        <td><%= u.getCampus_name()%></td>
                       <td><%= u.getActive().equals("0") ? "not active" : "active" %></td>

                        <td>
                            <form method="POST" action="editUserAccountForm.jsp">
                                    <button  class="btn btn-primary w-100" name="uId" value="<%= uId%>">Edit</button>

                            </form>
                            <form method="POST" action="User">
                                <input type="hidden" name="action" value="delete">
                                <input type="hidden"   name="userId" value="<%= uId%>">
                                <button class="btn btn-danger w-100 mt-1" <%if(u.getUser_id().equalsIgnoreCase("u0000001")) out.print("disabled");%>>Delete</button>
                            </form>
                        </td>
                    </tr>
                    <% }%>

                </tbody>
            </table>
            <br/>
            <a href="userAccountForm.jsp">
                <button class="button button1">Create Account</button>
            </a>
        </div>
    </body>
</html>