package it.web.routex.utility.decorator.decoratorpath;

import it.web.routex.bean.RouteBean;
import it.web.routex.exception.InvalidRouteInputExceptionRemoli;
import it.web.routex.model.Route;

public interface Component {
    RouteBean update(RouteBean rb, Route r) throws InvalidRouteInputExceptionRemoli;
}

