package com.blueicon.gpsblueicon.activities.http;

import com.blueicon.gpsblueicon.activities.models.request.NegocioRequest;
import com.blueicon.gpsblueicon.activities.models.response.SuccesResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by developer on 25/07/17.
 */

public interface HttpNegocios {

    @POST("Negocio/Temporal")
    @Headers("Accept: application/json")
    Call<SuccesResponse> sendaNegocio(@Body NegocioRequest negocioRequest);


}
