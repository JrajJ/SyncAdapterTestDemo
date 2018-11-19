package com.sparklinktech.stech.syncadaptertestdemo;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface CategoryInterface {



    @FormUrlEncoded
    @POST("woffers/json_get_logindetails.php")
    Call<Login> getLoginDetails(@Field("uid") String uid);





}