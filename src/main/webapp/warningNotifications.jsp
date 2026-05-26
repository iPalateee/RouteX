<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <title>RouteX - Warning</title>

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

        .warning-container {
            background-color: #ffffff;
            padding: 40px;
            border-radius: 15px;
            box-shadow: 0 4px 15px rgba(0, 0, 0, 0.15);
            text-align: center;
            max-width: 500px;
        }

        .warning-icon {
            font-size: 52px;
            color: #28a745;
            margin-bottom: 20px;
        }

        h1 {
            color: #28a745;
            font-size: 26px;
            margin-bottom: 15px;
            font-weight: 600;
        }

        .details {
            background-color: #f6fff9;
            border: 1px solid #c3e6cb;
            border-radius: 10px;
            padding: 12px;
            font-size: 15px;
            color: #155724;
            text-align: left;
            margin-bottom: 30px;
            white-space: pre-wrap;
        }

        .btn-home {
            background-color: #28a745;
            color: white;
            border: none;
            padding: 10px 24px;
            border-radius: 10px;
            font-size: 16px;
            text-decoration: none;
            transition: background-color 0.3s ease;
            display: inline-block;
        }

        .btn-home:hover {
            background-color: #218838;
        }
    </style>
</head>

<body>

<%@ include file="header.jspf" %>

<div class="warning-container">
    <i class="fas fa-check-circle warning-icon"></i>

    <h1><%= request.getAttribute("warningTitle") %></h1>

    <div class="details">
        <strong>Dettagli:</strong><br>
        <%= org.apache.commons.lang3.StringEscapeUtils.escapeHtml4(
                (String) request.getAttribute("warningMessage")) %>
    </div>

    <a href="dashboardWorker.jsp" class="btn-home">
        <i class="fas fa-home"></i> Torna alla Home
    </a>
</div>

</body>
</html>
