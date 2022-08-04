package com.example.assignmentbalusonawane.data.remote

import com.example.assignmentbalusonawane.BuildConfig
import com.example.assignmentbalusonawane.model.NetworkResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {

    @GET("categories")
    suspend fun getCategories(
        @Header("Authorization") token: String = "Bearer ${BuildConfig.TOKEN}",
        @Query("count") count: Int
    ) : Response<NetworkResponse>

    @GET("categories/{identifier}/iconsets")
    suspend fun getIconSets(
        @Header("Authorization") token: String = "Bearer ${BuildConfig.TOKEN}",
        @Path("identifier") identifier: String,
        @Query("count") count: Int
    ) : Response<NetworkResponse>

    @GET("iconsets/{iconset_id}/icons")
    suspend fun getIcons(
        @Header("Authorization") token: String = "Bearer ${BuildConfig.TOKEN}",
        @Path("iconset_id") iconset_id: Int,
        @Query("count") count: Int
    ) : Response<NetworkResponse>


    @GET("icons/search")
    suspend fun searchIcons(
        @Header("Authorization") token: String = "Bearer ${BuildConfig.TOKEN}",
        @Query("query") query: String,
        @Query("count") count: Int
    ) : Response<NetworkResponse>
}