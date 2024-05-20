package com.lirioams.list.data.remote

import com.lirioams.list.data.remote.model.ListDetailDto
import com.lirioams.list.data.remote.model.ListDto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Url

interface ListApi {
    //Endpoints

    //https://last-airbender-api.fly.dev/api/v1/characters?perPage=497
    @GET
    fun getElements(
        @Url url: String
    ): Call<List<ListDto>>

    //https://last-airbender-api.fly.dev/api/v1/characters/5cf5679a915ecad153ab68cb
    @GET("api/v1/characters/{id}")
    fun getElementDetail(
        @Path("id") id: String,
    ): Call<ListDetailDto>

}