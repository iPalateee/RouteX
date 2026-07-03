<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <title>RouteX • Send Communication</title>

    <%@ include file="imports.jsp" %>
    <style>
        body {
            margin: 0;
            height: 100vh;
            background-color: #0f1115;
            font-family: "Segoe UI", sans-serif;
            display: flex;
            align-items: center;
            justify-content: center;
            color: #eaeaea;
        }

        .container {
            background-color: #181b20;
            padding: 40px;
            border-radius: 16px;
            width: 520px;
            box-shadow: 0 0 40px rgba(0,0,0,0.6);
            border: 1px solid #2a2e35;
        }

        h1 {
            margin-top: 0;
            font-size: 24px;
            color: #ffffff;
            text-align: center;
        }

        p {
            font-size: 15px;
            color: #b0b3b8;
            text-align: center;
            margin-bottom: 20px;
        }

        textarea {
            width: 100%;
            height: 150px;
            background-color: #0f1115;
            color: #eaeaea;
            border: 1px solid #2f333a;
            border-radius: 12px;
            padding: 14px;
            font-size: 15px;
            resize: none;
            outline: none;
        }

        textarea::placeholder {
            color: #6c7078;
        }

        textarea:focus {
            border-color: #007bff;
        }

        .counter {
            text-align: right;
            font-size: 13px;
            color: #7d818a;
            margin-top: 6px;
        }

        .actions {
            margin-top: 30px;
            display: flex;
            justify-content: center;
            gap: 16px;
        }

        .btn {
            padding: 10px 26px;
            font-size: 15px;
            border-radius: 14px;
            border: none;
            cursor: pointer;
            transition: background-color 0.3s ease;
        }

        .btn-send {
            background-color: #007bff;
            color: white;
        }

        .btn-send:hover {
            background-color: #0056b3;
        }

        .btn-back {
            background-color: #3a3f46;
            color: white;
            text-decoration: none;
            display: flex;
            align-items: center;
            justify-content: center;
        }

        .btn-back:hover {
            background-color: #2c3036;
        }

        .icon {
            text-align: center;
            font-size: 42px;
            color: #007bff;
            margin-bottom: 10px;
        }
    </style>
</head>

<body>

<div class="container">

    <div class="icon">
        <i class="fas fa-bullhorn"></i>
    </div>

    <h1>System Communication</h1>
    <p>This message will be delivered to all workers of the platform.</p>

    <form action="confirmCommunication" method="post">
        <label for="message">Message:</label>
        <textarea
                id="message"
                name="message"
                maxlength="250"
        ></textarea>

        <div class="counter" id="counter">0 / 250</div>

        <div class="actions">
            <button type="submit" class="btn btn-send">Send</button>
            <a href="indexAdmin.jsp" class="btn btn-back">Back</a>
        </div>
    </form>

</div>



</body>
</html>
