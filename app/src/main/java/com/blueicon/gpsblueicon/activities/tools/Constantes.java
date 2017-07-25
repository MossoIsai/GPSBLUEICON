package com.blueicon.gpsblueicon.activities.tools;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

/**
 * Created by developer on 24/07/17.
 */

public class Constantes {
    public static final String BASE_URL = "";

    /** :::::: Metodo que lanza un AlertDialog ::::::**/
    public static void messageDialog(String title, String message, Context context) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setTitle(null)
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
