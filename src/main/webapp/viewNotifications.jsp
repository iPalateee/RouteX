<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="it.web.routex.bean.MessageBean" %>
<%@ page import="java.text.SimpleDateFormat" %>

<%
    List<MessageBean> notifiche =(List<MessageBean>) request.getAttribute("notifiche");

    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
%>

<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <title>RouteX • Notifiche</title>

    <!-- Bootstrap -->
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/lib/bootstrap/dist/css/bootstrap.css">
    <%@ include file="imports.jsp" %>
    <style>
        body {
            background: linear-gradient(135deg, #eef2f7, #f8f9fa);
            font-family: "Segoe UI", sans-serif;
        }

        .page-container {
            max-width: 1100px;
            margin: 60px auto;
        }

        .page-header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 25px;
        }

        .page-title {
            font-weight: 600;
            color: #2c3e50;
        }

        .card-custom {
            background-color: #ffffff;
            border-radius: 14px;
            padding: 25px;
            box-shadow: 0 8px 25px rgba(0,0,0,0.08);
        }

        table thead {
            background-color: #f1f3f5;
        }

        table th {
            font-weight: 600;
            color: #495057;
        }

        table tbody tr:hover {
            background-color: #f8f9fa;
        }

        .checkbox-custom {
            transform: scale(1.2);
            cursor: pointer;
        }

        .btn-primary {
            border-radius: 10px;
        }

        .btn-success {
            border-radius: 10px;
            padding: 8px 22px;
        }

        .action-buttons a {
            margin-left: 8px;
        }

        .table {
            table-layout: fixed;
            width: 100%;
        }

        .table td {
            word-wrap: break-word;
            overflow-wrap: break-word;
            word-break: break-word;
        }

    </style>
</head>

<body>

<div class="page-container">

    <!-- HEADER -->
    <div class="page-header">
        <h2 class="page-title"> Notifiche di sistema</h2>

        <div class="action-buttons">
            <a href="dashboardWorker.jsp" class="btn btn-primary">Home</a>
            <a href="logout" class="btn btn-outline-danger">Logout</a>
        </div>
    </div>

    <!-- CARD -->
    <div class="card-custom">

        <form action="updateNotifications" method="post">

            <div class="table-responsive">
                <table class="table table-striped align-middle">
                    <thead>
                    <tr>
                        <th style="width: 55%">Messaggio</th>
                        <th style="width: 25%">Data</th>
                        <th style="width: 20%" class="text-center">Risolta</th>
                    </tr>
                    </thead>
                    <tbody>

                    <%
                            for (MessageBean m : notifiche) {
                    %>
                        <tr>
                            <td><%= m.getMessage() %></td>
                            <td><%= sdf.format(m.getDate()) %></td>
                            <td class="text-center">
                                <label for="risolta_<%= m.getDate().getTime() %>" class="visually-hidden">Segna come risolta</label>
                                <input type="checkbox" id="risolta_<%= m.getDate().getTime() %>" class="checkbox-custom" name="risolte" value="<%= m.getDate().getTime() + "|" + m.getMessage() %>" />
                            </td>
                        </tr>
                    <%
                            }
                    %>

                    </tbody>
                </table>
            </div>

            <div class="text-end mt-4">
                <button type="submit" class="btn btn-success">
                    Salva modifiche
                </button>
            </div>

        </form>
    </div>
</div>

</body>
</html>
