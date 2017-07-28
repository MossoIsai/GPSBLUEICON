package com.blueicon.gpsblueicon.activities.models.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class NegocioMain {
    @SerializedName("Negocio")
    @Expose
    private List<Negocio> negocios;
    @SerializedName("Estatus")
    @Expose
    private boolean estatus;
    @SerializedName("Mensaje")
    @Expose
    private String mensaje;

    public NegocioMain() {

    }
    public NegocioMain(List<Negocio> negocios, boolean estatus, String mensaje) {
        this.negocios = negocios;
        this.estatus = estatus;
        this.mensaje = mensaje;
    }
    public List<Negocio> getNegocios() {
        return negocios;
    }
    public void setNegocios(List<Negocio> negocios) {
        this.negocios = negocios;
    }
    public boolean isEstatus() {
        return estatus;
    }
    public void setEstatus(boolean estatus) {
        this.estatus = estatus;
    }
    public String getMensaje() {
        return mensaje;
    }
    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
