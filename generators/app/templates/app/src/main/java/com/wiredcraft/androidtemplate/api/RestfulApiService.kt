package com.wiredcraft.androidtemplate.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface RestfulApiService {
    @POST("app-middleware/api/guests/login")
    suspend fun logIn(@Body data: String): Response<String>

}