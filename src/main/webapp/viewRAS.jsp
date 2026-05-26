<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="it.web.routex.bean.ReportsStatsBean"%>
<%@ page import="it.web.routex.bean.PathInfoBean"%>
<%@ page import="java.util.Set"%>
<%@ page import="java.util.List"%>

<%
    ReportsStatsBean stats =
            (ReportsStatsBean) request.getAttribute("stats");

    List<PathInfoBean> pathList = stats.getPaths();
    Set<String> utenti = stats.getUtenti();
%>

<!DOCTYPE html>
<html lang="it">
<head>
    <title>RouteX - Reports & Statistics</title>
    <%@ include file="imports.jsp" %>

    <style>
        body {
            margin:0;
            padding:0;
            font-family:'Arial Rounded MT Bold', sans-serif;
            height:100vh;
            display:flex;
            align-items:center;
            background:url('images/light.jpg') no-repeat center center/cover;
            overflow:hidden;
            position:relative;
            color:white;
        }

        .background-blur {
            position:absolute;
            inset:0;
            backdrop-filter:blur(6px);
            z-index:-1;
        }

        .main-container {
            display:flex;
            width:100%;
            height:100%;
        }

        .left-box {
            flex:1;
            padding:30px;
            background:rgba(0,0,0,0.6);
            display:flex;
            flex-direction:column;
            align-items:center;
            justify-content:center;
            box-shadow:2px 0 10px rgba(0,0,0,0.5);
        }

        .left-box img {
            width:150px;
            margin-bottom:20px;
        }

        .right-content {
            flex:3;
            padding:40px;
            overflow-y:auto;
            position:relative;
        }

        .button-container {
            position:absolute;
            top:20px;
            right:40px;
            display:flex;
            gap:10px;
        }

        .button-container a {
            background:#007bff;
            color:white;
            padding:10px 15px;
            border-radius:10px;
            text-decoration:none;
        }

        .stats-container {
            display:grid;
            grid-template-columns:repeat(auto-fit,minmax(250px,1fr));
            gap:20px;
            margin-top:40px;
        }

        .stat-card {
            background:rgba(255,255,255,0.9);
            color:black;
            padding:20px;
            border-radius:15px;
            text-align:center;
            box-shadow:0 4px 15px rgba(0,0,0,0.3);
        }

        .stat-card i {
            font-size:40px;
            color:#007bff;
            margin-bottom:10px;
        }

        table {
            width:100%;
            margin-top:40px;
            border-collapse:collapse;
            background:rgba(255,255,255,0.95);
            color:black;
            font-size:14px;
            border-radius:10px;
            overflow:hidden;
        }

        th, td {
            padding:6px 8px;
            border:1px solid #ccc;
            text-align:center;
        }

        th {
            background:#007bff;
            color:white;
        }

        tr:nth-child(even) {
            background:#f5f5f5;
        }

        .action-bar {
            margin-top:30px;
            display:flex;
            gap:15px;
            align-items:center;
        }

        #userList {
            list-style:none;
            margin:0;
            padding:0;
            background:white;
            color:black;
            border-radius:5px;
            max-height:200px;
            overflow-y:auto;
            display:none;
            position:absolute;
            z-index:100;
        }

        #userList li {
            padding:6px 10px;
            cursor:pointer;
        }

        #userList li:hover {
            background:#007bff;
            color:white;
        }
    </style>
</head>

<body>
<div class="background-blur"></div>

<div class="main-container">

    <!-- SINISTRA -->
    <div class="left-box">
        <img src="images/logo-no-background.png" alt="Logo">
        <h2>RouteX</h2>
        <p>Administrative Reports & Statistics</p>
    </div>

    <!-- DESTRA -->
    <div class="right-content">

        <div class="button-container">
            <a href="indexAdmin.jsp">Home</a>
            <a href="logout">Logout</a>
        </div>

        <h1>Reports & Statistics</h1>

        <!-- STATISTICHE -->
        <div class="stats-container">
            <div class="stat-card">
                <i class="fas fa-route"></i>
                <h3>Total Trips</h3>
                <p><%= stats.getTotalTrips() %></p>
            </div>

            <div class="stat-card">
                <i class="fas fa-road"></i>
                <h3>Total Distance</h3>
                <p><%= String.format("%.2f", stats.getTotalDistance()) %> km</p>
            </div>

            <div class="stat-card">
                <i class="fas fa-clock"></i>
                <h3>Total Time</h3>
                <p><%= String.format("%.2f", stats.getTotalTime()) %> h</p>
            </div>
        </div>



        <!-- TABELLA COMPLETA -->
        <table>
            <tr>
                <th>Start</th><th>End</th><th>City</th><th>User</th>
                <th>Changes</th><th>Time</th><th>Distance</th>
            </tr>

            <% for(PathInfoBean p : pathList){ %>
                <tr>
                    <td><%= p.getStartStation() %></td>
                    <td><%= p.getEndStation() %></td>
                    <td><%= p.getCity() %></td>
                    <td><%= p.getUtente() %></td>
                    <td><%= p.getNCambi() %></td>
                    <td><%= p.getTempoDiArrivo() %></td>
                    <td><%= String.format("%.2f", p.getPercTerrenoUtilizzato()) %></td>
                </tr>
            <% } %>
        </table>
    </div>
</div>
</body>
</html>
