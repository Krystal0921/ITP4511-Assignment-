<%@ page import="ass.bean.UserBean , ass.utils.ServletUtil , ass.utils.ServletParam" %>
<%
    if(session.getAttribute("user")==null) return;
    UserBean user=  (UserBean)session.getAttribute("user");
    String role = user.getRole();
    String headerColor="";
    if(role.equalsIgnoreCase("user")||role.equalsIgnoreCase("staff")) headerColor="text-white";
%>

<div class="system-name">
    <h1 class="<%=headerColor %>">HKITI Booking Equipment System</h1>
</div>

<div class="nav-header">
    <%
        if(role.equalsIgnoreCase("user")||role.equalsIgnoreCase("staff")){ 
    %>
    <a id="home" href="../user/index.jsp">
        <i class="fa-solid fa-house text-white"></i>
        Home
    </a>
    <a id="item" href="../user/item.jsp?type=all">
        <i class="fa-solid fa-wrench text-white"></i>
        Item
    </a>
    <a id="wish" href="../user/wish.jsp">
        <i class="fa-solid fa-star text-white"></i>
        Wish List
    </a>
    <a id="cart" href="../user/cart.jsp">
        <i class="fa-solid fa-cart-shopping text-white"></i>
        cart
    </a>
    <a id ="record"href="../user/record.jsp">
        <i class="fa-solid fa-pen text-white"></i>
        Record List
    </a>
    
    <%
        }
    %>

    <%
    if(role.equalsIgnoreCase("technician")||role.equalsIgnoreCase("admin")){
    %>
        <a id="home" href="../technician/index.jsp">Home</a>
        <a id= "itemList" class="" href="../technician/itemList.jsp">Item List</a>
        <a id= "inventory" class="" href="../technician/inventoryList.jsp">Add Inventory</a>
        <a id="admin_bookingRecord" class="" href="../technician/admin_bookingRecord.jsp">Member Booking Record</a>
        <a id="admin_damageReport" class="" href="../technician/admin_damageReport.jsp">Damage Report</a>
        <%if(role.equalsIgnoreCase("admin")){%>
        <a id="admin_userAcc" class="" href="../technician/admin_userAcc.jsp">User Account</a>
        <a id="bookingReport" class="" href="../technician/bookingReport.jsp">Analytic Report</a>
        <%}%>
    <%}%>

    <%if(role.equalsIgnoreCase("courier")){%>
        <a id="home" class="" href="../courier/index.jsp">Home</a>
        <a id="orderList" class=""  href="../courier/orderList.jsp">Delivery</a>
        <a id="assigned" class=""  href="../courier/assignedList.jsp">Assigned List</a>
    <%}%>
    
    <div class="d-flex justify-content-end">
        <a id="setting" href="../common/setting.jsp">
            <i class="fa-solid fa-gear text-white"></i>
            Setting
        </a>
        <a  href="<%=request.getContextPath()%>/Login?logout=true">
            <i class="fa-solid fa-arrow-right-from-bracket text-white"></i>
            Logout
        </a>
    </div>

</div>

<script src="../js/header.js"></script>

<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css">
<link rel="stylesheet" href="../style/header.css" />

