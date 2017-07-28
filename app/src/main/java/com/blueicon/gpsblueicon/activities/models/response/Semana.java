package com.blueicon.gpsblueicon.activities.models.response;

/**
 * Created by developer on 27/07/17.
 */

public class Semana {
    private String nombreDia;
    private boolean estatus;

    public Semana(String nombreDia, boolean estatus) {
        this.nombreDia = nombreDia;
        this.estatus = estatus;
    }

    public String getNombreDia() {

        return nombreDia;
    }

    public void setNombreDia(String nombreDia) {
        this.nombreDia = nombreDia;
    }

    public boolean isEstatus() {
        return estatus;
    }

    public void setEstatus(boolean estatus) {
        this.estatus = estatus;
    }
}
