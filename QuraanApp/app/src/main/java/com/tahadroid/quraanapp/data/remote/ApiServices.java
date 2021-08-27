package com.tahadroid.quraanapp.data.remote;




import com.tahadroid.quraanapp.pojo.RadioResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiServices {
    @GET("radio_arabic.json")
    Call<RadioResponse> getRadios() ;
}
