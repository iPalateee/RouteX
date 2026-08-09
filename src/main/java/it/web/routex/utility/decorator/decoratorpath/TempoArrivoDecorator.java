package it.web.routex.utility.decorator.decoratorpath;

import it.web.routex.bean.RouteBean;
import it.web.routex.exception.InvalidRouteInputExceptionRemoli;
import it.web.routex.model.Route;

public class TempoArrivoDecorator extends Decorator
{
    public TempoArrivoDecorator(Component component) {
        super(component);
    }
    @Override
    public RouteBean update(RouteBean rb, Route r) throws InvalidRouteInputExceptionRemoli {
        rb = super.update(rb,r);
        if(rb.getTempoDiArrivo()==0.0)
        {
            rb.setPercTerrenoUtilizzato(0.0);
        }
        return rb;
    }
}
