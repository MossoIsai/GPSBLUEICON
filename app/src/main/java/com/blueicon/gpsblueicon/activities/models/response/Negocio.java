package com.blueicon.gpsblueicon.activities.models.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by developer on 26/07/17.
 */

public class Negocio {
    @SerializedName("NegocioId")
    @Expose
    private int negocioId;
    @SerializedName("Nombre")
    @Expose
    private String nombre;

    public Negocio() {
    }

    public Negocio(int negocioId, String nombre) {
        this.negocioId = negocioId;
        this.nombre = nombre;
    }

    public int getNegocioId() {
        return negocioId;
    }

    public void setNegocioId(int negocioId) {
        this.negocioId = negocioId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
