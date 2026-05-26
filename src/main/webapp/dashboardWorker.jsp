<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="it">
<head>
<!-- Font Awesome CSS -->
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css" integrity="sha512-1ycn6IcaQQ40/MKBW2W4Rhis/DbILU74C1vSrLJxCq57o941Ym01SwNsOMqvEBFlcgUa6xLiPY/NS5R+E6ztJQ==" crossorigin="anonymous" referrerpolicy="no-referrer">
<title>RouteX - Metro Finder</title>
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

/* Sfocatura per il background */
.background-blur {
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    backdrop-filter: blur(6px);
    z-index: -1;
}

/* Contenitore principale */
.main-container {
    display: flex;
    flex-direction: row;
    align-items: center;
    width: 100%;
    height: 100%;
    color: white;
}

/* Box a sinistra per logo e motto */
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

/* Contenitore centrale per pulsanti e contenuto */
.right-content {
    flex: 3;
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    padding: 40px;
}

.right-content .welcome-container {
    text-align: center;
    background: rgba(0, 0, 0, 0.7);
    padding: 30px;
    border-radius: 15px;
    box-shadow: 0 4px 20px rgba(0, 0, 0, 0.3);
}

.right-content .welcome-container img {
    animation: fadeIn 2s ease-in-out;
    width: 200px;
    margin-bottom: 20px;
}

.right-content .welcome-container h1 {
    color: white;
    font-size: 26px;
    margin-bottom: 20px;
}

.welcome-button {
    background-color: #007bff;
    color: white;
    border: none;
    padding: 10px 20px;
    border-radius: 15px;
    font-size: 18px;
    cursor: pointer;
    transition: background-color 0.3s ease;
    margin-bottom: 10px;
}

.welcome-button:hover {
    background-color: #0056b3;
}

/* Pulsanti di Login e Registrati */
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

/* --- Modal scheda work schedule --- */
.modal-scheda {
    display: none; /* verrà mostrato via JS se workerSchedule non è vuoto */
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

            <!-- Submit verso la servlet viewWorkSchedule -->
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