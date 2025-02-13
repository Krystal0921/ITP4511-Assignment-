<%-- 
    Document   : admin_setting
    Created on : 2024年4月12日, 下午5:14:17
    Author     : LYF00
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
        <title>Setting</title>
        <style>
.container {
    display: flex;
    justify-content: center;
    align-items: center;
    height: 100vh;
}
            .form {
                background-color: #15172b;
                border-radius: 20px;
                box-sizing: border-box;
                height: 400px;
                padding: 20px;
                width: 320px;
              
            }

            .title {
                color: #eee;
                font-family: sans-serif;
                font-size: 36px;
                font-weight: 600;
                margin-top: 30px;
            }

        
            .input-container {
                height: 50px;
                position: relative;
                width: 100%;
            }

            .ic1 {
                margin-top: 40px;
            }

            .ic2 {
                margin-top: 30px;
            }

            .input {
                background-color: #303245;
                border-radius: 12px;
                border: 0;
                box-sizing: border-box;
                color: #eee;
                font-size: 18px;
                height: 100%;
                outline: 0;
                padding: 4px 20px 0;
                width: 100%;
            }

            .cut {
                background-color: #15172b;
                border-radius: 10px;
                height: 20px;
                left: 20px;
                position: absolute;
                top: -20px;
                transform: translateY(0);
                transition: transform 200ms;
                width: 76px;
            }

            .cut-short {
                width: 50px;
            }

            .input:focus ~ .cut,
            .input:not(:placeholder-shown) ~ .cut {
                transform: translateY(8px);
            }

            .placeholder {
                color: #65657b;
                font-family: sans-serif;
                left: 20px;
                line-height: 14px;
                pointer-events: none;
                position: absolute;
                transform-origin: 0 50%;
                transition: transform 200ms, color 200ms;
                top: 20px;
            }

            .input:focus ~ .placeholder,
            .input:not(:placeholder-shown) ~ .placeholder {
                transform: translateY(-30px) translateX(10px) scale(0.75);
            }

            .input:not(:placeholder-shown) ~ .placeholder {
                color: #808097;
            }

            .input:focus ~ .placeholder {
                color: #dc2f55;
            }

            .submit {
                background-color: #08d;
                border-radius: 12px;
                border: 0;
                box-sizing: border-box;
                color: #eee;
                cursor: pointer;
                font-size: 18px;
                height: 50px;
                margin-top: 38px;
                outline: 0;
                text-align: center;
                width: 100%;
            }

            .submit:active {
                background-color: #06b;
            }


        </style>
    </head>
    <body>
        <%@include file="../component/header.jsp" %>
      <div class="container">
        <div class="form">
            <form>
            <div class="title">User Setting</div>
            <div class="input-container ic1">
                <input id="username" class="input" type="text" placeholder=" " />
                <label for="username" class="placeholder">Username</label>
            </div>
               <div class="input-container ic1">
                <input id="password" class="input" type="password" placeholder=" " />
                <label for="password" class="placeholder">Password</label>
           
            <button type="text" class="submit">save changes</button>
             </form>
            </div>
           </div>
           
    </body>
</html>
