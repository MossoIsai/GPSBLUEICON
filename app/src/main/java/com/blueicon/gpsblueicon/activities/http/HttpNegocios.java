package com.blueicon.gpsblueicon.activities.http;

import com.blueicon.gpsblueicon.activities.models.request.NegocioRequest;
import com.blueicon.gpsblueicon.activities.models.response.NegocioMain;
import com.blueicon.gpsblueicon.activities.models.response.SuccesResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by developer on 25/07/17.
 */

public interface HttpNegocios {

    /*@POST("NegocioTemporal/listado")
    @Headers("Accept: application/json")
    Call<SuccesResponse> sendNegocio(@Body NegocioRequest negocioRequest);*/


    @POST("NegocioTemporal/Listado")
    @Headers("Accept: application/json")
    Call<NegocioMain> obtenerNegocios();

}
