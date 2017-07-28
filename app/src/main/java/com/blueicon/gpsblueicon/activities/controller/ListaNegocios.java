package com.blueicon.gpsblueicon.activities.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.blueicon.gpsblueicon.R;
import com.blueicon.gpsblueicon.activities.adapters.AdapterHorario;
import com.blueicon.gpsblueicon.activities.adapters.AdapterNegocio;
import com.blueicon.gpsblueicon.activities.http.ClienteRetrofit;
import com.blueicon.gpsblueicon.activities.http.HttpNegocios;
import com.blueicon.gpsblueicon.activities.models.response.NegocioMain;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by developer on 24/07/17.
 */

public class ListaNegocios extends AppCompatActivity {

    @BindView(R.id.listaNegocios)
    ListView listView;
    AdapterNegocio adapterNegocio;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lista_negocios_layout);
        ButterKnife.bind(this);
        obtenerNegocios();
    }

    public void obtenerNegocios() {

        HttpNegocios httpNegocios = ClienteRetrofit.getSharedInstance().create(HttpNegocios.class);
        Call<NegocioMain> negocioMainClass = httpNegocios.obtenerNegocios();
        negocioMainClass.enqueue(new Callback<NegocioMain>() {

            @Override
            public void onResponse(Call<NegocioMain> call, final Response<NegocioMain> response) {
                if (response.body().isEstatus()) {
                    System.out.println("Mensaje de Textio " + response.body().isEstatus());
                    adapterNegocio = new AdapterNegocio(getApplicationContext(), response.body().getNegocios());
                    listView.setAdapter(adapterNegocio);

                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            System.out.println("ELEJISTE LA POSICION: " + response.body().getNegocios().get(i).getNegocioId());
                            Bundle bundle = new Bundle();
                            int negocioId = response.body().getNegocios().get(i).getNegocioId();
                            Intent intent = new Intent(getApplicationContext(), LevantarCoordenadas.class);
                            bundle.putInt("ID_NEGOCIO", negocioId);
                            intent.putExtras(bundle);
                            startActivity(intent);

                        }
                    });
                } else {
                    System.out.println("Mensaje de texto 2: " + response.body().getMensaje());
                }
            }

            @Override
            public void onFailure(Call<NegocioMain> call, Throwable t) {
                System.out.println("ERROR: " + t.getMessage() + " MENSAJE  DE ERROR: ");
            }
        });

    }
}
