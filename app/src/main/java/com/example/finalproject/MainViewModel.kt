package com.example.finalproject

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.Call
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date
import java.util.Locale

class MainViewModel : ViewModel() {

    private val _fixtures = MutableLiveData<List<Fixture>>()
    val fixtures: LiveData<List<Fixture>> = _fixtures

    @RequiresApi(Build.VERSION_CODES.O)
    fun getTodayMatches(leagueId: Int, season: Int) {
        viewModelScope.launch {
            try {
                val todayDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
                val toDate = LocalDate.now().plusWeeks(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                RetrofitClient.apiService.getTodayMatches(todayDate, toDate, leagueId, season).enqueue(object : retrofit2.Callback<FixtureResponse> {
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
