package com.tahadroid.quraanapp.data.remote;

import retrofit2.Retrofit;

public class ApiManager {
    private static Retrofit retrofit = null;

    static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl("http://api.mp3quran.net/radios/")
                    .build();
            return retrofit;
        } else
            return retrofit;
    }


}
