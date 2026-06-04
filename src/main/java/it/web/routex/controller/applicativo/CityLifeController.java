package it.web.routex.controller.applicativo;
import it.web.routex.bean.CityLifeBean;
import java.sql.SQLException;
import java.util.*;

import it.web.routex.dao.LayerPersistenza;
import it.web.routex.exception.FuoriRangeExceptionRemoli;
import it.web.routex.exception.UnreacheableNodeExceptionRemoli;
import it.web.routex.model.CityModel;
import it.web.routex.model.Fermata;
import it.web.routex.utility.factory.FactoryLayerPersistenza;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CityLifeController
{
    private final CityModel citylife;

    public CityLifeController(CityModel city) {
        this.citylife = city;
    }

    private static class StatoPercorso {

        boolean check = false;
        boolean noPass = false;
        boolean controllo = false;
        boolean ciSonPassato = false;
        boolean stopping = false;
        boolean ancora = false;
        boolean uno = false;

        int countBin = 0;
        int cambiLineeMetropolitane = 0;
        int quantoCiPasso = 0;
        int contatore = 0;
        int count = 0;
        int conta = 0;
        int checkino = 0;

        String nomeStazioneCambio = "";
        String ev = "";
        String lineaTemp = "";
        String daRaggiungere="";
        String temp="";
        String successivo="";
        String success="";
        String daNonRipetere="";

        List<String> percorsiConFermate = new ArrayList<>();
        List<Integer> percorsiCodifica = new ArrayList<>();
        List<String> sequenzeDiCambiamento = new ArrayList<>();
        List<String> sequenzeNodiCruciali = new ArrayList<>();
        List<String> listaAppoggio = new ArrayList<>();
        List<String> nomeCambio = new ArrayList<>();
        List<String> cambi = new ArrayList<>();
        List<String> cambiIniziali = new ArrayList<>();
        List<String> cambiInizialiLinee = new ArrayList<>();
        List<String> linee = new ArrayList<>();
        List<String> inMezzo = new ArrayList<>();
        List<String> inMezzoNomi = new ArrayList<>();
    }
    private void removeChangeLines(List<String> sequenzeDiCambiamentoFull)
    {
        for (int i = sequenzeDiCambiamentoFull.size() - 1; i >= 0; i--) {
            if (sequenzeDiCambiamentoFull.get(i).contains("-")) {
                sequenzeDiCambiamentoFull.remove(i);
            }
        }

    }
    private void removeDuplicate(List<String> sequenzeDiCambiamentoFull,int indice) {
        for(int i=indice;i<sequenzeDiCambiamentoFull.size();i++)
        {
            if(i==sequenzeDiCambiamentoFull.size()-1)
                return;
            if(sequenzeDiCambiamentoFull.get(i).equals(sequenzeDiCambiamentoFull.get(i+1)))
            {
                sequenzeDiCambiamentoFull.remove(i);
                removeDuplicate(sequenzeDiCambiamentoFull,i);

            }
        }

    }

    public CityLifeBean calcolaPercorso(List<Integer> ids, String city) throws SQLException {
        final Logger logger = LoggerFactory.getLogger(getClass());
        StatoPercorso stato = new StatoPercorso();

        LayerPersistenza layer = FactoryLayerPersistenza.createLayerPersistenza();
        List<Fermata> fermateTot = layer.getFermateByIds(ids, city);

        for (int i = 0; i < fermateTot.size(); i++)
        {
            Fermata corrente = fermateTot.get(i);
            gestisciCambiIniziali(corrente, stato, i);
            gestisciLogicaCambiPassoInduttivo(corrente, stato, i);

        }

        removeDuplicate(stato.cambi, 0);

        if (stato.cambi != null && !stato.cambi.isEmpty()) {

            for (int j = stato.cambi.size() - 1; j >= 0; j--) {
                String cambio = stato.cambi.get(j);

                logger.info("Cambio {}", cambio);
                stato.sequenzeNodiCruciali.add(cambio);
            }

        }
        stato.cambiLineeMetropolitane = stato.sequenzeNodiCruciali.size();
        logger.info("Cambi linee metropolitane in CityLifeController.java {}", stato.cambiLineeMetropolitane);

        removeDuplicate(stato.sequenzeDiCambiamento, 0);
        removeChangeLines(stato.sequenzeDiCambiamento);


        CityLifeBean c = new CityLifeBean();
        c.setLinee(stato.linee);
        c.setNumeroCambi(stato.cambiLineeMetropolitane);
        c.setSequenzeDiCambiamento(stato.sequenzeDiCambiamento);
        c.setSequenzeNodiCruciali(stato.sequenzeNodiCruciali);
        c.setPercorsiConNomi(stato.percorsiConFermate);
        c.setNumeroStazioniTotali(citylife.getMatriceAdiacenza().length);
        c.setSequenzeNodiCruciali(stato.sequenzeNodiCruciali);



        return c;
    }

    private boolean checkfill(int[]a, int dim)
    {
        for(int i=0;i<dim;i++)
        {
            if(a[i] == -1)
                return true;
        }
        return false;
    }
    private int[] trovaAdj(int nodo, int[][]adiacenza)
    {
        int index = 0;
        int [] adiacenti = new int[(adiacenza.length)-1];
        Arrays.fill(adiacenti,-1);
        for(int i=0;i< adiacenza.length;i++)
        {
            if((adiacenza[nodo][i]!=-1)&&(adiacenza[nodo][i]!=0))
            {
                adiacenti[index] = i;
                index = index + 1;
            }
        }
        return adiacenti;
    }
    private int nodoCostoMinore(int[]cost, int[]know)
    {
        int min=0;
        int minimoNodo=0;
        int temp = 10000;
        for(int i=0;i<cost.length;i++)
        {
            if(know[i]==-1)
            {
                min = cost[i];
                if(temp > min)
                {
                    temp = min;
                    minimoNodo = i;
                }
            }
        }
        return minimoNodo;
    }
    private void stampaPercorso(int nodo, int[] precedente, ArrayList<Integer> percorsiCodifica) {
        Deque<Integer> percorso = new ArrayDeque<>();
        int app;
        while (nodo != -1) {
            percorso.push(nodo);
            nodo = precedente[nodo];
        }
        while (!percorso.isEmpty()) {
            app = percorso.pop();
            percorsiCodifica.add(app);

        }
    }
    private void rilassaAdiacenti(int nodoPartenza, int[][] matriceAdiacenza, int[] know, int[] cost, int[] precedente)
    {
        int[] adiacentiVector = trovaAdj(nodoPartenza, matriceAdiacenza);

        for (int i = 0; i < adiacentiVector.length; i++) {
            rilassaNodo(adiacentiVector[i], nodoPartenza, matriceAdiacenza, know, cost, precedente);
        }
    }

    private void rilassaNodo(int adjTemp, int nodoPartenza, int[][] matriceAdiacenza, int[] know, int[] cost, int[] precedente)
    {
        if (adjTemp == -1) {
            return;
        }

        if (know[adjTemp] != -1) {
            return;
        }

        int nuovoCosto = cost[nodoPartenza] + matriceAdiacenza[nodoPartenza][adjTemp];

        if (cost[adjTemp] > nuovoCosto) {
            cost[adjTemp] = nuovoCosto;
            precedente[adjTemp] = nodoPartenza;
        }
    }


    public List<Integer> dijkstra(int partenza, int arrivo) throws FuoriRangeExceptionRemoli, UnreacheableNodeExceptionRemoli {
        if (partenza < 0 || partenza >= citylife.getMatriceAdiacenza().length)
            throw new FuoriRangeExceptionRemoli("ID partenza fuori range: " + partenza, FuoriRangeExceptionRemoli.Severity.CRITICAL );

        if (arrivo < 0 || arrivo >= citylife.getMatriceAdiacenza().length)
            throw new FuoriRangeExceptionRemoli("ID arrivo fuori range: " + arrivo, FuoriRangeExceptionRemoli.Severity.CRITICAL);


        ArrayList<Integer> percorsiCodifica = new ArrayList<Integer>();
        int nodoPartenza = partenza;
        int nodoArrivo = arrivo;
        int [] know = new int[citylife.getMatriceAdiacenza().length];
        int [] cost = new int[citylife.getMatriceAdiacenza().length];
        int[] precedente = new int[citylife.getMatriceAdiacenza().length];


        Arrays.fill(cost, 1000);
        Arrays.fill(know, -1);
        know[nodoPartenza] = 1;
        cost[nodoPartenza] = 0;
        Arrays.fill(precedente, -1);

        while (checkfill(know, citylife.getMatriceAdiacenza().length)) {

            rilassaAdiacenti(nodoPartenza, citylife.getMatriceAdiacenza(), know, cost, precedente);
            nodoPartenza = nodoCostoMinore(cost, know);
            know[nodoPartenza] = 1;
        }

        if (cost[nodoArrivo] == 1000 || precedente[nodoArrivo] == -1) {
            throw new UnreacheableNodeExceptionRemoli(
                    "Il percorso verso la stazione selezionata non è disponibile.",
                    "Nodo " + nodoArrivo + " irraggiungibile da " + nodoPartenza,
                    UnreacheableNodeExceptionRemoli.Severity.CRITICAL
            );
        }
        for (int i = 0; i < citylife.getMatriceAdiacenza().length; i++)
            if(i==nodoArrivo) this.stampaPercorso(i, precedente, percorsiCodifica);

        return percorsiCodifica;
    }

    private void gestisciCambiIniziali(Fermata corrente, StatoPercorso statoPercorso, int i)
    {
        String linea = corrente.getLinea();
        String fermate = corrente.getNome();
        checkFirstStation(statoPercorso, corrente, i);
        checkSecondStation(statoPercorso, corrente, i);

        if (!statoPercorso.lineaTemp.equals(linea) && i == 1) {
            statoPercorso.noPass = true;
        }
        if (!statoPercorso.stopping && statoPercorso.ancora && i > 1) {
            if (corrente.isLineaComposta())
            {
                statoPercorso.stopping = false;
                statoPercorso.noPass = true;
                statoPercorso.cambiIniziali.add(fermate);
                statoPercorso.cambiInizialiLinee.add(linea);
                statoPercorso.ancora = true;
            }
            else
            {
                statoPercorso.stopping = true;
                statoPercorso.success = linea;
                statoPercorso.noPass = true;
            }

        }
    }
    private void gestisciLogicaCambiPassoInduttivo(Fermata corrente, StatoPercorso statoPercorso, int i)
    {

        if (statoPercorso.check)
        {
            path1(statoPercorso,corrente,i);
        }
        else
        {
            path2(statoPercorso,corrente,i);
        }
        String linea = corrente.getLinea();
        String fermate = corrente.getNome();
        statoPercorso.percorsiConFermate.add(fermate); //linea che consente di stampare i percorsi con fermate (non toccare)
        statoPercorso.linee.add(linea);
    }
    private void cambiamentometropolitano(StatoPercorso statoPercorso)
    {
        for (int k = 0; k < statoPercorso.inMezzo.size(); k++) {
            String[] appoggio = statoPercorso.inMezzo.get(k).split("-");
            for (String parola : appoggio) {
                if (!parola.equals(statoPercorso.ev)) {
                    statoPercorso.conta = statoPercorso.conta + 1;
                    if (statoPercorso.conta == appoggio.length)
                    {
                        effettuaCambio(statoPercorso, k);
                    }
                } else {
                    statoPercorso.conta = 0;
                }
            }
        }
    }
    private void effettuaCambio(StatoPercorso statoPercorso, int k)
    {
        statoPercorso.cambiLineeMetropolitane = statoPercorso.cambiLineeMetropolitane + 1;
        statoPercorso.sequenzeNodiCruciali.add(statoPercorso.inMezzoNomi.get(k - 1));
        statoPercorso.sequenzeDiCambiamento.add(statoPercorso.ev);
        for (int index = 0; index < statoPercorso.inMezzo.size() - 1; index++)
        {
            String[] parolina = statoPercorso.inMezzo.get(index).split("-");
            for (String p : parolina)
            {
                if (!p.equals(statoPercorso.ev))
                {
                    String par = p;
                    String[] parolina2 = statoPercorso.inMezzo.get(index + 1).split("-");
                    for (String g : parolina2)
                    {
                        if (par.equals(g)) {
                            statoPercorso.sequenzeDiCambiamento.add(par);
                        }
                    }
                }
            }
        }
    }
    private void fermataCambio(StatoPercorso statoPercorso, String linea, String fermate)
    {
        statoPercorso.quantoCiPasso = statoPercorso.quantoCiPasso + 1;

        statoPercorso.inMezzo.add(linea);
        statoPercorso.inMezzoNomi.add(fermate);
        statoPercorso.check = true;
        statoPercorso.ciSonPassato = true;
    }
    private void fermataNocambio(StatoPercorso statoPercorso, String linea)
    {
        statoPercorso.cambiLineeMetropolitane = statoPercorso.cambiLineeMetropolitane + 1;
        statoPercorso.sequenzeDiCambiamento.add(statoPercorso.lineaTemp);
        statoPercorso.successivo = linea;
        statoPercorso.daNonRipetere = linea;
        if (statoPercorso.quantoCiPasso == 0)
            statoPercorso.sequenzeNodiCruciali.add(statoPercorso.nomeStazioneCambio);
        else
        {

            for (int j = 0; j < statoPercorso.inMezzo.size() - 1; j++) {
                if (statoPercorso.inMezzo.get(j).equals(statoPercorso.inMezzo.get(j + 1))) {
                    statoPercorso.count = statoPercorso.count + 1;
                }
            }
            if (statoPercorso.count == statoPercorso.inMezzo.size() - 1) {
                statoPercorso.count = 0;
                statoPercorso.sequenzeNodiCruciali.add(statoPercorso.inMezzoNomi.get(statoPercorso.inMezzoNomi.size() - 1));
            }
            else
            {
                fermata(statoPercorso,linea);
            }

        }
        statoPercorso.inMezzo.clear();
        statoPercorso.inMezzoNomi.clear();
        statoPercorso.check = false;
        statoPercorso.lineaTemp = statoPercorso.daNonRipetere;
        statoPercorso.quantoCiPasso = 0;
        statoPercorso.sequenzeDiCambiamento.add(statoPercorso.successivo);

    }
    private void fermata(StatoPercorso statoPercorso, String linea)
    {
        while (!(statoPercorso.daRaggiungere.equals(statoPercorso.ev)))
        {
            for (int j = 0; j < statoPercorso.inMezzo.size(); j++)
            {
                if (statoPercorso.inMezzo.get(j).contains(linea))
                {
                    statoPercorso.temp = statoPercorso.inMezzo.get(j);
                    statoPercorso.nomeCambio.add(statoPercorso.inMezzoNomi.get(j)); //nome_cambio ho tutto
                    statoPercorso.listaAppoggio.add(statoPercorso.temp);
                    statoPercorso.inMezzo.set(j, "");
                    statoPercorso.inMezzoNomi.set(j, "");

                }
            }
            for (int l = 0; l < statoPercorso.listaAppoggio.size(); l++)
            {
                statoPercorso.checkino = 0;
                String[] parole = statoPercorso.listaAppoggio.get(l).split("-");
                if(parola(statoPercorso, parole, l))
                    break;
            }
            statoPercorso.listaAppoggio.clear();
            statoPercorso.nomeCambio.clear();
        }

    }
    private boolean parola(StatoPercorso statoPercorso, String[] parole, int l)
    {
        for (String parola : parole) {
            if (!(parola.equals(statoPercorso.daNonRipetere))) //se parola != linea
            {
                statoPercorso.daRaggiungere = parola;
                if (!(statoPercorso.daRaggiungere.equals(statoPercorso.ev))) {
                    String lineaCorrente = statoPercorso.daRaggiungere;

                    statoPercorso.sequenzeDiCambiamento.add(lineaCorrente);
                    if (statoPercorso.checkino == 0) {
                        statoPercorso.cambiLineeMetropolitane = statoPercorso.cambiLineeMetropolitane + 1;
                        statoPercorso.cambi.add(statoPercorso.nomeCambio.get(l));
                    }
                    statoPercorso.checkino++;
                } else {
                    statoPercorso.cambi.add(statoPercorso.nomeCambio.get(l));
                    return true;
                }
            }
        }
        return false;

    }
    private void resetting(StatoPercorso statoPercorso, String linea)
    {
        statoPercorso.check = false;
        statoPercorso.lineaTemp = linea;

        if (statoPercorso.ciSonPassato) {
            statoPercorso.inMezzo.clear();
            statoPercorso.inMezzoNomi.clear();
            statoPercorso.check = false;
            statoPercorso.lineaTemp = linea;
            statoPercorso.ciSonPassato = false;
        }

    }
    private void path1(StatoPercorso statoPercorso, Fermata corrente, int i)
    {
        String linea = corrente.getLinea();
        String fermate = corrente.getNome();
        if (!statoPercorso.lineaTemp.equals(linea))
        {
            if (corrente.isLineaComposta())
            {
                fermataCambio(statoPercorso, linea, fermate);
                if (i == statoPercorso.percorsiCodifica.size() - 1) {
                    cambiamentometropolitano(statoPercorso);
                }
            }
            else
            {
                fermataNocambio(statoPercorso, linea);
            }
        }
        else
        {
            resetting(statoPercorso, linea);
        }

    }
    private void path2(StatoPercorso statoPercorso, Fermata corrente, int i)
    {
        String linea = corrente.getLinea();
        String fermate = corrente.getNome();
        if (statoPercorso.countBin != 0)
        {
            if (!statoPercorso.lineaTemp.equals(linea))
            {

                if ((i == 1) && (!statoPercorso.controllo))
                {
                    statoPercorso.check = true;
                    statoPercorso.nomeStazioneCambio = fermate; //la aggiungo solo se poi effettivamente rispetta check
                    statoPercorso.noPass = false;
                    statoPercorso.inMezzo.add(linea);
                    statoPercorso.inMezzoNomi.add(fermate);
                    statoPercorso.ev = statoPercorso.lineaTemp;
                }
                else
                {
                    if (!statoPercorso.noPass)
                    {
                        statoPercorso.check = true;
                        statoPercorso.nomeStazioneCambio = fermate; //la aggiungo solo se poi effettivamente rispetta check
                        statoPercorso.inMezzo.add(linea);
                        statoPercorso.inMezzoNomi.add(fermate);
                        statoPercorso.ev = statoPercorso.lineaTemp;
                        statoPercorso.ciSonPassato = true;
                    } else
                    {
                        passing(statoPercorso,linea);
                    }
                }

            } else {
                statoPercorso.lineaTemp = linea;
            }
        } else {
            statoPercorso.lineaTemp = linea;
            statoPercorso.countBin = statoPercorso.countBin + 1;
        }
    }
    private void passing(StatoPercorso statoPercorso, String linea)
    {
        statoPercorso.noPass = false;
        if (statoPercorso.stopping)
        {
            for (int j = 0; j < statoPercorso.cambiInizialiLinee.size(); j++)
            {
                String[] parole = statoPercorso.cambiInizialiLinee.get(j).split("-");
                for (String parola : parole)
                {
                    if (parola.equals(statoPercorso.success)) {
                        statoPercorso.contatore++;
                        statoPercorso.nomeStazioneCambio = statoPercorso.cambiIniziali.get(j);
                    }
                }
            }
            if (statoPercorso.contatore == statoPercorso.cambiInizialiLinee.size()) {
                statoPercorso.nomeStazioneCambio = "";
            } else {
                statoPercorso.sequenzeNodiCruciali.add(statoPercorso.nomeStazioneCambio);
                statoPercorso.sequenzeDiCambiamento.add(statoPercorso.success);
            }
            statoPercorso.contatore = 0;
            statoPercorso.cambiIniziali.clear();
            statoPercorso.cambiInizialiLinee.clear();
        }
        statoPercorso.lineaTemp = linea;
    }
    private void checkFirstStation(StatoPercorso statoPercorso, Fermata corrente, int i)
    {
        String linea = corrente.getLinea();
        String fermate = corrente.getNome();
        if (corrente.isLineaComposta() && i == 0) {
            statoPercorso.controllo = true;
            statoPercorso.cambiIniziali.add(fermate);
            statoPercorso.cambiInizialiLinee.add(linea);
            statoPercorso.uno = true;
        }
    }
    private void checkSecondStation(StatoPercorso statoPercorso, Fermata corrente, int i)
    {
        String linea = corrente.getLinea();
        String fermate = corrente.getNome();
        if (corrente.isLineaComposta() && i == 1) {
            statoPercorso.noPass = true;
            statoPercorso.cambiIniziali.add(fermate);
            statoPercorso.cambiInizialiLinee.add(linea);
            statoPercorso.stopping = false;

            if (statoPercorso.uno) {
                statoPercorso.ancora = true;
            }
        }
    }

}
