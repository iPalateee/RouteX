package it.web.routex.boundary.cli.view;
@SuppressWarnings("java:S106")
public final class ComunicazioneInviataCLI
{
    private ComunicazioneInviataCLI() {

    }
    public static void invioComunicazioneConfirm(String messaggio) {
        System.out.println("        Comunicazione Inviata !          ");
        System.out.println("Ok. " + messaggio);
    }
}
