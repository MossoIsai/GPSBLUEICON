package com.blueicon.gpsblueicon.activities.models.response;

import java.util.List;

/**
 * Created by developer on 26/07/17.
 */

public class ObjectListaHorario {


    //private List<Boolean> diasMarcados;
    private List<Semana> diasMarcados;
    private String horaApertura;
    private String horaCerrado;

    public ObjectListaHorario(){

    }

    public ObjectListaHorario(List<Semana> diasMarcados, String horaApertura, String horaCerrado) {
        this.diasMarcados = diasMarcados;
        this.horaApertura = horaApertura;
        this.horaCerrado = horaCerrado;
    }

    public List<Semana> getDiasMarcados() {
        return diasMarcados;
    }

    public void setDiasMarcados(List<Semana> diasMarcados) {
        this.diasMarcados = diasMarcados;
    }

    public String getHoraApertura() {
        return horaApertura;
    }

    public void setHoraApertura(String horaApertura) {
        this.horaApertura = horaApertura;
    }

    public String getHoraCerrado() {
        return horaCerrado;
    }

    public void setHoraCerrado(String horaCerrado) {
        this.horaCerrado = horaCerrado;
    }
}
