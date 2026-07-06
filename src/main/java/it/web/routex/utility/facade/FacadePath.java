package it.web.routex.utility.facade;
import it.web.routex.bean.CityLifeBean;
import it.web.routex.bean.InformazioniPercorsoBean;
import it.web.routex.bean.RoutingRequestBean;
import it.web.routex.controller.applicativo.CityLifeController;
import it.web.routex.exception.FuoriRangeExceptionBrondi;
import it.web.routex.exception.UnreacheableNodeExceptionRemoli;
import it.web.routex.model.CityModel;
import it.web.routex.utility.factory.CityLifeFactory;
import java.sql.SQLException;
import java.util.List;

public class FacadePath
{
    public InformazioniPercorsoBean compute(RoutingRequestBean route) throws IllegalArgumentException, FuoriRangeExceptionBrondi, UnreacheableNodeExceptionRemoli, SQLException {
        CityLifeBean cityLife = routingProcess(route);
        return settingProcess(cityLife);
    }
    private CityLifeBean routingProcess (RoutingRequestBean route) throws IllegalArgumentException, FuoriRangeExceptionBrondi, UnreacheableNodeExceptionRemoli, SQLException {
        CityModel modelcity = CityLifeFactory.createCity(route.getCity());
        CityLifeController controller = new CityLifeController(modelcity);
        List<Integer> path = controller.dijkstra(
                route.getStartId(),
                route.getEndId()
        );
        return controller.calcolaPercorso(path, route.getCity());
    }
    private InformazioniPercorsoBean settingProcess(CityLifeBean cityLife)
    {
        int numeroStazioniUsate = 0;
        Double minutaggio = 0.0;
        Double percentualeStazioniUsate = 0.0;
        Double app = 0.0;
        InformazioniPercorsoBean trasferimento = new InformazioniPercorsoBean();
        numeroStazioniUsate = cityLife.getPercorsiConNomi().size();
        minutaggio = numeroStazioniUsate * 2.5;
        app = (double) numeroStazioniUsate / cityLife.getNumeroStazioniTotali();
        percentualeStazioniUsate = app * 100.0;
        trasferimento.setCityLife(cityLife);
        trasferimento.setMinutaggio(minutaggio);
        trasferimento.setNumeroStazioniUsate(numeroStazioniUsate);
        trasferimento.setPercentualeStazioniUsate(percentualeStazioniUsate);
        return trasferimento;
    }
}
