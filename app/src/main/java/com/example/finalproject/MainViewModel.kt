package com.example.finalproject

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.Call
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainViewModel : ViewModel() {

    private val _fixtures = MutableLiveData<List<Fixture>>()
    val fixtures: LiveData<List<Fixture>> = _fixtures

    fun getTodayMatches(leagueId: Int, season: Int) {
        viewModelScope.launch {
            try {
                val todayDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
                RetrofitClient.apiService.getTodayMatches(todayDate, leagueId, season).enqueue(object : retrofit2.Callback<FixtureResponse> {
                    override fun onResponse(call: Call<FixtureResponse>, response: retrofit2.Response<FixtureResponse>) {
                        if (response.isSuccessful) {
                            _fixtures.postValue(response.body()?.response ?: emptyList())
                        }
                    }

                    override fun onFailure(call: Call<FixtureResponse>, t: Throwable) {
                        // failure
                    }
                })
            } catch (e: Exception) {
                //  exception
            }
        }
    }

}
