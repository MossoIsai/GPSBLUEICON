package com.blueicon.gpsblueicon.activities.models.response;

import java.util.ArrayList;

/**
 * Created by developer on 26/07/17.
 */

public class HorarioItem {

    private ArrayList<String> stringsArrays;
    private String horar;

    public ArrayList<String> getStringsArrays() {
        return stringsArrays;
    }

    public void setStringsArrays(ArrayList<String> stringsArrays) {
        this.stringsArrays = stringsArrays;
    }

    public String getHorar() {
        return horar;
    }

    public void setHorar(String horar) {
        this.horar = horar;
    }

    public HorarioItem(ArrayList<String> stringsArrays, String horar) {
        this.stringsArrays = stringsArrays;
        this.horar = horar;
    }
    public HorarioItem(){

    }
}
