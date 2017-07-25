package com.blueicon.gpsblueicon.activities.http;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by developer on 24/07/17.
 */

public class NegocioRequest {

    @SerializedName("Id")
    @Expose
    private  int id;

    @SerializedName("Nombre")
    @Expose
    private String nombre;

    @SerializedName("Latitud")
    @Expose
    private String latitud;

    @SerializedName("Longitud")
    @Expose
    private String longitud;

    @SerializedName("Direccion")
    @Expose
    private String direccion;

    @SerializedName("TelefonoPrincipal")
    @Expose
    private String telefonoPrincipal;

    @SerializedName("TelefonoSecundario")
    @Expose
    private String telefonoSecundario;

    @SerializedName("Descripcion")
    @Expose
    private String descripcion;

    @SerializedName("Horario")
    @Expose
    private String horario;

    @SerializedName("ServicioDocimicilio")
    @Expose
    private boolean servicioDomicilio;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefonoPrincipal() {
        return telefonoPrincipal;
    }

    public void setTelefonoPrincipal(String telefonoPrincipal) {
        this.telefonoPrincipal = telefonoPrincipal;
    }

    public String getTelefonoSecundario() {
        return telefonoSecundario;
    }

    public void setTelefonoSecundario(String telefonoSecundario) {
        this.telefonoSecundario = telefonoSecundario;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public boolean isServicioDomicilio() {
        return servicioDomicilio;
    }

    public void setServicioDomicilio(boolean servicioDomicilio) {
        this.servicioDomicilio = servicioDomicilio;
    }
}
