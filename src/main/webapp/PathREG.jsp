<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html lang="it">
<head>
    <title>RouteX - Visualizzazione Percorsi</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f9f9f9;
            margin: 0;
            padding: 0;
            display: flex;
            height: 100vh;
        }

        /* Barra laterale per i percorsi */
        .sidebar {
            width: 300px;
            background: #0078d7;
            color: white;
            padding: 20px;
            display: flex;
            flex-direction: column;
            justify-content: space-between;
            box-shadow: 2px 0 5px rgba(0, 0, 0, 0.1);
            overflow-y: auto; /* Aggiunge lo scrolling verticale */
            height: 100vh; /* Imposta altezza della barra laterale */
        }

        .sidebar h1 {
            margin: 0;
            font-size: 22px;
            text-align: center;
        }

        .route-list {
            list-style: none;
            padding: 0;
            margin: 20px 0;
        }

        .route-list li {
            display: flex;
            align-items: center;
            margin-bottom: 15px;
            padding: 10px;
            background: rgba(255, 255, 255, 0.2);
            border-radius: 8px;
            animation: fadeIn 0.5s ease;
            cursor: pointer;
            transition: background-color 0.3s ease;
        }

        .route-list li:hover {
            background: rgba(255, 255, 255, 0.4);
        }

        .route-list li span {
            margin-right: 10px;
            background: white;
            color: #0078d7;
            border-radius: 50%;
            width: 30px;
            height: 30px;
            display: flex;
            justify-content: center;
            align-items: center;
            font-weight: bold;
        }

        .no-data {
            text-align: center;
            color: rgba(255, 255, 255, 0.8);
        }

        /* Sezione principale */
        .main {
            flex-grow: 1;
            padding: 20px;
            position: relative;
        }

        /* Pulsante Home */
        .home-button {
            position: absolute;
            top: 20px;
            right: 20px;
            background: #0078d7;
            color: white;
            border: none;
            padding: 10px 20px;
            border-radius: 5px;
            font-size: 16px;
            cursor: pointer;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
            transition: background-color 0.3s ease, transform 0.2s ease;
        }
        .back-button {
            position: absolute;
            top: 20px;
            right: 120px; /* Posizionato vicino al pulsante "Home" */
            background: #0078d7; /* Colore blu */
            color: white;
            border: none;
            padding: 10px 20px;
            border-radius: 5px;
            font-size: 16px;
            cursor: pointer;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
            transition: background-color 0.3s ease, transform 0.2s ease;
        }

        .back-button:hover {
            background: #d32f2f;
            transform: translateY(-2px);
        }


        .home-button:hover {
            background: #005bb5;
            transform: translateY(-2px);
        }

        .submit {
                background: linear-gradient(90deg, #0078d7, #005bb5);
                color: white;
                font-size: 16px;
                font-weight: bold;
                border: none;
                border-radius: 25px;
                padding: 10px 20px;
                cursor: pointer;
                transition: all 0.3s ease;
                box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
                display: inline-block; /* Mantiene il pulsante sulla stessa linea */
                text-align: center; /* Centra il testo */
                margin: 20px auto; /* Spaziatura verticale */
            }

            .submit:hover {
                background: linear-gradient(90deg, #005bb5, #0078d7);
                transform: translateY(-3px);
                box-shadow: 0 6px 12px rgba(0, 0, 0, 0.3);
            }

            .submit:active {
                transform: translateY(1px);
                box-shadow: 0 3px 6px rgba(0, 0, 0, 0.2);
            }

            /* Contenitore per il layout */
            .button-container {
                display: flex;
                justify-content: center; /* Centra il pulsante orizzontalmente */
                align-items: center; /* Allinea verticalmente se necessario */
                margin-top: 20px; /* Spaziatura rispetto agli elementi sopra */
            }


        /* Animazioni */
        @keyframes fadeIn {
            from {
                opacity: 0;
                transform: translateY(10px);
            }
            to {
                opacity: 1;
                transform: translateY(0);
            }
        }
    </style>
</head>
<body>
    <!-- Barra laterale -->
    <div class="sidebar">
        <h1>Best route</h1>
        <ul class="route-list">
            <%
                // Ottieni i dati dall'attributo della richiesta
                List<String> dati = (List<String>) request.getAttribute("percorsi");
                List<String> linee = (List<String>) request.getAttribute("linee");



                if (dati != null && !dati.isEmpty())
                {
                    for(int i=0;i<dati.size();i++)
                    {
                        String elemento = dati.get(i);
                        String linea = linee.get(i);
            %>
                        <li>
                            <span><%=(i+1)%></span>
                            <%= elemento %>
                            <% out.print("   <h1 style='padding-left: 10px;'>      ("+linea+")       </h1>"); %>
                        </li>
            <%
                    }
                } else {
            %>
                <div class="no-data">
                    <p>Nessun dato disponibile per il percorso.</p>
                </div>
            <%
                }
            %>
        </ul>
    </div>

    <!-- Sezione principale -->
    <div class="main">
        <button class="home-button" onclick="location.href='indexLogged.jsp'">Home</button>
         <form action="PathControllerGrafico" method="get">
            <button class="back-button" type="submit">Back</button>
         </form>

        <h2>Welcome to RouteX!</h2>
        <p>
            Information about the route you have just chosen will be provided below.
            <%
            int numero_cambi = (int) request.getAttribute("numero_cambi");
            int numero_stazioni = (int) request.getAttribute("numero");
            String status = (String) request.getAttribute("status");
            Double minutaggio = (Double) request.getAttribute("minutaggio");
            String startstation = (String) request.getAttribute("inizio");
            String endstation = (String) request.getAttribute("fine");
            String city = (String) request.getAttribute("city");
            int stazionitotali = (int) request.getAttribute("stazionitotali");
            Double suolo = (Double) request.getAttribute("suolometropolitano");
            List<String> sequenza_cambi = (List<String>) request.getAttribute("listacambi");
            List<String> sequenza_cruciali = (List<String>) request.getAttribute("nodicruciali");

            //out.println("<li>" + numero_cambi + "</li>");
            //out.print("<h3> Type of traveler : <span style='color: red;'>" + status + "</span></h3>");
            //out.print("<h3> Number of metro line changes : " + numero_cambi + "</h3>");
            //out.print("<h3> The number of stations to be traversed : " + numero_stazioni + "</h3>");

           out.print("<style>");
           out.print("table { width: 100%; border-collapse: collapse; font-family: Arial, sans-serif; }");
           out.print("th, td { padding: 12px; border: 1px solid #ddd; text-align: left; }");
           out.print("th { background-color: #0078d7; color: white; font-weight: bold; }");
           out.print("tr:nth-child(even) { background-color: #f9f9f9; }");
           out.print("tr:hover { background-color: #f1f1f1; }");
           out.print("</style>");

           out.print("<table>");
           out.print("<tr>");
           out.print("<th>Information</th>");
           out.print("<th>Details</th>");
           out.print("</tr>");
           out.print("<tr>");
           out.print("<td>Start Station</td>");
           out.print("<td><span style='color: violet; font-weight: bold;'>" + startstation + "</span></td>");
           out.print("</tr>");
           out.print("<td>End Station</td>");
           out.print("<td><span style='color: violet; font-weight: bold;'>" + endstation + "</span></td>");
           out.print("</tr>");
           out.print("<td>Name of the metropolitan city</td>");
           out.print("<td><span style='color: black; font-weight: bold;'>" + city + "</span></td>");
           out.print("</tr>");
           out.print("<tr>");
           out.print("<td>Type of traveler</td>");
           out.print("<td><span style='color: red; font-weight: bold;'>" + status + "</span></td>");
           out.print("</tr>");
           out.print("<tr>");
           out.print("<td>Number of metro line changes</td>");
           out.print("<td>" + numero_cambi + "</td>");
           out.print("</tr>");

           out.print("<tr>");
           out.print("<td>List of all metro line changes </td>");
           out.print("<td>");





               for(int i=0;i<sequenza_cambi.size();i++)
               {
                    String linea_singola = sequenza_cambi.get(i);
                    out.print(" ");
                    //out.print("<img src='images/"+ linea_singola + ".png' alt='Logo' width='30' height='20' />");


                    out.print(" " + linea_singola + " ");

               }

           out.print("</td>");

           out.print("</tr>");

           out.print("<tr>");
           out.print("<td>Interchange station </td>");
           out.print("<td>");


               for(int i=0;i<sequenza_cruciali.size();i++)
               {
                    String stazione = sequenza_cruciali.get(i);
                    out.print("<span style='color: black; font-weight: bold;'>");
                    out.print("<ul>");
                    out.print("<li>");
                    out.print(" " + stazione + " ");
                    out.print("</li>");
                    out.print("</ul>");
                    out.print("</span>");
               }

           out.print("</td>");
           out.print("</tr>");



           out.print("<tr>");
           out.print("<td>The number of stations to be traversed</td>");
           out.print("<td>" + numero_stazioni + "</td>");
           out.print("</tr>");
           out.print("<tr>");
           out.print("<td>Average time (minutes) to reach the destination. (Average time of about two minutes per stop - Approximate statistic)</td>");
           out.print("<td>" + minutaggio + " min</td>");
           out.print("</tr>");
           out.print("<tr>");
           out.print("<td> Total metro stations in the city ");
           out.print("<td>" + stazionitotali + " </td>");
           out.print("</tr>");
           out.print("<tr>");
           out.print("<td> Percentage of metropolitan land used relative to the total area. ");
           out.print("<td>" + String.format("%.2f",suolo) + "% </td>");
           out.print("</tr>");

           out.print("</table>");

            String newcity = city.toLowerCase();

            %>


            <a href="images/metro-<%= newcity %>.jpg" class="submit" download="<%= city %>_Map.jpg">Download Metropolitan Map</a>




        </p>
    </div>
</body>
</html>