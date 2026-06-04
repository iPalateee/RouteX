package it.web.routex.fx;

import it.web.routex.bean.InformazioniPercorsoBean;

public final class ResultContext {

    private static InformazioniPercorsoBean result;
    private static String statuss;
    private static String startt;
    private static String endd;
    private static String cityy;


    private ResultContext() {

    }

    public static void setResult(InformazioniPercorsoBean dto, String status, String start, String end, String city) {
        result = dto;
        statuss = status;
        startt = start;
        endd = end;
        cityy = city;
    }

    public static String getCity() {
        return cityy;
    }

    public static String getStatus() {
        return statuss;
    }

    public static String getStart() {
        return startt;
    }

    public static String getEnd() {
        return endd;
    }

    public static InformazioniPercorsoBean getResult() {
        return result;
    }

    public static void clear() {
        result = null;
        statuss = null;
        startt = null;
        endd = null;
        cityy = null;
    }
}
