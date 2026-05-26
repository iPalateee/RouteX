<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    Integer oraInizio = (Integer) request.getAttribute("oraInizio");
    Integer oraFine = (Integer) request.getAttribute("oraFine");
    String luogoDiLavoro = (String) request.getAttribute("luogoDiLavoro");
    Integer durataTurno = (Integer) request.getAttribute("durataTurno");
%>

<!DOCTYPE html>
<html lang="it">
<head>
    <title>Orario di lavoro</title>

    <%@ include file="imports.jsp" %>

    <style>
        body {
            background-color: #f5f6f8;
        }
        .card-custom {
            max-width: 500px;
            margin: 80px auto;
            border-radius: 12px;
            box-shadow: 0 6px 18px rgba(0,0,0,0.1);
        }
        .title {
            font-weight: 600;
        }
        .label {
            color: #6c757d;
            font-size: 0.9rem;
        }
    </style>
</head>

<body>

<div class="card card-custom">
    <div class="card-body">

        <h4 class="card-title title mb-4 text-center">
            Orario di lavoro
        </h4>

        <div class="mb-3">
            <div class="label">Ora di inizio</div>
            <div class="fs-5">
                <%= oraInizio %>:00
            </div>
        </div>

        <div class="mb-3">
            <div class="label">Ora di fine</div>
            <div class="fs-5">
                <%= oraFine %>:00
            </div>
        </div>

        <div class="mb-3">
            <div class="label">Luogo di lavoro</div>
            <div class="fs-5">
                <%= luogoDiLavoro %>
            </div>
        </div>

        <div class="mb-4">
            <div class="label">Durata turno</div>
            <div class="fs-5">
                <%= durataTurno %> ore
            </div>
        </div>

        <div class="text-center">
            <a href="<%= request.getContextPath() %>/dashboardWorker.jsp"
               class="btn btn-primary">
                Torna alla dashboard
            </a>
        </div>

    </div>
</div>

</body>
</html>
