<%@ page import="ass.bean.UserBean ,ass.utils.ServletUtil" %>
<% Object msg = request.getAttribute("msg"); %>
<!DOCTYPE html>
<html lang="en">
<link>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
</head>

<body class="bg-dark">
    <%  
        if(session.getAttribute("msg") != null){
            out.println("<script> alert( ' " + session.getAttribute("msg") + " ' )</script>");
            session.removeAttribute("msg");
        } 
        session.removeAttribute("msg");
  
            

        if (ServletUtil.checkLogin(session)) {
            UserBean u = (UserBean) session.getAttribute("user");
            String role = u.getRole();
            if(role.equalsIgnoreCase("admin")) role = "technician";
            if(role.equalsIgnoreCase("staff")) role = "user";

            response.sendRedirect(role+"/index.jsp");
            return;
        }
    %>


    <div class="container-m position-absolute top-50 start-50 translate-middle">
        <div class="row">
            <div class="col-8 bg-light ">
                <div class=" my-auto mx-auto">
                    <h2 class="text-center mt-5 pt-5">
                        HKITI Equipment Booking System
                    </h2>

                </div>
            </div>
            <div class="col-4 bg-secondary">
                <h1>Login</h1>

                <form action="Login" method="POST" class="pb-3 pt-3">
                    <label for='username'class="text-light pb-2">Username:</label>
                    <br>
                    <input type='text' id='username' name='username' lang="en" required>
                    <br>
                    <label for='password'class="text-light pb-2">Password:</label><br>
                    <input type='password' id='password' name='password' autocomplete="true" lang="en" required>
                    <br>
                    <button type='submit' class="btn btn-primary mt-4 flex-row-reverse">Login</button>
                </form>
        </div>
    </div>



</body>
</html>
