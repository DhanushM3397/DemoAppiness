package com.example.demoappiness;




import com.example.demoappiness.modelclasss.ModelClass;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RegisterApi {



    @GET("getMyList")
    Call<ModelClass> getleaddata();




}
