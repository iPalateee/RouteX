<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="it">
<head>
    <title>RouteX - Login</title>

    <style>
        body {
            background: linear-gradient(to bottom, #e0f7fa, #80deea);
            font-family: 'Arial Rounded MT Bold', sans-serif;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
            position: relative;
        }

        .login-container {
            background: rgba(255, 255, 255, 0.9);
            padding: 40px;
            border-radius: 20px;
            max-width: 600px;
            width: 100%;
            text-align: center;
            animation: slideIn 1s ease-out;
            box-shadow: 0 0 15px rgba(0, 0, 0, 0.2);
        }

        h2 {
            margin-bottom: 25px;
            font-size: 28px;
            color: #007bff;
        }

        form {
            display: flex;
            flex-direction: column;
            align-items: center;
        }

        .form-group {
            width: 80%;
            margin-bottom: 15px;
            text-align: left;
        }

        .form-group label {
            display: block;
            margin-bottom: 8px;
            color: #333;
            font-size: 16px;
        }

        input[type="text"],
        input[type="email"],
        input[type="password"] {
            width: 100%;
            padding: 12px;
            border: 1px solid #007bff;
            border-radius: 10px;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
            font-size: 16px;
            transition: all 0.3s ease;
            box-sizing: border-box;
        }

        input:focus {
            outline: none;
            border-color: #0056b3;
            box-shadow: 0 0 8px rgba(0, 91, 187, 0.5);
        }

        button {
            background-color: #007bff;
            color: white;
            border: none;
            padding: 12px 20px;
            margin-top: 15px;
            border-radius: 15px;
            font-size: 18px;
            cursor: pointer;
            transition: background-color 0.3s ease, transform 0.2s ease;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
        }

        button:hover {
            background-color: #0056b3;
            transform: translateY(-2px);
        }

        .button-container-right {
            position: absolute;
            top: 20px;
            right: 40px;
            display: flex;
            gap: 10px;
        }

        .button-container-right a {
            background-color: #007bff;
            color: white;
            padding: 10px 15px;
            font-size: 16px;
            border-radius: 10px;
            text-decoration: none;
        }

        .button-container-right a:hover {
            background-color: #0056b3;
        }
    </style>
</head>

<body>

    <div class="button-container-right">
        <a href="index.jsp">Home</a>
    </div>

    <div class="login-container">
        <h2>RouteX - Login</h2>

        <form action="login" method="post">

            <div class="form-group">
                <label for="email">Email Address:</label>
                <input type="text" id="email" name="Email" placeholder="Email Address">
            </div>

            <div class="form-group">
                <label for="password">Password:</label>
                <input type="password" id="password" name="Password" placeholder="Password">
            </div>

            <button type="submit">Login</button>

        </form>
    </div>

</body>
</html>