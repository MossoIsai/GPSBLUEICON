package com.blueicon.gpsblueicon.activities.controller;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import com.blueicon.gpsblueicon.R;
import com.blueicon.gpsblueicon.activities.adapters.AdapterGridView;
import com.blueicon.gpsblueicon.activities.http.ClienteRetrofit;
import com.blueicon.gpsblueicon.activities.http.HttpNegocios;
import com.blueicon.gpsblueicon.activities.models.request.NegocioRequest;
import com.blueicon.gpsblueicon.activities.models.response.Horario;
import com.blueicon.gpsblueicon.activities.models.response.SuccesResponse;
import com.blueicon.gpsblueicon.activities.tools.Constantes;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.MapStyleOptions;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by developer on 24/07/17.
 */

public class LevantarCoordenadas extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap mapaGoogle;

    @BindView(R.id.nameNegocio) EditText nombre;
    @BindView(R.id.addressNegocio) EditText direccion;
    @BindView(R.id.phonePrincipal) EditText principalTelefono;
    @BindView(R.id.phoneSecundario) EditText secundarioTelefono;
    @BindView(R.id.servicioDomicilionegocio) Switch servicioDomicilio;
    @BindView(R.id.descriptionNegocio) EditText descripcion;
    @BindView(R.id.gridHorario) GridView gridView;
    @BindView(R.id.listaDias) TextView listaDiasTxt;
    @BindView(R.id.btnHorario) Button btnAddHorario;
    @BindView(R.id.btnSave) Button btnSaveinfo;


    @OnClick(R.id.btnHorario)
    void addHour() {
        DialogAddHour dialogAddHour = new DialogAddHour();
        dialogAddHour.showDialog(this);

    }
    @OnClick(R.id.btnSave) void btnSaveInfoNegocio(){

        HttpNegocios httpNegocios = ClienteRetrofit.getSharedInstance().create(HttpNegocios.class);
        Call<SuccesResponse> responseCall = httpNegocios.sendaNegocio( new NegocioRequest());
        responseCall.enqueue(new Callback<SuccesResponse>() {
            @Override
            public void onResponse(Call<SuccesResponse> call, Response<SuccesResponse> response) {
                 if(response.body().isEstatus()){

                 }else{

                 }
            }
            @Override
            public void onFailure(Call<SuccesResponse> call, Throwable t) {

            }
        });
    }

    private List<Horario> horarioList = new ArrayList<>();
    private AdapterGridView adapterGridView;
    private String[] diasAbreviados = {"Lun", "Mar", "Mié", "Jue", "Vie", "Sáb", "Dom"};
    private ArrayList<String> diasAgregados = new ArrayList<>();
    private int contador = 0;
    private boolean[] presionado = {false, false, false, false, false, false, false};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.levantar_coordenas_layout);
        ButterKnife.bind(this);
        horarioList.add(new Horario("L"));
        horarioList.add(new Horario("M"));
        horarioList.add(new Horario("M"));
        horarioList.add(new Horario("J"));
        horarioList.add(new Horario("V"));
        horarioList.add(new Horario("S"));
        horarioList.add(new Horario("D"));
        adapterGridView = new AdapterGridView(this, horarioList);
        gridView.setAdapter(adapterGridView);
        gridView.setVisibility(View.GONE);
        gridView.setOnItemClickListener(listenerSelectedia);
        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.mapa);
        mapFragment.getMapAsync(LevantarCoordenadas.this);
        principalTelefono.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
        secundarioTelefono.addTextChangedListener(new PhoneNumberFormattingTextWatcher());

    }

    AdapterView.OnItemClickListener listenerSelectedia = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
            //Toast.makeText(getApplicationContext(), "Horario ------->" + horarioList.get(position).getTextDia(), Toast.LENGTH_LONG).show();
            TextView textView = (TextView) view.findViewById(R.id.textDiaSemana);
            if (presionado[position]) {
                presionado[position] = false;
                for (int i = 0; i < presionado.length; i++) {
                    if (!presionado[i]) {
                        textView.setTextColor(Color.parseColor("#8b8b8b"));
                    }
                }

            } else {  // cuando es primera vez true
                presionado[position] = true;
                for (int i = 0; i < presionado.length; i++) {
                    if (presionado[i]) {
                        textView.setTextColor(Color.parseColor("#20bbcc"));
                    }
                }
            }
        }

    };


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mapaGoogle = googleMap;
        // mapaGoogle.setOnMapLongClickListener(listener);
        // mapaGoogle.setOnMapClickListener(onMapClickListener);

        boolean success = mapaGoogle.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.style_retro));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mapaGoogle.setPadding(10, 250, 10, 250);
        mapaGoogle.setMyLocationEnabled(true);
        //mapaGoogle.getUiSettings().setMyLocationButtonEnabled(false);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        View v = getCurrentFocus();

        if (v != null &&
                (ev.getAction() == MotionEvent.ACTION_UP || ev.getAction() == MotionEvent.ACTION_MOVE) &&
                v instanceof EditText &&
                !v.getClass().getName().startsWith("android.webkit.")) {
            int scrcoords[] = new int[2];
            v.getLocationOnScreen(scrcoords);
            float x = ev.getRawX() + v.getLeft() - scrcoords[0];
            float y = ev.getRawY() + v.getTop() - scrcoords[1];

            if (x < v.getLeft() || x > v.getRight() || y < v.getTop() || y > v.getBottom())
            // hideKeyboard(this);
            /** ::::::::: MANDO A LLAMAR EL METODO QU SE ENCUENTRA DENTRO DE COMTRANTES ::::::::***/
                Constantes.hideKeyboard(this);
        }
        return super.dispatchTouchEvent(ev);
    }

    class DialogAddHour {


        public void showDialog(Activity activity) {


            final Dialog dialog = new Dialog(activity);
            Calendar calendar;

            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.horario_dialog_layout);

            List<Horario> horarioList = new ArrayList<>();
            AdapterGridView adapterGridView;
            boolean[] presionado = {false, false, false, false, false, false, false};

            horarioList.add(new Horario("L"));
            horarioList.add(new Horario("M"));
            horarioList.add(new Horario("M"));
            horarioList.add(new Horario("J"));
            horarioList.add(new Horario("V"));
            horarioList.add(new Horario("S"));
            horarioList.add(new Horario("D"));


            GridView gridView = (GridView) dialog.findViewById(R.id.gridHorarioDialog);
            TimePicker timePickerInicio = (TimePicker) dialog.findViewById(R.id.picker1);
            TimePicker timePickerCerrado = (TimePicker) dialog.findViewById(R.id.picker2);
            Button btnSaveHours = (Button) dialog.findViewById(R.id.saveHours);
            adapterGridView = new AdapterGridView(activity, horarioList);
            gridView.setAdapter(adapterGridView);
            gridView.setOnItemClickListener(listenerSelectedia);

            timePickerCerrado.setIs24HourView(true);
            timePickerInicio.setIs24HourView(true);


            timePickerInicio.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
                @Override
                public void onTimeChanged(TimePicker timePicker, int i, int i1) {

                }
            });

            btnSaveHours.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }

            });

            dialog.show();

        }
    }
}
