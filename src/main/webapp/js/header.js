console.log(window.location.pathname.split("/").slice(-1)[0])
switch (window.location.pathname.split("/").slice(-1)[0]) {
  case "item.jsp":
    document.getElementById("item").classList.add("active")
    document
      .querySelector(".fa-wrench.text-white")
      .classList.remove("text-white")
    break
  case "wish.jsp":
    document.getElementById("wish").classList.add("active")
    document.querySelector(".fa-star.text-white").classList.remove("text-white")
    break
  case "cart.jsp":
    document.getElementById("cart").classList.add("active")
    document
      .querySelector(".fa-cart-shopping.text-white")
      .classList.remove("text-white")
    break
  case "record.jsp":
  case "record":
    document.getElementById("record").classList.add("active")
    document.querySelector(".fa-pen.text-white").classList.remove("text-white")
    break
  case "admin_bookingRecord.jsp":
  case "record":
    document.getElementById("admin_bookingRecord").classList.add("active")
    break
  case "setting.jsp":
    document.getElementById("setting").classList.add("active")
    document.querySelector(".fa-gear.text-white").classList.remove("text-white")
    break
  case "addInventory.jsp":
  case "inventoryList.jsp":
    document.getElementById("inventory").classList.add("active")
    document.querySelector(".fa-list.text-white").classList.remove("text-white")
    break
  case "createItemForm.jsp":
  case "itemList.jsp":
    document.getElementById("itemList").classList.add("active")
    break
  case "admin_damageReport.jsp":
  case "damageReportForm.jsp":
  case "damageReportDetail.jsp":
    document.getElementById("admin_damageReport").classList.add("active")
    break
  case "admin_userAcc.jsp":
  case "userAccountForm.jsp":
  case "editUserAccountForm.jsp":
    document.getElementById("admin_userAcc").classList.add("active")
    break
  case "bookingReport.jsp":
    document.getElementById("bookingReport").classList.add("active")
    break
  case "orderList.jsp":
    document.getElementById("orderList").classList.add("active")
    break
  case"delivery":
  case "assignedList.jsp":
    document.getElementById("assigned").classList.add("active")
    break
  default:
    document.getElementById("home").classList.add("active")
    document
      .querySelector(".fa-house.text-white")
      .classList.remove("text-white")
    break
}
