package com.blueicon.gpsblueicon.activities.tools;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by developer on 24/07/17.
 */

public class Constantes {
   // public static final String BASE_URL = "http://192.168.2.77:91/api/";
    public static final String BASE_URL = "http://api.distritosonata.com/api/";

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
    public static void hideKeyboard(Activity activity) {
        if (activity != null && activity.getWindow() != null && activity.getWindow().getDecorView() != null) {
            InputMethodManager imm = (InputMethodManager)activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(activity.getWindow().getDecorView().getWindowToken(), 0);
        }
    }
}
