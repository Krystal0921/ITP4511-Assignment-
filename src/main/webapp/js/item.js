function changeType() {
  console.log(document.getElementById("type").value)
  location.href = "item.jsp?type=" + document.getElementById("type").value;
}
