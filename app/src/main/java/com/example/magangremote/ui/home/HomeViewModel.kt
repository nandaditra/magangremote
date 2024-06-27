package com.example.magangremote.ui.home

import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.magangremote.api.ApiConfig
import com.example.magangremote.model.JobResponse
import com.example.magangremote.model.JobsResultsItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel: ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _listLowongan = MutableLiveData<List<JobsResultsItem>>()
    val listLowongan:LiveData<List<JobsResultsItem>> = _listLowongan
    fun getListLowongan(query:String, location:String, ltype:Int) {
        _isLoading.value = true;
        val client = ApiConfig.getApiService().getAllListJobs(query, location, ltype)
        client.enqueue(object: Callback<JobResponse> {
            override fun onResponse(call: Call<JobResponse>, response: Response<JobResponse>) {
                _isLoading.value = false;
                if(response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _listLowongan.postValue(responseBody.jobsResults)
                    }
                } else {
                    Log.e(HomeActivity.TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<JobResponse>, t: Throwable) {
                _isLoading.value = false;
                Log.e(HomeActivity.TAG, "onFailure: ${t.message}")
            }
        })
    }

    fun getListLowonganByKeyword(query:String, locationId:String) {
        _isLoading.value = true;
        val queryId = "$query remote intern"
        val client = ApiConfig.getApiService().getLowonganByKeyword(queryId, locationId)
        client.enqueue(object: Callback<JobResponse> {
            override fun onResponse(call: Call<JobResponse>, response: Response<JobResponse>) {
                _isLoading.value = false;
                if(response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _listLowongan.postValue(responseBody.jobsResults)
                    }
                } else {
                    _isLoading.value = false;
                    Log.e(HomeActivity.TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<JobResponse>, t: Throwable) {
                _isLoading.value = false;
                Log.e(HomeActivity.TAG, "onFailure: ${t.message}")
            }
        })
    }
}