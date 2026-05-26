<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html lang="it">
<head>
    <title>RouteX - Visualizzazione Percorsi</title>
    <link rel="stylesheet" href="css/path.css">
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
        <button class="home-button" onclick="location.href='index.jsp'">Home</button>
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