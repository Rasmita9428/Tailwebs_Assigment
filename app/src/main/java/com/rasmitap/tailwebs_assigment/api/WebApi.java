package com.rasmitap.tailwebs_assigment.api;

import com.rasmitap.tailwebs_assigment.model.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;


public interface WebApi {

    public static final String BASE_URL = "http://203.77.197.29:8013/api/";

    String UserName = "UserName";
    String Password = "Password";
    String imei = "imei";


    @POST(BASE_URL + "LoginAPI/AuthenticateMobile")
    Call<LoginResponse> callLoginApi(@Field("UserName") String UserName,
                                     @Field("Password") String Password,
                                     @Field("imei") String imei);


}
