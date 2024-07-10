<%-- 
    Document   : userAccountForm
    Created on : 2024年4月12日, 下午6:22:08
    Author     : LYF00
--%>

<%@page import="ass.db.UserDB"%>
<%@page import="ass.bean.User"%>
<%@page import="java.util.List"%>
<%@page import="ass.db.DataBase"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
   
    UserDB db = new UserDB(new DataBase());

    String uId = request.getParameter("uId");
    User u = db.queryUserByID(uId);

%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Edit User Account</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
        <style>
            .form-style-9{
                max-width: 450px;
                background: #FAFAFA;
                padding: 30px;
                margin: 50px auto;
                box-shadow: 1px 1px 25px rgba(0, 0, 0, 0.35);
                border-radius: 10px;
                border: 6px solid #305A72;
            }
            .form-style-9 ul{
                padding:0;
                margin:0;
                list-style:none;
            }
            .form-style-9 ul li{
                display: block;
                margin-bottom: 10px;
                min-height: 35px;
            }
            .form-style-9 ul li  .field-style{
                box-sizing: border-box;
                -webkit-box-sizing: border-box;
                -moz-box-sizing: border-box;
                padding: 8px;
                outline: none;
                border: 1px solid #B0CFE0;
                -webkit-transition: all 0.30s ease-in-out;
                -moz-transition: all 0.30s ease-in-out;
                -ms-transition: all 0.30s ease-in-out;
                -o-transition: all 0.30s ease-in-out;
            }
            .form-style-9 ul li  .field-style:focus{
                box-shadow: 0 0 5px #B0CFE0;
                border:1px solid #B0CFE0;
            }
            .form-style-9 ul li .field-split{
                width: 79%;
            }
            .form-style-9 ul li .field-full{
                width: 100%;
            }
            .form-style-9 ul li input.align-left{
                float:left;
            }
            .form-style-9 ul li input.align-right{
                float:right;
            }
            .form-style-9 ul li textarea{
                width: 100%;
                height: 100px;
            }
            .form-style-9 ul li input[type="button"],
            .form-style-9 ul li input[type="submit"] {
                -moz-box-shadow: inset 0px 1px 0px 0px #3985B1;
                -webkit-box-shadow: inset 0px 1px 0px 0px #3985B1;
                box-shadow: inset 0px 1px 0px 0px #3985B1;
                background-color: #216288;
                border: 1px solid #17445E;
                display: inline-block;
                cursor: pointer;
                color: #FFFFFF;
                padding: 8px 18px;
                text-decoration: none;
                font: 12px Arial, Helvetica, sans-serif;
            }
            .form-style-9 ul li input[type="button"]:hover,
            .form-style-9 ul li input[type="submit"]:hover {
                background: linear-gradient(to bottom, #2D77A2 5%, #337DA8 100%);
                background-color: #28739E;
            }
        </style>
    </head>

    <body>
        <%@include file="../component/header.jsp" %>
        <form class="form-style-9" method="POST" action="User">
            <h2>Edit User</h2>
            <ul>
                Username:
                <li>
                    <input type="hidden" name="action" value="edit">
                    <input type="hidden" name="userId" value="<%= uId%>">
                    
                    <input type="text" name="username" class="field-style field-split align-left" value="<%= u.getUsername()%>" />
                </li>
                Password:
                <li>
                    
                    <input type="password" name="password" class="field-style field-split align-left"  value="<%= u.getPassword()%>"/>
                </li>
                 User Type:
                <li>
                   
                    <select name="role" class="field-style field-split align-left">
                        <option value="" disabled selected>Role:</option>
                        <option value="user" <% if (u.getRole().equalsIgnoreCase("user")) out.print("selected"); %>>User</option>
                        <option value="staff" <% if (u.getRole().equalsIgnoreCase("staff")) out.print("selected"); %>>Staff</option>
                        <option value="technician" <% if (u.getRole().equalsIgnoreCase("technician")) out.print("selected"); %>>Technician</option>
                        <option value="admin" <% if (u.getRole().equalsIgnoreCase("admin")) out.print("selected"); %>>Admin</option>
                        <option value="courier" <% if (u.getRole().equalsIgnoreCase("courier")) out.print("selected"); %>>Courier</option>
                    </select>
                </li>
                 Campus:
                <li>
                    <select name="campus" class="field-style field-split align-left">
                    <option value="" disabled selected>Campus:</option>
                    <option value="c0000001" <% if (u.getCampus_id().equals("c0000001")) out.print("selected"); %>>Chai Wan</option>
                    <option value="c0000002" <% if (u.getCampus_id().equals("c0000002")) out.print("selected"); %>>Lee Wai Lee</option>
                    <option value="c0000003" <% if (u.getCampus_id().equals("c0000003")) out.print("selected"); %>>Sha Tin</option>
                    <option value="c0000004" <% if (u.getCampus_id().equals("c0000004")) out.print("selected"); %>>Tuen Mun</option>
                    <option value="c0000005" <% if (u.getCampus_id().equals("c0000005")) out.print("selected"); %>>Tsing Yi</option>
                    </select>
                </li>
                <li>
                    <input type="submit" value="Edit" />
                </li>
            </ul>
        </form>
    </body>
</html>

