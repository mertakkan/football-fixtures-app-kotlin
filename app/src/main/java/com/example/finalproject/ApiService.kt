package com.example.finalproject

import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("fixtures")
    fun getTodayMatches(
        @Query("from") todayDate: String,
        @Query("to") toDate: String,
        @Query("league") leagueId: Int,
        @Query("season") season: Int,
        //@Query("status") status: String = "NS" // Not Started
    ): retrofit2.Call<FixtureResponse>
}

