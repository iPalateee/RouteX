<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="it">
<head>
    <title>Avvio applicazione</title>

    <style>
        * {
            box-sizing: border-box;
            font-family: "Segoe UI", Arial, sans-serif;
        }

        body {
            margin: 0;
            padding: 0;
            height: 100vh;
            background: linear-gradient(135deg, #1e3c72, #2a5298);
            display: flex;
            justify-content: center;
            align-items: center;
            color: #ffffff;
        }

        .container {
            background: rgba(0, 0, 0, 0.65);
            padding: 40px 50px;
            border-radius: 16px;
            box-shadow: 0 10px 30px rgba(0, 0, 0, 0.4);
            text-align: center;
            width: 380px;
        }

        .container h2 {
            margin-bottom: 25px;
            font-size: 24px;
            font-weight: 600;
        }

        .subtitle {
            font-size: 14px;
            opacity: 0.85;
            margin-bottom: 30px;
        }

        .mode-button {
            width: 100%;
            padding: 14px;
            margin: 12px 0;
            font-size: 16px;
            font-weight: 600;
            border: none;
            border-radius: 12px;
            cursor: pointer;
            transition: transform 0.2s ease, box-shadow 0.2s ease;
        }

        .demo {
            background-color: #17a2b8;
            color: white;
        }

        .full {
            background-color: #28a745;
            color: white;
        }

        .mode-button:hover {
            transform: translateY(-2px);
            box-shadow: 0 6px 15px rgba(0, 0, 0, 0.3);
        }

        .footer-note {
            margin-top: 25px;
            font-size: 12px;
            opacity: 0.7;
        }
    </style>
</head>

<body>

<div class="container">
    <h2>Seleziona modalità di esecuzione</h2>
    <div class="subtitle">
        Scegli come avviare l'applicazione
    </div>

    <form action="selectMode" method="post">
        <button class="mode-button demo" type="submit" name="mode" value="DEMO">
            Demo Version
        </button>

        <button class="mode-button full" type="submit" name="mode" value="FULL">
            Full Version
        </button>
    </form>

    <div class="footer-note">
        La modalità selezionata resterà attiva per tutta la sessione applicativa
    </div>
</div>

</body>
</html>
