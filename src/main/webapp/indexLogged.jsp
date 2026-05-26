<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <title>RouteX - Metro Finder</title>
    <%@ include file="imports.jsp" %>
    <link rel="stylesheet" href="css/index.css">
</head>

<body>

<%@ include file="header.jspf" %>

<div class="background-blur"></div>

<div class="main-container">
    <div class="left-box">
        <img src="images/logo-no-background.png" alt="Logo">
        <h1>RouteX</h1>
        <p>Navigating the Future,<br>One Stop at a Time</p>
    </div>

    <div class="right-content">

        <!--  BOTTONI POST-LOGIN -->
        <div class="button-container">
            <a href="indexLogged.jsp">Home</a>
            <a href="areaRiservata">Area Riservata</a>
            <a href="logout" class="logout-link">Logout</a>
        </div>

        <div class="welcome-container">
            <img src="images/logo-no-background.png" alt="Logo">
            <h1>Welcome to RouteX!</h1>


            <form action="PathControllerGrafico" method="get">
                <button class="welcome-button" type="submit">Start Exploring</button>
            </form>


            <form action="buyTicket" method="get">
                <button class="buy-button" type="submit">Buy Ticket</button>
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

</body>
</html>
