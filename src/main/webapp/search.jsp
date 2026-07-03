<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html lang="it">
<head>
    <title>RouteX - Metro Finder</title>
    <style>
        /* Stile di base per la pagina */
        .searchBox {
                    width: 90%;
                    padding: 10px;
                    font-size: 18px;
                    border-radius: 10px;
                    border: 1px solid #007bff;
                    margin-bottom: 10px;
                }

                /* Contenitore per i risultati */
                .searchResults {
                    border: 1px solid #ccc;
                    max-height: 200px;
                    overflow-y: auto;
                    background: white;
                    width: 90%;
                    position: absolute;
                    z-index: 1000;
                    display: none;
                }

                /* Stile per ogni risultato */
                .resultItem {
                    padding: 10px;
                    cursor: pointer;
                    border-bottom: 1px solid #ddd;
                }

                .resultItem:hover {
                    background-color: #f0f0f0;
                }
        body {
            background: linear-gradient(to bottom, #e0f7fa, #80deea);
            font-family: 'Arial Rounded MT Bold', sans-serif;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
            position: relative;
        }

        /* Container per il form e la mappa */
        .main-container {
            display: flex;
            background: rgba(255, 255, 255, 0.9);
            padding: 40px; /* Aumentato il padding */
            border-radius: 20px; /* Aumentato il border-radius */
            box-shadow: 0 0 20px rgba(0, 0, 0, 0.3); /* Rafforzato lo shadow */
            animation: slideIn 1s ease-out;
            max-width: 1200px; /* Aumentato da 1000px a 1200px */
            width: 95%; /* Aumentato il width */
            gap: 40px; /* Aumentato lo spazio tra i contenitori */
        }

        /* Container per il form */
        .form-container {
            flex: 1;
            text-align: center;
        }

        /* Container per la mappa */
        .map-container {
            flex: 1;
            display: flex;
            justify-content: center;
            align-items: center;
            border: 2px solid #007bff;
            border-radius: 20px; /* Aumentato il border-radius */
            box-shadow: 0 8px 10px rgba(0, 0, 0, 0.2); /* Rafforzato lo shadow */
            background: #f0f8ff;
        }

        .map-container img {
            max-width: 100%;
            max-height: 100%;
            border-radius: 10px;
        }

        /* Stile per le dropdown */
        select {
            width: 90%; /* Aumentata la larghezza */
            padding: 16px; /* Aumentato il padding */
            margin: 10px 0;
            border: 1px solid #007bff;
            border-radius: 10px;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
            font-size: 18px; /* Aumentato il font-size */
            transition: all 0.3s ease;
        }

        /* Stile per il bottone */
        button {
            background-color: #007bff;
            color: white;
            border: none;
            padding: 16px 30px; /* Aumentato il padding */
            border-radius: 15px;
            font-size: 20px; /* Aumentato il font-size */
            cursor: pointer;
            transition: background-color 0.3s ease, transform 0.2s ease;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
        }

        /* Effetto hover per il bottone */
        button:hover {
            background-color: #0056b3;
            transform: translateY(-2px);
        }

        /* Effetto focus per i select */
        select:focus {
            outline: none;
            border-color: #0056b3;
            box-shadow: 0 0 8px rgba(0, 91, 187, 0.5);
        }

        /* Animazione di slide in */
        @keyframes slideIn {
            from {
                opacity: 0;
                transform: translateY(-20px);
            }
            to {
                opacity: 1;
                transform: translateY(0);
            }
        }

        /* Stile per il testo del risultato */
        p {
            font-size: 20px; /* Aumentato il font-size */
            color: #333;
            margin-top: 20px;
        }

        /* Stile per i pulsanti registrati, login e home */
        .button-container-right {
            position: absolute;
            top: 20px;
            right: 40px; /* Spazio dal margine destro */
            display: flex;
            gap: 10px; /* Spazio tra i due pulsanti */
        }

        .button-container-right a, .button-container-left a {
            background-color: #007bff;
            color: white;
            padding: 10px 15px;
            font-size: 16px;
            border: none;
            cursor: pointer;
            border-radius: 10px;
            text-decoration: none;
            text-align: center;
        }

        .button-container-right a:hover, .button-container-left a:hover {
            background-color: #0056b3;
        }

        /* Contenitore per il pulsante Home */
        .button-container-left {
            position: absolute;
            top: 20px;
            left: 40px; /* Spazio dal margine sinistro */
        }

        /* Tooltip container per il bottone disabilitato */
        #submitBtn[disabled] {
          position: relative;
          cursor: not-allowed;
        }

        /* Tooltip personalizzato (inizialmente nascosto) */
        #submitBtn[disabled]::after {
          content: attr(data-tooltip);
          position: absolute;
          bottom: 130%; /* sopra il bottone */
          left: 50%;
          transform: translateX(-50%);
          background-color: #333;
          color: #fff;
          padding: 6px 10px;
          border-radius: 6px;
          white-space: nowrap;
          font-size: 14px;
          opacity: 0;
          pointer-events: none;
          transition: opacity 0s;
          visibility: hidden;
          z-index: 1000;
        }

        /* Tooltip visibile subito al passaggio mouse */
        #submitBtn[disabled]:hover::after {
          opacity: 1;
          visibility: visible;
          transition-delay: 0s; /* nessun delay */
        }
    </style>
<body>



    <%
        session = request.getSession(false);
        String nome = null;
        if (session != null) {
            nome = (String) session.getAttribute("nome");
        }

        if (nome == null) {
    %>
        <div class="button-container-right">
            <a href="index.jsp">Home</a>
            <a href="login.jsp">Login</a>
        </div>
    <%
        } else {
    %>
        <div class="button-container-right">
            <a href="indexLogged.jsp">Home</a>
            <a href="logout" class="logout-link">Logout</a>
            <a href="areaRiservata" method="get">Area riservata</a>
        </div>
    <%
        }
    %>

    <div class="main-container">
        <div class="form-container">
            <h2>RouteX - Find Your Metro Route</h2>
            <form action="PathControllerGrafico" method="post" name="select">

                <label for="citySelect">Seleziona Città:</label>
                <select id="citySelect" name="city" class="form-select" onchange="updateStationsAndMap()">
                <option value="" disabled selected>-- Choose a city --</option>
                <%
                    List<it.web.routex.bean.CityBean> cities = (List<it.web.routex.bean.CityBean>) request.getAttribute("cities2");
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


                <br><br><br>

                <label for="startSearchBox">Start Station:</label>
                <input type="text" name="startStation" id="startSearchBox" class="searchBox" placeholder="Search start station...">

                <label for="endSearchBox">End Station:</label>
                <input type="text" name="endStation" id="endSearchBox" class="searchBox" placeholder="Search end station...">

                <button type="submit" id="submitBtn">
                  Find Route
                </button>

                <br><br><br>

                <img src="images/logo-no-background.png" height="90" width="300" alt="Logo">

            </form>

        </div>

        <div class="map-container">
            <img id="mapImage" src="images/subway_default.png" alt="Metro Map">
        </div>
    </div>

    <script>
        // Dati delle stazioni per ogni città
        const cityStations = {
            "Rome": [
                "Battistini", "Cornelia", "Baldo degli Ubaldi", "Valle Aurelia", "Cipro",
                "Ottaviano", "Lepanto", "Flaminio", "Spagna", "Barberini", "Repubblica",
                "Termini", "Vittorio Emanuele", "Manzoni", "San Giovanni", "Re di Roma",
                "Ponte Lungo", "Furio Camillo", "Colli Albani", "Arco di Travertino",
                "Porta Furba", "Numidio Quadrato", "Lucio Sestio", "Giulio Agricola",
                "Subaugusta", "Cinecitta", "Anagnina", "Pantano", "Graniti",
                "Finocchio", "Bolognetta", "Borghesiana", "Due Leoni - Fontana Candida", "Grotte Celoni",
                "Torre Gaia", "Torre Angela", "Torrenova", "Giardinetti", "Torre Maura",
                "Torre Spaccata", "Alessandrino", "Parco di Centocelle", "Mirti",
                "Gardenie", "Teano", "Malatesta", "Pigneto", "Lodi", "San Giovanni", "Laurentina",
                "EUR Fermi", "EUR Palasport", "EUR Magliana", "Marconi", "Basilica S. Paolo",
                "Garbatella", "Piramide", "Circo Massimo", "Colosseo","Cavour", "Termini",
                "Castro Pretorio", "Policlinico", "Bologna", "Tiburtina FS", "Quintiliani",
                "Monti Tiburtini", "Pietralata", "Santa Maria del Soccorso", "Ponte Mammolo",
                "Rebibbia", "Annibaliano", "Libia", "Conca D oro", "Jonio"
            ],
            "Naples": ["Pozzuoli Solfatara", "Bagnoli-Agnano Terme", "Campi Flegrei", "Mostra", "P.Leopardi", "Augusto", "Lala",
                       "Mergellina", "Arco Mirelli", "San Pasquale", "Chiaia", "P.Amedeo", "Montesanto", "Museo-Piazza Cavour",
                       "Dante", "Toledo", "Municipio", "Universita", "Duomo", "Garibaldi", "Gianturco", "S.Giovanni-Barra",
                       "Materdei", "Salvator Rosa", "Quattro Giornate", "Vanvitelli", "Medaglie D'Oro", "Montedonzelli",
                       "Rione Alto", "Policlinico", "Colli Aminei", "Frullone", "Chiaiano", "Piscinola", "Mugnano", "Giugliano",
                       "Aversa Ippodromo", "Aversa Centro", "Cavalleggeri Aosta"],
            "Athens": ["Airport", "Koropi", "Paiania-Kantza", "Pallini", "Doukissis Plakentias", "Halandri",
                        "Aghia Paraskevi", "Nomismatokopio", "Holargos", "Ethniki Amyna", "Katehaki", "Panormou",
                        "Ampelokipi", "Megaro Moussikis", "Evangelismos", "Syntagma", "Panipistimo","Omonia",
                        "Victoria", "Attiki", "Aghios Nikolaos", "Kato Patissia", "Aghios Eleftherios", "Ano Patissia",
                        "Perissos", "Pefkakia", "Nea Ionia", "Iraklio", "Irini", "Neratziotissa", "Maroussi", "KAT",
                        "Kifissia", "Akropoli", "Syngrou Fix", "Aghios Ioannis", "Dafni", "Aghios Dimitrios", "Illioupoli",
                        "Alimos", "Argyroupoli", "Elliniko", "Monastiraki", "Thissio", "Petralona", "Tavros", "Kallithea",
                        "Moschato", "Faliro", "Piraeus", "Dimotiko Theatro", "Maniatika", "Nikaia", "Korydallos",
                        "Aghia Varvara", "Aghia Marina", "Egaleo", "Eleonas", "Kerameikos", "Metaxourghio",
                        "Larissa Station", "Sepolia", "Aghios Antonios", "Peristeri", "Anthoupoli", "Neos Kosmos"],

            "Budapest": ["Ors vezer tere", "Pillango utca", "Puskas Ferenc Stadion", "Keleti Palyaudvar", "Blaha Lujza Ter",
                        "Il. Janos Pal Papa Ter", "Rakoczi Ter", "Kalvin Ter", "Fovam Ter", "Szent Gellert Ter - Muegyetem",
                        "Moricz Zsigmond Korter", "Ujbuda-kozport", "Bikas Park", "Kelenfood Vasutallomas", "Kobanya-Kispes",
                        "Hatar Ut", "Pottyos Utca", "Ecseri Ut", "Nepliget", "Nagyvarad Ter", "Semmelweis Klinikak",
                        "Corvin-negyed", "Ferenciek Tere", "Deak Ferenc Ter", "Vorosmarty Ter", "Bajcsy-Zsilinszky ut", "Opera",
                        "Oktogon", "Vorosmarty Utca", "Kodaly Korond", "Bajza Utca", "Hosok Tere", "Szechenyi-furdo",
                        "Mexikoi Ut", "Ujpest-Kozpont", "Ujpest-Varoskapu", "Gyongyosi Utca", "Forgach Utca",
                        "Goncz Arpad Vkp", "Dozsa Gyorgy Ut", "Lehel Ter", "Nyugati Palyaudva", "Arany Janos Utca",
                        "Kossuth Lajos Ter", "Battyhany Ter", "Szell Kalman Ter", "Deli Palyaudvar", "Astoria"]
        };


        function updateStationsAndMap() {
            const citySelect = document.getElementById('citySelect');
            const startSearchBox = document.getElementById('startSearchBox');
            const endSearchBox = document.getElementById('endSearchBox');
            const mapImage = document.getElementById('mapImage');
            const selectedCity = citySelect.value;

            // Se non è selezionata una città, non cambiare l'immagine
            if (!selectedCity) {
                return;
            }

            // Aggiorna l'immagine della mappa in base alla città selezionata
            mapImage.src = 'images/metro-' + selectedCity.toLowerCase() + '.jpg';

            // Aggiorna i placeholder delle caselle di testo
            startSearchBox.placeholder = `Search stations in ${selectedCity}...`;
            endSearchBox.placeholder = `Search stations in ${selectedCity}...`;

        }

    </script>

</body>
</html>