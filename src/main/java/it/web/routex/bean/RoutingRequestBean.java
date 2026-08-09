package it.web.routex.bean;

import it.web.routex.exception.InvalidRouteInputExceptionRemoli;
import it.web.routex.exception.InvalidBuyTicketInputExceptionBrondi;

import static it.web.routex.utility.text.TextUtils.validateCitySyntax;

public class RoutingRequestBean {

    private String city;
    private int startId;
    private int endId;

    public int getEndId() { return endId; }
    public String getCity() { return city; }
    public int getStartId() { return startId; }

    public void setCity(String city) throws InvalidBuyTicketInputExceptionBrondi {
        this.city = validateCitySyntax(city);
    }

    public void setStartId(int startId) throws InvalidRouteInputExceptionRemoli {
        if (startId < 0) {
            throw new InvalidRouteInputExceptionRemoli(
                    "startId",
                    "Errore: L'ID della stazione di partenza non può essere negativo."
            );
        }
        this.startId = startId;
    }

    public void setEndId(int endId) throws InvalidRouteInputExceptionRemoli {
        if (endId < 0) {
            throw new InvalidRouteInputExceptionRemoli(
                    "endId",
                    "Errore: L'ID della stazione di arrivo non può essere negativo."
            );
        }
        this.endId = endId;
    }
}