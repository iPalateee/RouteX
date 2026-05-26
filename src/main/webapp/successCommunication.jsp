<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <title>RouteX - Success</title>

    <%@ include file="imports.jsp" %>
    <style>
        body {
            background-color: #f3fbf6;
            font-family: "Segoe UI", sans-serif;
            color: #212529;
            height: 100vh;
            margin: 0;
            display: flex;
            justify-content: center;
            align-items: center;
        }

        .success-container {
            background-color: white;
            padding: 40px;
            border-radius: 15px;
            box-shadow: 0 4px 15px rgba(0, 0, 0, 0.15);
            text-align: center;
            max-width: 500px;
        }

        .success-icon {
            font-size: 55px;
            color: #28a745;
            margin-bottom: 20px;
        }

        h1 {
            color: #28a745;
            font-size: 26px;
            margin-bottom: 10px;
        }

        p {
            font-size: 16px;
            margin-bottom: 30px;
        }

        .btn-home {
            background-color: #28a745;
            color: white;
            border: none;
            padding: 10px 22px;
            border-radius: 10px;
            font-size: 16px;
            text-decoration: none;
            transition: background-color 0.3s ease;
        }

        .btn-home:hover {
            background-color: #218838;
        }
    </style>
</head>

<body>

<%@ include file="header.jspf" %>

<%
    String successTitle = (String) request.getAttribute("successTitle");
    String successMessage = (String) request.getAttribute("successMessage");
%>

<div class="success-container">
    <i class="fas fa-check-circle success-icon"></i>

    <h1><%= successTitle %></h1>

    <p><%= successMessage %></p>

    <a href="indexAdmin.jsp" class="btn-home">
        <i class="fas fa-home"></i> Torna alla Home
    </a>
</div>

</body>
</html>
