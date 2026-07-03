package it.web.routex.extractor;

import javax.servlet.http.HttpServletRequest;
import it.web.routex.exception.InvalidRouteInputExceptionRemoli;
import it.web.routex.record.RouteRecord;

import static it.web.routex.utility.text.TextUtils.sanitize;

public final class RouteInputExtractor {

    private RouteInputExtractor()
    {
        throw new AssertionError("Classe di estrazione dati, non si creano new");
    }

    public static RouteRecord from(HttpServletRequest request)
            throws InvalidRouteInputExceptionRemoli {

        String city = sanitize(request.getParameter("city"));
        String start = sanitize(request.getParameter("startStation"));
        String end = sanitize(request.getParameter("endStation"));

        if (city == null || city.isEmpty())
            throw new InvalidRouteInputExceptionRemoli("city", "City parameter is missing");

        if (start == null || start.isEmpty())
            throw new InvalidRouteInputExceptionRemoli("startStation", "Start station is missing");

        if (end == null || end.isEmpty())
            throw new InvalidRouteInputExceptionRemoli("endStation", "End station is missing");

        return new RouteRecord(city, start, end);
    }

    /*static String sanitize(String s) {
        return (s == null) ? null : s.trim();
    }*/
}
