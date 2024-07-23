package com.example.magangremote.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.magangremote.api.ApiConfig
import com.example.magangremote.api.ApplyOptionsItem
import com.example.magangremote.api.LowonganDetailResponse
import com.example.magangremote.model.JobResponse
import com.example.magangremote.model.JobsResultsItem
import com.example.magangremote.ui.detail.DetailActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LowonganRepository {
    fun getAllListJobs(query: String, location: String, ltype: Int, callback: (Result<List<JobsResultsItem>>) -> Unit) {
        val client = ApiConfig.getApiService().getAllListJobs(query, location, ltype)
        client.enqueue(object : Callback<JobResponse> {
            override fun onResponse(call: Call<JobResponse>, response: Response<JobResponse>) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        callback(Result.success(responseBody.jobsResults))
                    } else {
                        callback(Result.failure(Exception("Response body is null")))
                    }
                } else {
                    callback(Result.failure(Exception("Response is not successful: ${response.message()}")))
                }
            }

            override fun onFailure(call: Call<JobResponse>, t: Throwable) {
                callback(Result.failure(t))
            }
        })
    }

    fun getListLowonganByKeyword(queryId: String, locationId: String, callback: (Result<List<JobsResultsItem>>) -> Unit) {
        val client = ApiConfig.getApiService().getLowonganByKeyword(queryId, locationId)
        client.enqueue(object : Callback<JobResponse> {
            override fun onResponse(call: Call<JobResponse>, response: Response<JobResponse>) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        callback(Result.success(responseBody.jobsResults))
                    } else {
                        callback(Result.failure(Exception("Response body is null")))
                    }
                } else {
                    callback(Result.failure(Exception("Response is not successful: ${response.message()}")))
                }
            }

            override fun onFailure(call: Call<JobResponse>, t: Throwable) {
                callback(Result.failure(t))
            }
        })
    }

    fun getLowonganDetail(id: String, callback: (Result<List<ApplyOptionsItem>>) -> Unit) {
        val client = ApiConfig.getApiService().getLowonganDetail(id)
        client.enqueue(object : Callback<LowonganDetailResponse> {
            override fun onResponse(
                call: Call<LowonganDetailResponse>,
                response: Response<LowonganDetailResponse>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()?.applyOptions
                    if (responseBody != null) {
                        callback(Result.success(responseBody))
                    }
                } else {
                    callback(Result.failure(Exception("Response is not successful: ${response.message()}")))
                }
            }

            override fun onFailure(call: Call<LowonganDetailResponse>, t: Throwable) {
                Log.e(DetailActivity.TAG, "onFailure: ${t.message}")
            }

        })
    }
}