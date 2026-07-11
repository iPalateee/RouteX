<!DOCTYPE html>
<html lang="it">
<head>
<%@ include file="imports.jsp" %>
<title>RouteX - Comunicazione</title>
<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/index.css">
<style>

    .form-container {
        text-align: center;
        background: rgba(0, 0, 0, 0.7);
        padding: 30px;
        border-radius: 15px;
        box-shadow: 0 4px 20px rgba(0, 0, 0, 0.3);
        width: 80%;
        max-width: 600px;
    }

    .form-container h1 {
        font-size: 26px;
        color: white;
        margin-bottom: 20px;
    }

    textarea {
        width: 100%;
        height: 150px;
        border-radius: 10px;
        border: none;
        padding: 10px;
        font-size: 16px;
        resize: none;
    }

    .send-btn {
        background-color: #007bff;
        color: white;
        border: none;
        padding: 10px 25px;
        border-radius: 15px;
        font-size: 18px;
        cursor: pointer;
        transition: background-color 0.3s ease;
        margin-top: 15px;
    }

    .send-btn:hover:enabled {
        background-color: #0056b3;
    }

    .send-btn:disabled {
        background-color: gray;
        cursor: not-allowed;
    }

    .char-counter {
        font-size: 14px;
        color: #ccc;
        text-align: right;
        margin-top: 5px;
    }

    .button-container {
        position: absolute;
        top: 20px;
        right: 40px;
        display: flex;
        gap: 10px;
    }

    .button-container a {
        background-color: #007bff;
        color: white;
        padding: 10px 15px;
        font-size: 16px;
        border: none;
        cursor: pointer;
        border-radius: 10px;
        text-decoration: none;
        text-align: center;
        transition: background-color 0.3s ease;
    }

    .button-container a:hover {
        background-color: #0056b3;
    }

    .metro-logos {
        position: absolute;
        right: 20px;
        bottom: 20px;
        display: flex;
        flex-direction: column;
        align-items: center;
        gap: 15px;
    }

    .metro-logos i {
        font-size: 50px;
        color: white;
        transition: transform 0.3s ease;
    }

    .metro-logos i:hover {
        transform: scale(1.2);
    }

    /* MODAL PERSONALIZZATO */
    .modal {
        display: none;
        position: fixed;
        z-index: 1000;
        left: 0; top: 0;
        width: 100%; height: 100%;
        background-color: rgba(0,0,0,0.6);
        backdrop-filter: blur(4px);
        justify-content: center;
        align-items: center;
    }

    .modal-content {
        background: rgba(255,255,255,0.95);
        color: #000;
        padding: 30px;
        border-radius: 20px;
        text-align: center;
        width: 400px;
        box-shadow: 0 4px 20px rgba(0,0,0,0.4);
        animation: fadeIn 0.3s ease;
    }

    @keyframes fadeIn {
        from { transform: scale(0.8); opacity: 0; }
        to { transform: scale(1); opacity: 1; }
    }

    .modal-content h2 {
        margin-top: 0;
        font-size: 22px;
        color: #007bff;
    }

    .modal-content p {
        font-size: 16px;
        margin: 20px 0;
        word-wrap: break-word;
    }

    .modal-buttons {
        display: flex;
        justify-content: space-around;
        margin-top: 20px;
    }

    .modal-btn {
        padding: 10px 20px;
        border-radius: 12px;
        font-size: 16px;
        border: none;
        cursor: pointer;
        transition: 0.3s;
    }

    .modal-btn.confirm {
        background-color: #007bff;
        color: white;
    }

    .modal-btn.confirm:hover {
        background-color: #0056b3;
    }

    .modal-btn.cancel {
        background-color: gray;
        color: white;
    }

    .modal-btn.cancel:hover {
        background-color: #333;
    }

</style>
</head>
<body>
<div class="background-blur"></div>

<div class="main-container">
    <!-- Colonna sinistra -->
    <div class="left-box">
        <img src="images/logo-no-background.png" alt="Logo">
        <h1>RouteX</h1>
        <p>Send global updates<br>to all users in one click.</p>
    </div>

    <!-- Contenuto principale -->
    <div class="right-content">
        <!-- Pulsanti -->
        <div class="button-container">
            <a href="dashboardWorker.jsp">Home</a>
            <a href="logout" class="logout-link">Logout</a>
        </div>

        <!-- Form invio comunicazione -->
        <div class="form-container">
            <h1>Send Global Communication</h1>

            <form id="sendForm" action="confirmCommunicationWorker" method="post">
                <label for="message">Your Message:</label>
                <textarea id="message" name="message" maxlength="250" placeholder="Write your message..." required></textarea>
                <div class="char-counter" id="counter">0 / 250</div>
                <button class="send-btn" type="button" id="sendBtn" disabled>Send message</button>
            </form>

            <form action="dashboardWorker.jsp" method="get">
                <button class="send-btn" type="submit">Back</button>
            </form>
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

<!-- MODALE PERSONALIZZATO -->
<div id="customAlert" class="modal">
    <div class="modal-content">
        <h2>Confirm Communication</h2>
        <p id="alertMessage"></p>
        <div class="modal-buttons">
            <button id="cancelBtn" class="modal-btn cancel">Back</button>
            <button id="confirmBtn" class="modal-btn confirm">Send Communication</button>
        </div>
    </div>
</div>

<script>
    const textarea = document.getElementById('message');
    const counter = document.getElementById('counter');
    const sendBtn = document.getElementById('sendBtn');
    const sendForm = document.getElementById('sendForm');

    // Elementi modale
    const modal = document.getElementById('customAlert');
    const alertMessage = document.getElementById('alertMessage');
    const cancelBtn = document.getElementById('cancelBtn');
    const confirmBtn = document.getElementById('confirmBtn');

    // Aggiorna contatore caratteri
    textarea.addEventListener('input', () => {
        const length = textarea.value.length;
        counter.textContent = length + " / 250";
        sendBtn.disabled = length === 0 || length > 250;
        sendBtn.title = length > 250 ? "Numero massimo di caratteri superato" : "";
    });

    // Mostra modale
    sendBtn.addEventListener('click', () => {
        const messaggio = textarea.value.trim();
        if (messaggio.length === 0) return;

        alertMessage.textContent = messaggio;
        modal.style.display = "flex";
    });

    // Chiudi modale senza inviare
    cancelBtn.addEventListener('click', () => {
        modal.style.display = "none";
    });

    // Conferma invio
    confirmBtn.addEventListener('click', () => {
        sendForm.submit();
    });
</script>

</body>
</html>