<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<!DOCTYPE html>
<html lang="it">
<head>
    <title>Errore nel pagamento</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f8d7da;
            color: #721c24;
            display: flex;
            align-items: center;
            justify-content: center;
            height: 100vh;
            margin: 0;
        }

        .box {
            background: white;
            padding: 2rem;
            border-radius: 10px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.2);
            text-align: center;
            max-width: 450px;
        }

        h2 {
            margin-bottom: 1rem;
            font-size: 24px;
        }

        p {
            font-size: 18px;
            margin-bottom: 1.5rem;
            white-space: pre-line; /* supporta messaggi multilinea */
        }

        .links a {
            display: inline-block;
            margin: 0.4rem;
            padding: 10px 20px;
            color: white;
            text-decoration: none;
            border-radius: 6px;
            font-weight: bold;
        }

        .retry-btn {
            background-color: #6c757d;
        }

        .retry-btn:hover {
            background-color: #5a6268;
        }

        .home-btn {
            background-color: #007bff;
        }

        .home-btn:hover {
            background-color: #0056b3;
        }
    </style>
</head>

<body>

<%@ include file="header.jspf" %>

<div class="box">
    <h2>Pagamento non riuscito</h2>

    <p>${messaggioErrore}</p>

    <div class="links">
        <a href="buyTicket" class="retry-btn">Riprova pagamento</a>
        <a href="indexLogged.jsp" class="home-btn">Torna alla Home</a>
    </div>
</div>

</body>
</html>
