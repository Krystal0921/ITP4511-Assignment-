<%-- 
    Document   : createItemForm
    Created on : 2024年4月15日, 下午7:35:51
    Author     : LYF00
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Create Item</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
        <link rel="stylesheet" href="../style/createItem.css">
    </head>
    <body>
        <%@include file="../component/header.jsp" %>
        <form class="form-style-9"  method="POST" action="Item">
            <h2>Create Item</h2>
            <ul>
                <li>
                    <input type="hidden" name="action" value="create">
                    <input type="text" name="equipmentName" class="field-style field-split align-left" placeholder="Equipment Name" required/>
                </li>
                <li>
                    <input type="text" name="type" class="field-style field-split align-left" placeholder="Type" required/>
                </li>
                <li>
                    <input type="radio" name="userType" value="Y"  required/>
                    <label for="staffRadio">Staff Exclusive</label>
                    <input type="radio" name="userType" value="N"  required/>
                    <label for="generalRadio">General</label>
                </li>
                <li>
                    <input type="submit" value="Create" />
                </li>
            </ul>
        </form>
    </body>
</html>
