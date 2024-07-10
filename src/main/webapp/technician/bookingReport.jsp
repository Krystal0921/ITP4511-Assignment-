<%-- 
    Document   : bookingReport
    Created on : 2024年4月15日, 下午8:56:06
    Author     : LYF00
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Report</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
        <style>

     body {
      font-family: Arial, sans-serif;
        background-color: #f5f5f5;
        margin: 0;
        padding: 0;
    }

    .container {
        max-width: 600px;
        margin: 0 auto;
        padding: 20px;
        background-color: #fff;
        border-radius: 4px;
        box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
    }

    h2 {
        margin-bottom: 20px;
    }

    form {
        margin-bottom: 20px;
    }

    label {
        display: block;
        margin-bottom: 5px;
        font-weight: bold;
    }

    select,
    input[type="month"] {
        width: 100%;
        padding: 10px;
        margin-bottom: 10px;
        border: 1px solid #ccc;
        border-radius: 4px;
    }

    input[type="submit"] {
        background-color: #4caf50;
        color: #fff;
        padding: 10px 20px;
        border: none;
        border-radius: 4px;
        cursor: pointer;
    }

    input[type="submit"]:hover {
        background-color: #45a049;
    }
        </style>
    </head>

    <body>
        <%@include file="../component/header.jsp" %>
        <div class="container">
            <h2>Booking Rate of Selected Venue</h2>
            <form action="record" method="POST">
                <input type="hidden" name="action" value="venue">

                <label for="venueId">Select Venue:</label>
                <select name="venueId" id="venueId" required>
                    <option value="" disabled selected>Campus:</option>
                    <option value="c0000001">Chai Wan</option>
                    <option value="c0000002">Lee Wai Lee</option>
                    <option value="c0000003">Sha Tin</option>
                    <option value="c0000004">Tuen Mun</option>
                    <option value="c0000005">Tsing Yi</option>
                </select>
                <br>
                <label for="Month and Year">Select Month:</label>
                <input type="month" name="month" id="month" required>
                <br>
           
                <input type="submit" value="Search">
            </form>

        </div>
    </body>
</html>