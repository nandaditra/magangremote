package com.example.magangremote.ui.home

import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.magangremote.api.ApiConfig
import com.example.magangremote.model.JobResponse
import com.example.magangremote.model.JobsResultsItem
import com.example.magangremote.repository.LowonganRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel: ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _listLowongan = MutableLiveData<List<JobsResultsItem>>()
    val listLowongan:LiveData<List<JobsResultsItem>> = _listLowongan

    private val repository = LowonganRepository()
    fun getListLowongan(query: String) {
        _isLoading.value = true
        repository.getAllListJobs(query){ result ->
            _isLoading.value = false
            result.onSuccess { results ->
                _listLowongan.postValue(results)
            }.onFailure { throwable ->
                Log.d(HomeActivity.TAG, "onFailure : ${throwable.message}")
            }
        }
    }

    fun getListLowonganByKeyword(query: String, locationId: String) {
        _isLoading.value = true
        val queryId = "$query remote intern"
        repository.getListLowonganByKeyword(queryId, locationId) { result ->
            _isLoading.value = false
            result.onSuccess { jobsResults ->
                _listLowongan.postValue(jobsResults)
            }.onFailure { throwable ->
                Log.e(HomeActivity.TAG, "onFailure: ${throwable.message}")
            }
        }
    }
}