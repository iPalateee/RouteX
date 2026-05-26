<%@ page import="java.util.List" %>
<%@ page import="it.web.routex.model.Segnalazione" %>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <title>Tutte le Segnalazioni</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/lib/bootstrap/dist/css/bootstrap.css">
</head>
<body class="bg-light">
<div class="container mt-5">
    <h2 class="mb-4">Segnalazioni Utenti</h2>

    <%
        List<Segnalazione> lista = (List<Segnalazione>) request.getAttribute("listaSegnalazioni");
        if (lista != null && !lista.isEmpty()) {
    %>
        <table class="table table-bordered table-striped">
            <thead class="table-dark">
                <tr>
                    <th>Nome</th>
                    <th>Cognome</th>
                    <th>Numero Segnalazione</th>
                    <th>Testo</th>
                </tr>
            </thead>
            <tbody>
            <%
                for (Segnalazione s : lista) {
            %>
                <tr>
                    <td><%= s.getNome() %></td>
                    <td><%= s.getCognome() %></td>
                    <td><%= s.getNumeroSegnalazione() %></td>
                    <td><%= s.getTesto() %></td>
                </tr>
            <%
                }
            %>
            </tbody>
        </table>
    <%
        } else {
    %>
        <p class="alert alert-info">Nessuna segnalazione trovata.</p>
    <%
        }
    %>
</div>
</body>
</html>
