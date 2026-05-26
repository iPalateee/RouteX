<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="it.web.routex.bean.TicketBean" %>
<%@ page import="it.web.routex.bean.RouteBean" %>



<!DOCTYPE html>
<html lang="it">
<head>
<%@ include file="imports.jsp" %>
    <meta charset="UTF-8">
    <title>RouteX - Area Riservata</title>

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="<%= request.getContextPath() %>/lib/bootstrap/dist/css/bootstrap.css">

    <%@ include file="imports.jsp" %>


    <link rel="stylesheet" href="css/risBuy.css">
    <style>


        .table-responsive {
            background-color: #ffffff;
            padding: 20px;
            border-radius: 10px;
            box-shadow: 0 4px 12px rgba(0,0,0,0.1);
        }

        table.dataTable thead {
            background-color: #e9ecef;
            color: #212529;
        }

        table.dataTable tbody tr:nth-child(even) {
            background-color: #f1f1f1;
        }

        .dataTables_wrapper .dataTables_paginate {
            display: flex !important;
            justify-content: center;
            margin-top: 15px;
        }

        .dataTables_wrapper .dataTables_paginate a,
        .dataTables_wrapper .dataTables_paginate span {
            color: #212529 !important;
            background-color: #e2e6ea !important;
            border: 1px solid #ced4da !important;
            padding: 6px 12px;
            margin: 0 3px;
            border-radius: 5px;
            text-decoration: none;
            font-weight: bold;
        }

        .dataTables_wrapper .dataTables_paginate .current {
            background-color: #007bff !important;
            color: white !important;
        }

        .dataTables_wrapper .dataTables_paginate a:hover {
            background-color: #0056b3 !important;
            color: white !important;
        }

        .dataTables_wrapper .dataTables_filter input {
            background-color: #ffffff;
            border: 1px solid #ced4da;
            color: #212529;
            padding: 5px;
            border-radius: 4px;
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
    <div class="main-container">
        <div class="right-content">
            <div class="button-container">
                <%
                    String nome = null;
                    if (session != null) {
                        nome = (String) session.getAttribute("nome");
                    }
                    if (nome == null) {
                %>
                    <a href="register.jsp">Register</a>
                    <a href="login.jsp">Login</a>
                <%
                    } else {
                %>
                    <a href="indexLogged.jsp">Home</a>
                    <a href="logout">Logout</a>
                <%
                    }
                %>
            </div>


            <div class="table-responsive">
                <table id="GridIscritti" class="table table-striped">
                    <thead>
                        <tr>
                            <th>PARTENZA</th>
                            <th>ARRIVO</th>
                            <th>CITTÀ</th>
                            <th>NUMERO CAMBI</th>
                            <th>LISTA CAMBI</th>
                            <th>STAZIONI DI INTERSCAMBIO</th>
                            <th>NUMERO STAZIONI ATTRAVERSATE</th>
                            <th>TEMPO DI ARRIVO MEDIO</th>
                            <th>NUMERO STAZIONI CITTÀ</th>
                            <th>TERRENO UTILIZZATO (%)</th>
                        </tr>
                    </thead>
                    <tbody>
                        <%
                            List<RouteBean> listaPercorsi = (List<RouteBean>) request.getAttribute("listaPercorsi");

                                for (RouteBean r : listaPercorsi)
                                {
                        %>
                            <tr>
                                <td><%= r.getPartenza() %></td>
                                <td><%= r.getArrivo() %></td>
                                <td><%= r.getCitta() %></td>
                                <td><%= r.getnCambi() %></td>
                                <td><%= r.getListaCambi() %></td>
                                <td><%= r.getStazInterscambio() %></td>
                                <td><%= r.getnStazAttraversate() %></td>
                                <td><%= r.getTempoDiArrivo() %></td>
                                <td><%= r.getnStazioniCitta() %></td>
                                <td><%= r.getPercTerrenoUtilizzato() %></td>
                            </tr>
                        <%
                                }

                        %>
                    </tbody>
                </table>


               <%
                   List<TicketBean> tickets = (List<TicketBean>) request.getAttribute("tickets");
                   if (tickets != null && !tickets.isEmpty()) {
               %>

               <h3>Biglietti acquistati</h3>

               <table class="table table-striped">
                   <thead>
                       <tr>
                           <th>Codice Biglietto</th>
                           <th>Città</th>
                           <th>Data Acquisto</th>
                       </tr>
                   </thead>
                   <tbody>
                   <% for (TicketBean t : tickets) { %>
                       <tr>
                           <td><%= t.getCodice() %></td>
                           <td><%= t.getCitta() %></td>
                           <td><%= t.getDataAcquisto() %></td>
                       </tr>
                   <% } %>
                   </tbody>
               </table>

               <% } %>



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

    <!-- Inizializzazione DataTables -->
    <script>
        $(document).ready(function () {
            $('#GridIscritti').DataTable({
                pageLength: 10,
                lengthChange: false,
                language: {
                    paginate: {
                        previous: "Precedente",
                        next: "Successiva"
                    },
                    info: "Pagina _PAGE_ di _PAGES_",
                    search: "Cerca:"
                }
            });
        });


    </script>
</body>
</html>
