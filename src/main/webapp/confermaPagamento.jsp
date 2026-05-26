<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <title>RouteX - Conferma Pagamento</title>

    <!-- Bootstrap + FontAwesome -->
    <link rel="stylesheet" href="<%= request.getContextPath() %>/lib/bootstrap/dist/css/bootstrap.css">
    <!-- Font Awesome CSS -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css" integrity="sha512-1ycn6IcaQQ40/MKBW2W4Rhis/DbILU74C1vSrLJxCq57o941Ym01SwNsOMqvEBFlcgUa6xLiPY/NS5R+E6ztJQ==" crossorigin="anonymous" referrerpolicy="no-referrer">

    <style>
        body {
            background-color: #f8f9fa;
            font-family: "Segoe UI", sans-serif;
        }
        .container {
            margin-top: 60px;
            max-width: 700px;
        }
        .card {
            border-radius: 15px;
            box-shadow: 0 4px 15px rgba(0, 0, 0, 0.2);
        }
        .btn-confirm {
            background-color: #28a745;
            color: white;
            border: none;
            border-radius: 10px;
            padding: 10px 20px;
            font-size: 17px;
            transition: background-color 0.3s ease;
        }
        .btn-confirm:hover {
            background-color: #218838;
        }
        .btn-cancel {
            background-color: #6c757d;
            color: white;
            border: none;
            border-radius: 10px;
            padding: 10px 20px;
            font-size: 17px;
            transition: background-color 0.3s ease;
        }
        .btn-cancel:hover {
            background-color: #5a6268;
        }
        .payment-section {
            text-align: left;
            margin-top: 25px;
        }
        .payment-details {
            display: none;
            margin-top: 15px;
        }
        label {
            font-weight: 500;
        }
    </style>

    <script>
        function mostraCampiPagamento() {
            const metodo = document.querySelector('input[name="metodoPagamento"]:checked').value;
            document.getElementById('paypal-details').style.display = (metodo === 'paypal') ? 'block' : 'none';
            document.getElementById('mastercard-details').style.display = (metodo === 'mastercard') ? 'block' : 'none';
        }
    </script>
</head>

<body>
<%@ include file="header.jspf" %>
    <div class="container">
        <div class="card p-4 text-center">
            <h2 class="mb-3">Riepilogo Acquisto</h2>
            <hr>

            <%
                String city = (String) request.getAttribute("city");
                String quantity = (String) request.getAttribute("quantity");
                Double prezzo = (Double) request.getAttribute("prezzo");
            %>

            <p style="font-size: 18px;">
                <strong>Città selezionata:</strong> <%= city %><br>
                <strong>Numero di biglietti:</strong> <%= quantity %><br>
                <strong>Totale da pagare:</strong> € <%= String.format("%.2f", prezzo) %>
            </p>

            <hr>

            <form action="confermaPagamento" method="post" class="text-start">

                <!-- Hidden data -->
                <input type="hidden" name="city" value="<%= city %>">
                <input type="hidden" name="quantity" value="<%= quantity %>">
                <input type="hidden" name="totale" value="<%= prezzo %>">

                <!-- Sezione scelta metodo -->
                <div class="payment-section">
                    <label><strong>Seleziona il metodo di pagamento:</strong></label><br>
                    <div class="form-check form-check-inline mt-2">
                        <input class="form-check-input" type="radio" name="metodoPagamento" id="mastercard" value="mastercard" onclick="mostraCampiPagamento()">
                        <label class="form-check-label" for="mastercard">
                            <i class="fab fa-cc-mastercard text-danger"></i> Mastercard
                        </label>
                    </div>
                    <div class="form-check form-check-inline">
                        <input class="form-check-input" type="radio" name="metodoPagamento" id="paypal" value="paypal" onclick="mostraCampiPagamento()">
                        <label class="form-check-label" for="paypal">
                            <i class="fab fa-paypal text-primary"></i> PayPal
                        </label>
                    </div>
                </div>

                <!-- Mastercard details -->
                <div id="mastercard-details" class="payment-details">
                    <div class="mb-3">
                        <label for="numeroCarta" class="form-label">Numero carta</label>
                        <input type="text" class="form-control" id="numeroCarta" name="numeroCarta">
                    </div>
                    <div class="mb-3">
                        <label for="scadenza" class="form-label">Data di scadenza</label>
                        <input type="text" class="form-control" id="scadenza" name="scadenza">
                    </div>
                    <div class="mb-3">
                        <label for="cvv" class="form-label">CVV</label>
                        <input type="password" class="form-control" id="cvv" name="cvv">
                    </div>
                </div>

                <!-- PayPal details -->
                <div id="paypal-details" class="payment-details">
                    <div class="mb-3">
                        <label for="emailPaypal" class="form-label">Email PayPal</label>
                        <input type="text" cla ss="form-control" id="emailPaypal" name="emailPaypal">
                    </div>
                    <div class="mb-3">
                        <label for="codiceTransazione" class="form-label">Codice Transazione</label>
                        <input type="text" class="form-control" id="codiceTransazione" name="codiceTransazione">
                    </div>
                </div>

                <!-- Sezione scelta persistenza -->
                <div class="payment-section mt-4">
                    <label><strong>Modello di persistenza:</strong></label><br>

                    <div class="form-check form-check-inline mt-2">
                        <input class="form-check-input" type="radio" name="persistence"
                               id="persistenceJDBC" value="JDBC" checked>
                        <label class="form-check-label" for="persistenceJDBC">
                            <i class="fas fa-database text-primary"></i> Database (JDBC)
                        </label>
                    </div>

                    <div class="form-check form-check-inline">
                        <input class="form-check-input" type="radio" name="persistence"
                               id="persistenceFile" value="FileSystem">
                        <label class="form-check-label" for="persistenceFile">
                            <i class="fas fa-file-csv text-success"></i> File System (CSV)
                        </label>
                    </div>
                </div>


                <div class="text-center mt-4">
                    <button type="submit" class="btn-confirm">
                        <i class="fas fa-credit-card"></i> Conferma Pagamento
                    </button>
                </div>
            </form>

            <div class="mt-3">
                <a href="buyTicket" class="btn-cancel">
                    <i class="fas fa-arrow-left"></i> Annulla
                </a>
            </div>
        </div>
    </div>


</body>
</html>
