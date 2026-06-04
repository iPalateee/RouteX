package it.web.routex.bean;


import it.web.routex.model.City;

/**
 * Bean per il trasferimento del solo nome della città verso la View.
 */
public class CityBean {

    private String nome;

    public CityBean() {}

    public CityBean(City city) {
        this.nome = city.getName();
    }

    public String getName() {
        return nome;
    }

    public void setName(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return nome;
    }
}

