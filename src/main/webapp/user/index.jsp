<%@ page import="ass.utils.ServletUtil" %>

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
        %>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="../style/header.css" />
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
    <title>HKITI Booking Equipment System</title>
    </head>
    <body class="bg-dark">
        <%@ include file="../component/header.jsp" %>

    </body>
</html>