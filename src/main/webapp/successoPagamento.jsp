<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <title>RouteX - Pagamento completato</title>

    <!-- Bootstrap + FontAwesome -->
    <link rel="stylesheet" href="<%= request.getContextPath() %>/lib/bootstrap/dist/css/bootstrap.css">
    <%@ include file="imports.jsp" %>
    <style>
        body {
            background-color: #f8f9fa;
            font-family: "Segoe UI", sans-serif;
        }
        .container {
            margin-top: 70px;
            max-width: 700px;
        }
        .card {
            border-radius: 15px;
            box-shadow: 0 4px 15px rgba(0, 0, 0, 0.2);
        }
        .btn-home {
            background-color: #007bff;
            color: white;
            border: none;
            border-radius: 10px;
            padding: 10px 20px;
            font-size: 17px;
            transition: background-color 0.3s ease;
        }
        .btn-home:hover {
            background-color: #0056b3;
        }
        #qrcode {
            margin: 20px auto;
            width: fit-content;
        }
        .ticket-list {
            text-align: left;
            background-color: #f1f1f1;
            border-radius: 8px;
            padding: 10px;
            margin-top: 15px;
            font-family: monospace;
            font-size: 14px;
            white-space: pre-wrap;
            max-height: 200px;
            overflow-y: auto;
        }
    </style>
</head>

<body>
    <div class="container">
        <div class="card p-4 text-center">
            <h2 class="mb-3 text-success"><i class="fas fa-check-circle"></i> Pagamento completato con successo!</h2>
            <hr>

            <%
                String city = (String) request.getAttribute("city");
                String quantity = (String) request.getAttribute("quantity");
                Double totale = (Double) request.getAttribute("totale");
                String metodo = (String) request.getAttribute("metodo");
                String messaggio = (String) request.getAttribute("messaggio");
                List<String> codiciBiglietti = (List<String>) request.getAttribute("codiciBiglietti");

                // concateno tutti i codici in un'unica stringa per il QR
                StringBuilder qrContent = new StringBuilder();
                if (codiciBiglietti != null && !codiciBiglietti.isEmpty()) {
                    for (String codice : codiciBiglietti) {
                        qrContent.append(codice).append("\n");
                    }
                }
            %>

            <p style="font-size: 18px;">
                <strong>Città:</strong> <%= city %><br>
                <strong>Numero di biglietti:</strong> <%= quantity %><br>
                <strong>Totale pagato:</strong> € <%= String.format("%.2f", totale) %><br>
                <strong>Metodo di pagamento:</strong> <%= metodo %><br>
            </p>

            <div class="alert alert-info"><%= messaggio %></div>

            <hr>
            <h4>QR Code biglietti</h4>
            <div id="qrcode"></div>

            <p class="text-muted mt-3">Scannerizza il QR Code per visualizzare i codici univoci dei tuoi biglietti.</p>

            <!-- Mostra i codici a schermo -->
            <div class="ticket-list">
                <% if (codiciBiglietti != null) {
                    for (String codice : codiciBiglietti) { %>
                        <%= codice %><br>
                <% } } %>
            </div>

            <div class="mt-4">
                <a href="indexLogged.jsp" class="btn-home">
                    <i class="fas fa-home"></i> Torna alla Home
                </a>
            </div>
        </div>
    </div>

    <!-- Generazione QR dinamico -->
    <script>
        const qrData = `<%= qrContent.toString() %>`;
        new QRCode(document.getElementById("qrcode"), {
            text: qrData.trim(),
            width: 200,
            height: 200
        });
    </script>
</body>
</html>
