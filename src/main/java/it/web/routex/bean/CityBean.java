package it.web.routex.bean;


import it.web.routex.exception.InvalidBuyTicketInputExceptionBrondi;
import it.web.routex.model.City;

import static it.web.routex.utility.text.TextUtils.sanitize;

public class CityBean {

    private String city;

    public CityBean() {}

    public CityBean(City city) {
        this.city = city.getCity();
    }

    public String getCity() {
        return city;
    }

    public void setCity(String rawCity) throws InvalidBuyTicketInputExceptionBrondi {

        if (rawCity == null)
            throw new InvalidBuyTicketInputExceptionBrondi(
                    "Il campo città non è stato inviato dal form.",
                    "Parametro 'Città' null.",
                    InvalidBuyTicketInputExceptionBrondi.Severity.LOW
            );

        String city = sanitize(rawCity);

        if (city.isBlank())
            throw new InvalidBuyTicketInputExceptionBrondi(
                    "Città mancante.",
                    "Città è blank dopo sanitizzazione.",
                    InvalidBuyTicketInputExceptionBrondi.Severity.MEDIUM
            );

        if (!city.matches("^[\\p{L}\\s\\-']{2,50}$")) {
            throw new InvalidBuyTicketInputExceptionBrondi(
                    "Inserisci una città valida.",
                    "Regex città non rispettata: " + city,
                    InvalidBuyTicketInputExceptionBrondi.Severity.MEDIUM
            );
        }

        this.city = city;
    }

    @Override
    public String toString() {
        return city;
    }
}

