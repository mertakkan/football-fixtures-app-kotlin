package com.example.finalproject

import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("fixtures")
    fun getTodayMatches(
        @Query("date") date: String,
        @Query("league") leagueId: Int,
        @Query("season") season: Int,
        @Query("status") status: String = "FT" // Not Started
    ): retrofit2.Call<FixtureResponse>
}

