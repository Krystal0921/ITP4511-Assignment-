<%@ page import="ass.bean.UserBean" %>
<%@ page import="ass.utils.ServletUtil" %>
<%
if( !ServletUtil.checkLogin(session)){
  response.sendRedirect("../");
  return;
}

if(session.getAttribute("msg") != null){
    out.println("<script> alert( ' " + session.getAttribute("msg") + " ' )</script>");
    session.removeAttribute("msg");
}
UserBean u = (UserBean)session.getAttribute("user");
String roleString = u.getRole();
String bgColor = "bg-dark";
if(roleString.equalsIgnoreCase("technician")||roleString.equalsIgnoreCase("admin")) bgColor = "bg-light";
if(roleString.equalsIgnoreCase("courier")) bgColor = "bg-secondary";
String textColor = !bgColor.equals("bg-light")?"text-light":"text-muted";


%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
    <script src="../js/setting.js"></script>
    <link rel="stylesheet" href="../style/setting.css">

    <title>HKITI Booking Equipment System</title>
    </head>
    <body class="<%=bgColor%>">
        <%@ include file="../component/header.jsp" %>
        <div class="row mt-2 w-100">
            <div class="col mx-3">
                <form action="update" method="post">
                      <h3 class="mt-2 <%=textColor%>">Update Password</h3>
                      <div>
                        <label for="cur-password" class="form-label <%=textColor%> mt-2">Current Password</label>
                        <input type="password" class="form-control" name="cur-password" minlength="8" maxlength="20" placeholder="Current Password" required>
                     </div>

                      <div>
                        <label for="new-password" class="form-label <%=textColor%> mt-2">New Password</label>
                        <input type="password" class="form-control" name="new-password" minlength="8" maxlength="20" placeholder="New Password" required>
                     </div>
                     
                      <div>
                        <label for="new-password" class="form-label <%=textColor%> mt-2">Confirm New Password</label>
                        <input type="password" class="form-control" name="con-password" minlength="8" maxlength="20" placeholder="Confirm New Password" required>
                     </div>
                     <input type="hidden" name="action" value="updatePassword"> 

                    <button type="submit" class="btn btn-primary mt-5">Submit</button>

                </form>
            </div>
            <div class="col me-3">
                <form action="update" method="post">
                    <h3 class="mt-2 <%=textColor%>">Update Email / Username</h3>
                    <div>
                      <label for="cur-password" class="form-label <%=textColor%> mt-2">User ID</label>
                      <input type="text" class="form-control" name= "user-id" placeholder="User ID" required>
                   </div>

                    <div>
                      <label for="new-password" class="form-label <%=textColor%> mt-2">Current Email / Username</label>
                      <input type="text" class="form-control" name = "curr-email" minlength="5" placeholder="Current Email / Username" >
                   </div>

                    <div>
                      <label for="new-password" class="form-label <%=textColor%> mt-2">Email / Username</label>
                      <input type="text" class="form-control" name = "email" minlength="5"  placeholder="Email / Username" required>
                   </div>
                   
                    <div>
                      <label for="new-password" class="form-label <%=textColor%> mt-2">Confirm Email / Username</label>
                      <input type="text" class="form-control" name="con-email" minlength="5" placeholder="Confirm Email / Username" required>
                   </div>
                   
                    <div>
                      <label for="new-password" class="form-label <%=textColor%> mt-2">Password</label>
                      <input type="password" class="form-control" name="password" placeholder="Password" required>
                   </div>
                   <input type="hidden" name="action" value="updateEmail"> 

                  <button type="submit" class="btn btn-primary mt-3">Submit</button>

                </form>
            </div>
        </div>
    </body>
</html>