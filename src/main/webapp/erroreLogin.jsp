<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<html lang="it">
<head>
    <title>Errore di login</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f2dede;
            color: #a94442;
            display: flex;
            align-items: center;
            justify-content: center;
            height: 100vh;
        }
        .box {
            background: white;
            padding: 2rem;
            border-radius: 8px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.2);
        }
        h2 { margin-bottom: 1rem; }
        a {
            color: #a94442;
            text-decoration: none;
            font-weight: bold;
        }
    </style>
</head>
<body>
<%@ include file="header.jspf" %>
<div class="box">
    <h2>Accesso non riuscito</h2>
    <p>${messaggioErrore}</p>
    <a href="login.jsp">Torna al login</a>
</div>

</body>
</html>
