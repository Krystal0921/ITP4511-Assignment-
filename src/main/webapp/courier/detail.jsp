<%@ page import="ass.bean.DeliveryBean" %>
<%@ page import="ass.utils.ServletUtil , ass.utils.ServletParam" %>
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


    if(request.getAttribute("record")==null){
        response.sendRedirect("assignedList.jsp");
        return;
    }
    ArrayList<DeliveryBean>list = (ArrayList<DeliveryBean>)request.getAttribute("record");

  
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
<body class="bg-secondary">
    <%@ include file="../component/header.jsp" %>
    <div class="container">
        <h2 class="text-white text-center mt-3">Detail List</h2>
        <form action="record" method="post">
            <table >
                <thead>
                    <td></td>
                    <th>Name</th>
                    <th></th>
                    <th>Name</th>
                    <th>Pick up Location</th>
                </thead>
                <tbody class="text-white">
                    <% 
                        for(DeliveryBean item : list){ 
                    %>
                        <tr>
                            <td></td>
                            <td><%=item.getId()%></td>
                            <td></td>
                            <td><%=item.getItemName()%></td>
                            <td><%=item.getCampus()%></td>
                        </tr>
                    <% } %>
        
                    
                </tbody>
            </table>
        </form>
    </div>
    
</body>
</html>