package com.example.ezobook.data.remote

import com.example.ezobook.data.model.AuthorsDTO
import retrofit2.http.GET
import retrofit2.http.Query

interface ServiceApi {

    @GET("/v2/list")
    suspend fun getAuthor(@Query("limit") limit: Int? = 10): AuthorsDTO
}