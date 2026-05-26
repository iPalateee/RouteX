<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <title>RouteX - Admin Error</title>
    <%@ include file="imports.jsp" %>
    <style>
        body {
            background-color: #f8f9fa;
            font-family: "Segoe UI", sans-serif;
            color: #212529;
            height: 100vh;
            margin: 0;
            display: flex;
            justify-content: center;
            align-items: center;
        }

        .error-container {
            background-color: white;
            padding: 40px;
            border-radius: 15px;
            box-shadow: 0 4px 15px rgba(0, 0, 0, 0.2);
            text-align: center;
            max-width: 520px;
        }

        .error-icon {
            font-size: 50px;
            color: #dc3545;
            margin-bottom: 20px;
        }

        h1 {
            color: #dc3545;
            font-size: 26px;
            margin-bottom: 10px;
        }

        p {
            font-size: 16px;
            margin-bottom: 30px;
        }

        .btn-home {
            background-color: #007bff;
            color: white;
            border: none;
            padding: 10px 22px;
            border-radius: 10px;
            font-size: 16px;
            text-decoration: none;
            transition: background-color 0.3s ease;
        }

        .btn-home:hover {
            background-color: #0056b3;
        }

        .details {
            background-color: #f8f9fa;
            border: 1px solid #dee2e6;
            border-radius: 10px;
            padding: 12px;
            font-size: 14px;
            color: #6c757d;
            text-align: left;
            margin-bottom: 20px;
            white-space: pre-wrap;
        }
    </style>
</head>

<body>

<%@ include file="header.jspf" %>

<div class="error-container">
    <i class="fas fa-exclamation-triangle error-icon"></i>
    <h1>Admin Operation Failed</h1>

    <%
        String errore = (String) request.getAttribute("errore");
    %>

    <% if (errore != null) { %>
        <p>An error occurred while performing an administrative operation:</p>

        <div class="details">
            <strong>Details:</strong><br>
            <%= org.apache.commons.lang3.StringEscapeUtils.escapeHtml4(errore) %>
        </div>
    <% } else { %>
        <p>An unexpected administrative error occurred. Please try again later.</p>
    <% } %>

    <a href="indexAdmin.jsp" class="btn-home">
        <i class="fas fa-home"></i> Back to Admin Home
    </a>
</div>

</body>
</html>
