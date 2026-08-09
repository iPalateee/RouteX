package it.web.routex.bean;


import it.web.routex.exception.InvalidBuyTicketInputExceptionBrondi;
import it.web.routex.model.City;

import static it.web.routex.utility.text.TextUtils.*;

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

        this.city = validateCitySyntax(rawCity);

    }

}

