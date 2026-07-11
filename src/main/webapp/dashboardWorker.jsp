<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="it">
<head>
<%@ include file="imports.jsp" %>
<title>RouteX - Metro Finder</title>
<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/index.css">
<style>
/* Sezione per loghi della metropolitana */
.metro-logos {
    position: absolute;
    right: 20px;
    bottom: 20px;
    display: flex;
    flex-direction: column;
    align-items: center;
    gap: 15px;
}

.metro-logos img, .metro-logos i {
    width: 50px;
    height: 50px;
    border-radius: 50%;
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.5);
    transition: transform 0.3s ease;
}

.metro-logos img:hover, .metro-logos i:hover {
    transform: scale(1.2);
}

.modal-scheda {
    display: none;
    position: fixed;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    width: 350px;
    background: rgba(255,255,255,0.95);
    border-radius: 15px;
    box-shadow: 0 4px 20px rgba(0,0,0,0.5);
    z-index: 9999;
    padding: 20px;
    color: #000;
}
.modal-scheda h5 { text-align: center; margin-bottom: 15px; }
.modal-scheda table { width: 100%; font-size: 14px; }
.modal-scheda table th, .modal-scheda table td { text-align: center; padding: 5px; }
.modal-close { float: right; cursor: pointer; font-weight: bold; font-size: 18px; }
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
            <a href="dashboardWorker.jsp">Home</a>
            <a href="logout" class="logout-link">Logout</a>
        </div>

        <div class="welcome-container">
            <img src="images/logo-no-background.png" alt="Logo">
            <h1>Welcome to RouteX!</h1>

            <form action="viewNotifications" method="get">
                <button class="welcome-button" type="submit">View Notifications</button>
            </form>

            <form action="viewWorkSchedule" method="get">
                <button class="welcome-button" type="submit">View Work Schedule</button>
            </form>

        </div>
    </div>
</div>

<div class="metro-logos">
    <i class="fas fa-subway" style="font-size: 50px; color: white;"></i>
    <i class="fas fa-train" style="font-size: 50px; color: white;"></i>
    <i class="fas fa-bus" style="font-size: 50px; color: white;"></i>
    <i class="fas fa-map-marker-alt" style="font-size: 50px; color: white;"></i>
</div>



</body>
</html>