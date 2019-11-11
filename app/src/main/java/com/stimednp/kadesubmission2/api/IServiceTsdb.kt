package com.stimednp.kadesubmission2.api

import com.stimednp.kadesubmission2.model.ResponseLeagues
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by rivaldy on 11/10/2019.
 */

interface IServiceTsdb {
    //list liga
    @GET("api/v1/json/1/all_leagues.php")
    fun getListLeagues(): Deferred<Response<ResponseLeagues>>

    //detail liga
    @GET("api/v1/json/1/lookupleague.php?")
    fun getDetailById(@Query("id") id: Int?): Deferred<Response<ResponseLeagues>>

    //next match
    @GET("api/v1/json/1/eventsnextleague.php?")
    fun getNextMatch(@Query("id") id: Int?): Deferred<Response<ResponseLeagues>>

    //previous match
    @GET("api/v1/json/1/eventspastleague.php?")
    fun getPrevMatch(@Query("id") id: Int?): Deferred<Response<ResponseLeagues>>

    //detail event / pertandingan
    @GET("api/v1/json/1/lookupevent.php?")
    fun getDetailEvent(@Query("id") id: Int?): Deferred<Response<ResponseLeagues>>

    //search event / pertandingan
    @GET("api/v1/json/1/searchevents.php?")
    fun getSearchEvent(@Query("e") query: String?): Deferred<Response<ResponseLeagues>>
}