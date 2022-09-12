package com.example.demoappiness;


import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetroClient {
    private static final String BASE_URL = "http://demo2143341.mockable.io/";






    private RegisterApi registerAPI;

    private static Retrofit getRetrofitInstance() {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public RegisterApi getApiService() {
        if (registerAPI == null)
            registerAPI = getRetrofitInstance().create(RegisterApi.class);

        return registerAPI;
    }

}
