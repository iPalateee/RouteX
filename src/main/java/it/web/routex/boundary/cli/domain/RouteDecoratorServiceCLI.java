package it.web.routex.boundary.cli.domain;

import it.web.routex.bean.InformazioniPercorsoBean;
import it.web.routex.utility.builder.PathNORegBuilder;
import it.web.routex.utility.decorator.decoratorchange.BaseComponent;
import it.web.routex.utility.decorator.decoratorchange.CheckCambiamentiDecorator;
import it.web.routex.utility.decorator.decoratorchange.Component;

public final class RouteDecoratorServiceCLI {

    private RouteDecoratorServiceCLI(){

    }

    public static void decorate(InformazioniPercorsoBean dto)
    {
        Component c = new CheckCambiamentiDecorator(new BaseComponent());
        new PathNORegBuilder(dto.getCityLife().getPercorsiConNomi())
                .numeroCambi(dto.getCityLife().getNumeroCambi())
                .linee(dto.getCityLife().getLinee())
                .numeroStazioniUsate(dto.getNumeroStazioniUsate())
                .minutaggio(dto.getMinutaggio())
                .numeroStazioniTotali(dto.getCityLife().getNumeroStazioniTotali())
                .percentualeStazioniUsate(dto.getPercentualeStazioniUsate())
                .sequenzeDiCambiamento(c.getChanges(dto.getCityLife().getSequenzeDiCambiamento()))
                .sequenzeNodiCruciali(c.getChanges(dto.getCityLife().getSequenzeNodiCruciali()))
                .build();

    }
}
