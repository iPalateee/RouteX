package it.web.routex.model;


import it.web.routex.interfaces.Payment;
import it.web.routex.enumerator.PaymentMethod;
import it.web.routex.exception.PaymentValidationExceptionRemoli;

/**
 * Model di dominio per i pagamenti tramite Mastercard.
 * * Incapsula i dati della carta di credito e gestisce la logica
 * di validazione interna dei campi inseriti dall'utente.
 */
public class Mastercard implements Payment
{
    String numeroCarta;
    String dataScadenza;
    String cvv;

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public void setDataScadenza(String dataScadenza) {
        this.dataScadenza = dataScadenza;
    }

    public void setNumeroCarta(String numeroCarta) {
        this.numeroCarta = numeroCarta;
    }



    @Override
    public PaymentMethod getMethod() {
        return PaymentMethod.MASTERCARD;
    }

    @Override
    public boolean isValid() {
        return numeroCarta != null && numeroCarta.matches("\\d{16}")
                && cvv != null && cvv.matches("\\d{3}")
                && dataScadenza != null && dataScadenza.matches("\\d{4}-\\d{2}-\\d{2}");
    }

    @Override
    public void validate() throws PaymentValidationExceptionRemoli {
        if (!isValid()) {
            throw new PaymentValidationExceptionRemoli(
                    "Dati Mastercard non validi",
                    PaymentMethod.MASTERCARD,
                    "Validazione dominio Mastercard"
            );
        }
    }

    public String maskedNumber() {
        return "**** **** **** " + numeroCarta.substring(12);
    }

    public String getNumeroCarta() {
        return numeroCarta;
    }

    public String getCvv() {
        return cvv;
    }

    public String getDataScadenza() {
        return dataScadenza;
    }
}
