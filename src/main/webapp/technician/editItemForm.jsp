<%-- 
    Document   : createItemForm
    Created on : 2024年4月15日, 下午7:35:51
    Author     : LYF00
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="ass.db.ItemDB , ass.db.DataBase"%>
<%@page import="java.util.List"%>
<%@page import="ass.bean.Item"%>
<%
   
    ItemDB db = new ItemDB(new DataBase());

   String eId = request.getParameter("eId");
    Item item = db.queryItemByID(eId);

%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Edit Item</title>
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
                width: 49%;
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
        <form class="form-style-9" action="Item" method="POST">
            <h2>Edit Item</h2>
            <ul>
                <input type="hidden" name="action" value="edit">
                 <input type="hidden" name="itemId" value="<%= eId %>">
                   Equipment Name:
                <li>
                    <input type="text" name="equipmentName" class="field-style field-split align-left" value="<%= item.getItem_name() %>" />
                </li>
                     Type:
                <li>
                <input type="text" name="type" class="field-style field-split align-left" placeholder="Type" value="<%= item.getItem_type() %>" />
            </li>

              <li>
                <input type="radio" name="userType" value="Y" id="staffRadio" <% if (item.getUserType().equals("Y")) out.print("checked"); %> />
                <label for="staffRadio">Staff Exclusive</label>
                <input type="radio" name="userType" value="N" id="generalRadio" <% if (item.getUserType().equals("N")) out.print("checked"); %> />
                <label for="generalRadio">General</label>
            </li>

                <li>
                    <input type="submit" value="save changes" />
                </li>
            </ul>
        </form>
    </body>
</html>
