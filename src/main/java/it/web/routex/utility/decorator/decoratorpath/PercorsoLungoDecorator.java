package it.web.routex.utility.decorator.decoratorpath;

import it.web.routex.bean.RouteBean;
import it.web.routex.exception.InvalidRouteInputExceptionRemoli;
import it.web.routex.model.Route;

public class PercorsoLungoDecorator extends Decorator
{
    public PercorsoLungoDecorator(Component component) {
        super(component);
    }

    @Override
    public RouteBean update(RouteBean rb, Route r) throws InvalidRouteInputExceptionRemoli {
        rb = super.update(rb,r);
        if(r.getnStazAttraversate()==1)
        {
            rb.setTempoDiArrivo(0.0);
        }
        return rb;
    }
}
