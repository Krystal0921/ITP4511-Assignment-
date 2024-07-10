<%@ page import="ass.utils.ServletUtil , ass.bean.ItemBean"%>
<%@ page import="java.util.ArrayList , java.util.Date" %>

<jsp:useBean id="db" class="ass.db.DataBase"/>
<jsp:useBean id="itemDB" class="ass.db.UserItemDB"/>
<jsp:setProperty name="itemDB" property="db" value="<%=db %>" />

<%
    if( !ServletUtil.checkLogin(session)){
        response.sendRedirect("index.jsp");
        return;
    }

    if(session.getAttribute("msg") != null){
        out.println("<script> alert( ' " + session.getAttribute("msg") + " ' )</script>");
        session.removeAttribute("msg");
    }
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
    <link rel="stylesheet" href="../style/item.css">
    <title>HKITI Booking Equipment System</title>
</head>
<body>
    <body class="bg-dark">
        <%@ include file="../component/header.jsp" %>
        <div class="container">
            <h2 class="text-white text-center mt-3">Cart</h2>
            <form action="item" method ="post">
                <table>
                    <thead>
                        <!-- <th></th> -->
                        <td></td>
                        <th>Name</th>
                        <th>Type</th>
                        <th>Action</th>
                        <th></th>
                    </thead>
                    <tbody class="text-white">
                        <%
                            ArrayList<ItemBean> items = itemDB.getCartItem(session);
                            if(items.size() == 0){
                                out.println("<tr><td colspan='7' class='text-center'>No record found</td></tr>");
                                return;
                            }
                            String isCheckOutDisable = items.size() == 0 ? "disabled" : "";
                            String btnColor = isCheckOutDisable.equals("disabled")?"btn-secondary":"btn-primary";
                            for(ItemBean item : items){
                                out.println(item.getId());  
                                String isItemDisable = itemDB.checkAva(item.getId()) ? "" : "disabled";
                        %>
                                <tr>
                                    <td>
                                        <input type="checkbox" name="items" value="<%=item.getId()%>" <%=isItemDisable %>/>
                                    </td>
                                    <%=item.toString()%>
                                    <td>
                                        <form action="item" method="post">
                                            <input type="hidden" name="action" value="removeCart"/>
                                            <button type="submit" name="id" class="btn btn-danger" value="<%=item.getId()%>">Remove</button>
                                        </form>
                                    </td>
                                    <td class="text-center">
                                        <%
                                            if(isItemDisable.equals("disabled")){
                                                out.print("<label class='text-danger'>Out of stock</label>");
                                            }
                                        %>
                                    </td>
                                </tr>
                        <%
                            }
                        %>
                    </tbody>
                </table>
                <p class="text-white pt-4">Total: <%=items.size()%> item(s)</p>
                <div class="row mt-2">
                    <div class="col-2">
                        <label for="pick-up-date" class="text-white">Pick Up Date</label>
                        <input type="date" min="2024-04-29" name="pickDate" class="form-control" >
                    </div>
                 
                    <div class="col-2">
                        <label for="return-date" class="text-white">Return Date</label>
                        <input type="date" min="2024-05-06" name="returnDate" class="form-control">
                    </div>
                </div>
                <div class="col d-flex justify-content-end">
                    <button type="submit" class="btn <%=btnColor%> " name="toCheckout" value="checkOut" <%=isCheckOutDisable %>>Check Out</button>
                </div>
                
            </form>
        </div>
    </body>
</body>
</html>