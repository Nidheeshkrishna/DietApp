package com.example.dietapp.ui.Connection;

import com.example.dietapp.ui.Model.UserRegisterModel;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApInterface {


    @FormUrlEncoded
    @POST("user_reg.php")
    Call<ResponseBody>usrregister(@Field("na") String name,
                                  @Field("pass")String pword,
                                  @Field("email")String email,
                                  @Field("ph")String phone,@Field("ph")String address,@Field("ph")String district);

    @FormUrlEncoded
    @POST("login.php")
    Call<ResponseBody> login(@Field("user")String username,@Field("pass") String password);

    @FormUrlEncoded
    @POST("add_trainee.php")
    Call<ResponseBody> Add_Service(@Field("gid")String uid,@Field("name")String name,@Field("center") String center,@Field("ph") String phone,@Field("email") String email,@Field("pass") String pass);

    @FormUrlEncoded
    @POST("view_user_pro.php")
    Call<ResponseBody> profile(@Field("id")String uid);

    @GET("view_bank.php")
    Call<ResponseBody> getbanks();

    @FormUrlEncoded
    @POST("link_acc.php")
    Call<ResponseBody> AddBank(@Field("user")String uid,@Field("hname") String name,@Field("branch") String branch,@Field("accno") String acc,@Field("ifsc") String ifsc,@Field("bid") String types);

    @FormUrlEncoded
    @POST("view_gym_pro.php")
    Call<ResponseBody> gymprofile(@Field("gid")String uid);

    @FormUrlEncoded
    @POST("view_trainee.php")
    Call<ResponseBody> viewtrainers(@Field("id")String uid);

    @GET("view_shop.php")
    Call<ResponseBody> viewshop();

    @FormUrlEncoded
    @POST("view_bank_stat.php")
    Call<ResponseBody> Bankstatus(@Field("id")String uid);

    @FormUrlEncoded
    @POST("view_products.php")
    Call<ResponseBody> viewproducts(@Field("sid")String shopid);

    @FormUrlEncoded
    @POST("view_orders.php")
    Call<ResponseBody> viewoders(@Field("gid")String uid);


    @FormUrlEncoded
    @POST("view_trainee_user.php")
    Call<ResponseBody> viewtrainer();

    @GET("view_trainee_user.php")
    Call<ResponseBody> viewtrainee();

    //@FormUrlEncoded
    @GET("get_videos.php")
    Call<ResponseBody> viewvideos();

    @GET("view_weight.php")
    Call<ResponseBody> view_weight();

    @FormUrlEncoded
    @POST("send_request.php")
    Call<ResponseBody> user_request(@Field("user")String uid,@Field("tid") String trainerid);

    @GET("view_weight.php")
    Call<ResponseBody> getweight();

    @FormUrlEncoded
    @POST("view_food.php")
    Call<ResponseBody> getdetailss(@Field("typeid")String tids);

}