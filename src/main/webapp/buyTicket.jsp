<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <title>RouteX - Buy Ticket</title>

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="<%= request.getContextPath() %>/lib/bootstrap/dist/css/bootstrap.css">

    <%@ include file="imports.jsp" %>

    <style>
        body {
            background-color: #f8f9fa;
            color: #212529;
            font-family: "Segoe UI", sans-serif;
        }

        .main-container {
            display: flex;
            width: 100%;
            min-height: 100vh;
        }

        .right-content {
            flex: 1;
            padding: 40px;
            background-color: #ffffff;
        }

        .button-container {
            display: flex;
            justify-content: flex-end;
            gap: 10px;
            margin-bottom: 20px;
        }

        .button-container a {
            background-color: #007bff;
            color: white;
            padding: 10px 15px;
            font-size: 16px;
            border-radius: 10px;
            text-decoration: none;
            transition: background-color 0.3s ease;
        }

        .button-container a:hover {
            background-color: #0056b3;
        }

        .form-container {
            background-color: #ffffff;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 4px 12px rgba(0,0,0,0.1);
            max-width: 500px;
            margin: 0 auto;
        }

        label {
            font-weight: bold;
        }

        .btn-submit {
            background-color: #007bff;
            color: white;
            padding: 10px 15px;
            font-size: 16px;
            border-radius: 10px;
            border: none;
            transition: background-color 0.3s ease;
        }

        .btn-submit:hover {
            background-color: #0056b3;
        }

        .metro-logos {
            position: fixed;
            right: 20px;
            bottom: 20px;
            display: flex;
            flex-direction: column;
            align-items: center;
            gap: 15px;
        }

        .metro-logos i {
            font-size: 40px;
            color: #6c757d;
        }
    </style>
</head>
<body>
    <%@ include file="header.jspf" %>
    <div class="main-container">
        <div class="right-content">
            <div class="button-container">
                <a href="indexLogged.jsp">Home</a>
                <a href="logout">Logout</a>
            </div>

            <div class="form-container">
                <h3>Buy Your Ticket</h3>
                <form action="buyTicket" method="post">
                    <div class="mb-3">
                        <label for="city" class="form-label">Select City</label>
                        <select id="city" name="city" class="form-select">
                            <option value="" disabled selected>-- Choose a city --</option>
                            <%
                                List<it.web.routex.bean.CityBean> cities = (List<it.web.routex.bean.CityBean>) request.getAttribute("cities");
                                if (cities != null && !cities.isEmpty()) {
                                    for (it.web.routex.bean.CityBean c : cities) {
                            %>
                                        <option value="<%= c.getName() %>"><%= c.getName() %></option>
                            <%
                                    }
                                } else {
                            %>
                                <option disabled>(No cities available)</option>
                            <%
                                }
                            %>

                        </select>
                    </div>

                    <div class="mb-3">
                        <label for="quantity" class="form-label">Number of Tickets</label>
                        <input type="text" id="quantity" name="quantity" class="form-control">
                    </div>

                    <button type="submit" class="btn-submit">Confirm</button>
                </form>
            </div>
        </div>
    </div>

    <!-- Icone metro -->
    <div class="metro-logos">
        <i class="fas fa-subway"></i>
        <i class="fas fa-train"></i>
        <i class="fas fa-bus"></i>
        <i class="fas fa-map-marker-alt"></i>
    </div>
</body>
</html>
