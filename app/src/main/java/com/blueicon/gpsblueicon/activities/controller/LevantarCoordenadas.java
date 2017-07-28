package com.blueicon.gpsblueicon.activities.controller;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import com.blueicon.gpsblueicon.R;
import com.blueicon.gpsblueicon.activities.adapters.AdapterGridView;
import com.blueicon.gpsblueicon.activities.adapters.AdapterHorario;
import com.blueicon.gpsblueicon.activities.http.ClienteRetrofit;
import com.blueicon.gpsblueicon.activities.http.HttpNegocios;
import com.blueicon.gpsblueicon.activities.models.request.NegocioRequest;
import com.blueicon.gpsblueicon.activities.models.response.Horario;
import com.blueicon.gpsblueicon.activities.models.response.ObjectListaHorario;
import com.blueicon.gpsblueicon.activities.models.response.Semana;
import com.blueicon.gpsblueicon.activities.models.response.SuccesResponse;
import com.blueicon.gpsblueicon.activities.tools.Constantes;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by developer on 24/07/17.
 */

public class LevantarCoordenadas extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap googleMap;
    private List<Horario> horarioList = new ArrayList<>();
    private AdapterGridView adapterGridView;
    private String[] diasAbreviados = {"Dom", "Lun", "Mar", "Mié", "Jue", "Vie", "Sáb",};
    private ArrayList<String> diasAgregados = new ArrayList<>();
    private int contador = 0;
    private boolean[] presionado = {false, false, false, false, false, false, false};
    private String[] dias = {"D", "L", "M", "Mi", "J", "V", "S",};

    private List<ObjectListaHorario> objectListaHorarios;
    private AdapterHorario adapterHorario;
    private List<Boolean> diasMarcados;
    private List<Semana> semanaList;

    @BindView(R.id.nameNegocio) EditText nombre;
    @BindView(R.id.addressNegocio) EditText direccion;
    @BindView(R.id.phonePrincipal) EditText principalTelefono;
    @BindView(R.id.phoneSecundario) EditText secundarioTelefono;
    @BindView(R.id.servicioDomicilionegocio) Switch servicioDomicilio;
    @BindView(R.id.descriptionNegocio) EditText descripcion;
    // @BindView(R.id.listaDias) TextView listaDiasTxt;
    @BindView(R.id.btnHorario) Button btnAddHorario;
    @BindView(R.id.btnSave) Button btnSaveinfo;
    @BindView(R.id.spinnerGiro) Spinner spinnerGiro;
    @BindView(R.id.spinnerEstacinamiento) Spinner spinnerEstacionamiento;
    @BindView(R.id.recyclerHorario) RecyclerView recyclerView;
    @BindView(R.id.mapa) MapView mapView;
    @BindView(R.id.textHorario) TextView textViewHorario;



    @OnClick(R.id.btnHorario)
    void addHour() {
        DialogAddHour dialogAddHour = new DialogAddHour();
        dialogAddHour.showDialog(this);

    }

    @OnClick(R.id.btnSave)
    void btnSaveInfoNegocio() {
        int diaNumero = 0;

        for (ObjectListaHorario objectListaHorario : objectListaHorarios) {
            for (Semana semana : objectListaHorario.getDiasMarcados()) {
                if (semana.isEstatus()) {
                    switch (semana.getNombreDia()){
                        case "L":
                            diaNumero  = 1;
                            break;
                        case "M":
                            diaNumero = 2;
                            break;
                        case "Mi":
                            diaNumero = 3;
                        break;
                        case "J":
                            diaNumero = 4;
                            break;
                        case "V":
                            diaNumero = 5;
                            break;
                        case "S":
                            diaNumero = 6;
                            break;
                        case  "D":
                            diaNumero = 0;
                            break;
                    }
                    //System.out.println("DiaSemana: " +diaNumero+ "@" +objectListaHorario.getHoraApertura()+"-"+objectListaHorario.getHoraCerrado()+"|");
                    textViewHorario.append(diaNumero+"@"+objectListaHorario.getHoraApertura()+"-"+objectListaHorario.getHoraCerrado()+"|");
                }
            }
        }
            System.out.println("HORARIO FINAL: "+textViewHorario.getText());
        armarHorario("1@9:00-12:00|2@9:00-12:00|3@9:00-12:00|4@9:00-12:00|5@9:00-12:00|1@14:00-16:00|2@14:00-16:00|3@14:00-16:00|4@14:00-16:00|5@14:00-16:00");

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.levantar_coordenas_layout);
        ButterKnife.bind(this);

  //      MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.mapa);
//        mapFragment.getMapAsync(LevantarCoordenadas.this);
        mapView.onCreate(savedInstanceState);
        mapView.onResume();
        mapView.getMapAsync(this);


        principalTelefono.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
        secundarioTelefono.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
        String[] girosArray = {"Moda, Belleza", "Salud, Fitness", "Dónde Comer", "Cafeterías / Postrerías", "Vida Nocturna",
                "Entretenimiento", "Servicios", "Hogar", "Regalos y novedades", "Tecnología", "Tiendas departamentales", "Miscelaneas y Autoservicios", "Market", "Truck Market"};

        String[] estacionamiento = {"De la Plaza", "Frontal", "Techado", "No tiene"};

        objectListaHorarios = new ArrayList<>();
        diasMarcados = new ArrayList<>();
        semanaList = new ArrayList<>();
        ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.spinner_item, R.id.titulo, girosArray);
        spinnerGiro.setAdapter(adapter);

        ArrayAdapter adapterEstacionamiento = new ArrayAdapter<String>(this, R.layout.spinner_item, R.id.titulo, estacionamiento);
        spinnerEstacionamiento.setAdapter(adapterEstacionamiento
        );

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;


        //boolean success = mapaGoogle.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.style_retro));
        this.googleMap.setPadding(10, 250, 10, 250);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        this.googleMap.setMyLocationEnabled(true);
        this.googleMap.getUiSettings().setMyLocationButtonEnabled(true);
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
            System.out.println("INSTANCIA: ");

            presionado =  new boolean[7];


            final Dialog dialog = new Dialog(activity);
            Calendar calendar;

            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.horario_dialog_layout);

            List<Horario> horarioList = new ArrayList<>();
            final AdapterGridView adapterGridView;

            horarioList.add(new Horario("D"));
            horarioList.add(new Horario("L"));
            horarioList.add(new Horario("M"));
            horarioList.add(new Horario("Mi"));
            horarioList.add(new Horario("J"));
            horarioList.add(new Horario("V"));
            horarioList.add(new Horario("S"));

            GridView gridView = (GridView) dialog.findViewById(R.id.gridHorarioDialog);
            final TimePicker timePickerInicio = (TimePicker) dialog.findViewById(R.id.picker1);
            final TimePicker timePickerCerrado = (TimePicker) dialog.findViewById(R.id.picker2);
            Button btnSaveHours = (Button) dialog.findViewById(R.id.saveHours);
            adapterGridView = new AdapterGridView(activity, horarioList);
            gridView.setAdapter(adapterGridView);
            gridView.setOnItemClickListener(listenerSelectedia);

            timePickerCerrado.setIs24HourView(true);
            timePickerInicio.setIs24HourView(true);

            btnSaveHours.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.M)
                @Override
                public void onClick(View view) {

                    String minutosI = String.valueOf(timePickerInicio.getMinute());
                    String minutosF = String.valueOf(timePickerCerrado.getMinute());
                    if(minutosI.equals("0") || minutosF.equals("0")){
                        minutosI = "00";
                        minutosF = "00";
                    }
                    if(Integer.parseInt(minutosI) == 1 ||Integer.parseInt(minutosI) == 2 || Integer.parseInt(minutosI) == 3 ||Integer.parseInt(minutosI) == 4 || Integer.parseInt(minutosI) == 5  || Integer.parseInt(minutosI) == 6  || Integer.parseInt(minutosI) == 7 || Integer.parseInt(minutosI) == 8  || Integer.parseInt(minutosI) == 9
                            ||Integer.parseInt(minutosF) == 1 ||Integer.parseInt(minutosF) == 2 || Integer.parseInt(minutosF) == 3 ||Integer.parseInt(minutosF) == 4 || Integer.parseInt(minutosF) == 5  || Integer.parseInt(minutosF) == 6  || Integer.parseInt(minutosF) == 7 || Integer.parseInt(minutosF) == 8  || Integer.parseInt(minutosF) == 9) {
                               minutosI = "0" + minutosI;
                              minutosF = "0" + minutosF;

                    }


                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        for (int i = 0; i < 7; i++) {
                            semanaList.add(new Semana(dias[i], presionado[i]));
                            System.out.println("Dias de la semana: " + semanaList.get(i).isEstatus());
                        }
                        objectListaHorarios.add(new ObjectListaHorario(semanaList, timePickerInicio.getHour() + ":" + minutosI, timePickerCerrado.getHour() + ":" + minutosF));

                        for (ObjectListaHorario objectListaHorario : objectListaHorarios) {
                            for (Semana semana : objectListaHorario.getDiasMarcados()) {
                                if (semana.isEstatus()) {
                                    System.out.println("DiaSemana: " + semana.getNombreDia() + " Apertura: " + objectListaHorario.getHoraApertura() + " Cerrado: "+objectListaHorario.getHoraCerrado());

                                }
                            }
                        }
                    }
                    adapterHorario = new AdapterHorario(objectListaHorarios, new AdapterHorario.OnItem() {
                        @Override
                        public void onItemClick(ObjectListaHorario objectListaHorario) {

                        }
                    });
                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    recyclerView.setAdapter(adapterHorario);
                    adapterHorario.notifyDataSetChanged();


                    dialog.dismiss();
                }
            });

            dialog.show();
        }
        //Lista  selecionada
        AdapterView.OnItemClickListener listenerSelectedia = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                TextView textView = (TextView) view.findViewById(R.id.textDiaSemana);
                if (presionado[position]) {
                    presionado[position] = false;
                    for (int i = 0; i < presionado.length; i++) {
                        if (!presionado[i]) {
                            presionado[i] = false;
                            //color gris
                            textView.setTextColor(Color.parseColor("#8b8b8b"));
                        }
                    }

                } else {  // cuando es primera vez true
                    presionado[position] = true;
                    for (int i = 0; i < presionado.length; i++) {
                        if (presionado[i]) {
                            //azul
                            presionado[i] = true;
                            textView.setTextColor(Color.parseColor("#20bbcc"));
                        }
                    }
                }
                for (boolean item : presionado) {
                    System.out.println("VALOR: "+item);

                }

            }


        };
    }

    public String armarHorario(String cadena){
         String[] diaCompleto = cadena.split("\\|");
        for (int i = 0; i < diaCompleto.length; i++){
                for(int j=0;j<diaCompleto.length-1;j++){
                    if(!diaCompleto[i].equals(diaCompleto[j+1])) {
                        if (diaCompleto[i].charAt(0) == diaCompleto[j+1].charAt(0)) {
                            // eliminamos su valor
                            System.out.println("Somo iguales: " + diaCompleto[i] + " ----->" + diaCompleto[j+1]);
                            String secondHorario = diaCompleto[j+1].substring(1,diaCompleto[j+1].length());
                            System.out.println("listona: "+diaCompleto[i]+secondHorario+"|");
                        } else {
                            System.out.println("No Somo iguales");
                        }
                    }

                }
        }
      return  "";
    }


}
