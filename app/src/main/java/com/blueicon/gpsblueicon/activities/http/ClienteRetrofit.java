package com.blueicon.gpsblueicon.activities.http;

import com.blueicon.gpsblueicon.activities.tools.Constantes;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by developer on 24/07/17.
 */

public class ClienteRetrofit {

    private static Retrofit mretrofit = null;


    public static Retrofit getSharedInstance() {

        if (mretrofit == null) {
            //Agregar encabezados en las solitudues http
            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(30, TimeUnit.SECONDS) //3 segundos para realizar la conexion
                    .readTimeout(30, TimeUnit.SECONDS) //3 segundos para realizar la conexion
                    .addInterceptor(httpLoggingInterceptor)
                    .build();

            mretrofit = new Retrofit.Builder()
                    .baseUrl(Constantes.BASE_URL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return mretrofit;

    }
}
