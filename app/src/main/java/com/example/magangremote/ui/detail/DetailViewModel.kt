package com.example.magangremote.ui.detail

import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.magangremote.api.ApiConfig
import com.example.magangremote.api.ApplyOptionsItem
import com.example.magangremote.api.LowonganDetailResponse
import com.example.magangremote.model.JobsResultsItem
import com.example.magangremote.repository.LowonganRepository
import com.example.magangremote.ui.home.HomeActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel:ViewModel() {
    private val _lowongan = MutableLiveData<List<ApplyOptionsItem>?>()
    val lowongan: LiveData<List<ApplyOptionsItem>?> = _lowongan

    private val repository = LowonganRepository()
    fun getLowonganDetail(id: String) {
        repository.getLowonganDetail(id){ result ->
            result.onSuccess { jobsResults ->
                _lowongan.postValue(jobsResults)
            }.onFailure { throwable ->
                Log.e(HomeActivity.TAG, "onFailure: ${throwable.message}")
            }
        }
    }
}