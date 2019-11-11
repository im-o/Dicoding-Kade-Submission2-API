package com.stimednp.kadesubmission2.api

import com.stimednp.kadesubmission2.model.ResponseTsdb
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by rivaldy on 11/10/2019.
 */

interface IServiceTsdb {
    @GET("api/v1/json/1/all_leagues.php")
    fun getListLeagues(): Call<Response<ResponseTsdb>>

    @GET("api/v1/json/1/lookupleague.php?")
    fun getDetailById(@Query("id") id: Int?): Call<Response<ResponseTsdb>>
}