package com.example.mystoryapp.api.retrofit
import com.example.mystoryapp.api.response.LoginResponse
import com.example.mystoryapp.api.response.StatusResponse
import com.example.mystoryapp.api.response.StoryResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface ApiService {
    @FormUrlEncoded
    @POST("login")
    fun postLogin(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<LoginResponse>

    @FormUrlEncoded
    @POST("register")
    fun postRegister(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<StatusResponse>

    @GET("stories?location=1")
    fun getStories(
        @Header("Authorization") token: String
    ): Call<StoryResponse>

    @Multipart
    @POST("stories")
    fun postStory(
        @Header("Authorization") token: String,
        @Part file: MultipartBody.Part,
        @Part("description") description: RequestBody,
        @Part("lat") lat: Double,
        @Part("lon") lng: Double
    ): Call<StatusResponse>

    @GET("stories")
    suspend fun getStoryList(
        @Query("page") page: Int,
        @Query("size") size: Int,
        @Header("Authorization") token: String
    ): StoryResponse
}