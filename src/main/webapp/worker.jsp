<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <title>Worker Area</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/lib/bootstrap/dist/css/bootstrap.css">
    <%@ include file="imports.jsp" %>
    <style>
        body {
            background-color: #f8f9fa;
            font-family: "Segoe UI", sans-serif;
        }

        .menu-container {
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            gap: 80px;
        }

        .menu-item {
            text-align: center;
            transition: transform 0.3s ease, box-shadow 0.3s ease;
        }

        .menu-item .circle {
            width: 150px;
            height: 150px;
            border-radius: 50%;
            background: linear-gradient(135deg, #007bff, #0056b3);
            color: white;
            display: flex;
            justify-content: center;
            align-items: center;
            font-size: 50px;
            margin: 0 auto 15px auto;
            box-shadow: 0 6px 15px rgba(0, 0, 0, 0.2);
        }

        .menu-item:hover {
            transform: translateY(-10px);
        }

        .menu-item p {
            font-weight: bold;
            font-size: 18px;
            color: #212529;
        }

        .menu-item a {
            text-decoration: none;
        }
    </style>
</head>
<body>
    <div class="menu-container">
        <!-- 🔹 A sinistra -->
        <div class="menu-item">
            <a href="allReports">
                <div class="circle">
                    <i class="fas fa-exclamation-circle"></i>
                </div>
                <p>Visualizza Segnalazioni</p>
            </a>
        </div>

        <div class="menu-item">
            <a href="workSchedule">
                <div class="circle">
                    <i class="fas fa-clock"></i>
                </div>
                <p>Visualizza Orario di Lavoro</p>
            </a>
        </div>
    </div>
</body>
</html>
