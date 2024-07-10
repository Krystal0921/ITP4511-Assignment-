<%@ page import="ass.utils.ServletUtil , ass.bean.RecordBean , ass.bean.UserBean" %>
<%@ page import="ass.db.DataBase , ass.db.RecordDB" %>
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
    RecordDB db = new RecordDB(new DataBase());
    UserBean u = (UserBean) session.getAttribute("user");
    ArrayList<RecordBean> records = db.getRecord(u.getId());
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
    </head>
    <body class="bg-dark">

        <%@ include file="../component/header.jsp" %>
        <div class="container">
            <h2 class="text-white text-center mt-3">Record List</h2>
            

            <form action="record" method="post">
                <table >
                    <thead>
                        <td></td>
                        <th>ID</th>
                        <th>Status</th>
                        <th>Pick Up Date</th>
                        <th>Return Date</th>
                        <th>View</th>
                    </thead>
                    <tbody class="text-white">
                        <% 
                        if(records.size() == 0){
                            out.println("<tr><td colspan='7' class='text-center'>No record found</td></tr>");
                            return;
                        }
                            for(RecordBean record : records){ 
                        %>
                            <tr>
                                <td></td>
                                <%=record %>
                                <td>
                                    <button type="submit" name="id" class="btn btn-secondary" value="<%=record.getId()%>">View</button>
                                </td>
                            </tr>
                        <% } %>
            
                        
                    </tbody>
                </table>
            </form>
        </div>
    </body>
</html>