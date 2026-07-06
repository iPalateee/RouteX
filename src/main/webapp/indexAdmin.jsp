<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="it">
<head>
<%@ include file="imports.jsp" %>
<title>RouteX - Admin</title>
<link rel="stylesheet" type="text/css" href="css/dashWork.css">
    <style>

        .metro-logos {
            position: absolute;
            right: 20px;
            bottom: 20px;
            display: flex;
            flex-direction: column;
            align-items: center;
            gap: 15px;
        }

        .metro-logos i {
            width: 50px;
            height: 50px;
            font-size: 50px;
            color: white;
            border-radius: 50%;
            transition: transform 0.3s ease;
        }

        .metro-logos i:hover {
            transform: scale(1.2);
        }
    </style>
</head>
<body>

    <div class="background-blur"></div>

    <div class="main-container">

        <div class="left-box">
            <img src="images/logo-no-background.png" alt="Logo">
            <h1>RouteX</h1>
            <p>Navigating the Future,<br>One Stop at a Time</p>
        </div>

        <div class="right-content">

            <div class="button-container">
                <a href="logout">Logout</a>
            </div>

            <div class="welcome-container">
                <img src="images/logo-no-background.png" alt="Logo">
                <h1>RouteX - Admin Homepage</h1>

                <form action="sendCommunicationn.jsp" method="post">
                    <button class="welcome-button" type="submit">Send Communication</button>
                </form>
                <br>
                <form action="PathInfoRAS" method="get">
                    <button class="welcome-button" type="submit">View Reports And Statistics</button>
                </form>

            </div>
        </div>
    </div>

    <div class="metro-logos">
        <i class="fas fa-subway"></i>
        <i class="fas fa-train"></i>
        <i class="fas fa-bus"></i>
        <i class="fas fa-map-marker-alt"></i>
    </div>

    <!-- ALERT PER SUCCESSO/ERRORE -->
    <%
        String msg = (String) session.getAttribute("alertMessage");
        if (msg != null && !msg.isEmpty()) {
    %>

        <script>
            window.onload = function() {
                alert("<%= msg %>");
            };
        </script>

    <%
        session.removeAttribute("alertMessage");
        }
    %>

    <%
        String successRegister = (String) session.getAttribute("successRegister");
        if (successRegister != null && !successRegister.isEmpty()) {
    %>

        <script>
            window.onload = function() {
                alert("<%= successRegister %>");
            };
        </script>

    <%
        session.removeAttribute("successRegister");
        }
    %>

</body>
</html>