<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <title>RouteX - Metro Finder</title>
    <%@ include file="imports.jsp" %>
    <style>
        body {
            margin: 0;
            padding: 0;
            font-family: 'Arial Rounded MT Bold', sans-serif;
            height: 100vh;
            display: flex;
            align-items: center;
            background: url('images/light.jpg') no-repeat center center/cover;
            overflow: hidden;
            position: relative;
        }

        .background-blur {
            position: absolute;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            backdrop-filter: blur(6px);
            z-index: -1;
        }

        .main-container {
            display: flex;
            flex-direction: row;
            align-items: center;
            width: 100%;
            height: 100%;
            color: white;
        }

        .left-box {
            flex: 1;
            padding: 30px;
            background: rgba(0, 0, 0, 0.6);
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
            height: 100%;
            box-shadow: 2px 0 10px rgba(0, 0, 0, 0.5);
        }

        .left-box img {
            width: 150px;
            margin-bottom: 20px;
        }

        .left-box h1 {
            font-size: 24px;
            text-align: center;
            margin-bottom: 10px;
        }

        .left-box p {
            font-size: 18px;
            text-align: center;
            line-height: 1.5;
        }

        .right-content {
            flex: 3;
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
            padding: 40px;
        }

        .welcome-container {
            text-align: center;
            background: rgba(0, 0, 0, 0.7);
            padding: 30px;
            border-radius: 15px;
            box-shadow: 0 4px 20px rgba(0, 0, 0, 0.3);
        }

        .welcome-container img {
            animation: fadeIn 2s ease-in-out;
            width: 200px;
            margin-bottom: 20px;
        }

        .welcome-container h1 {
            color: white;
            font-size: 26px;
            margin-bottom: 20px;
        }

        .welcome-button, .buy-button {
            background-color: #007bff;
            color: white;
            border: none;
            padding: 10px 20px;
            border-radius: 15px;
            font-size: 18px;
            cursor: pointer;
            transition: background-color 0.3s ease;
            margin-top: 10px;
        }

        .welcome-button:hover, .buy-button:hover {
            background-color: #0056b3;
        }

        .button-container {
            position: absolute;
            top: 20px;
            right: 40px;
            display: flex;
            gap: 10px;
        }

        .button-container a {
            background-color: #007bff;
            color: white;
            padding: 10px 15px;
            font-size: 16px;
            border: none;
            cursor: pointer;
            border-radius: 10px;
            text-decoration: none;
            text-align: center;
            transition: background-color 0.3s ease;
        }

        .button-container a:hover {
            background-color: #0056b3;
        }

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
            font-size: 50px;
            color: white;
            transition: transform 0.3s ease;
        }

        .metro-logos i:hover {
            transform: scale(1.2);
        }
    </style>
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
