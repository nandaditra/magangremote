package com.example.magangremote.api

import com.example.magangremote.model.JobResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

const val apiKey="6a8582bb1c95e61b64be22a7da4930c49f0d120999bcd22b28b07c3b8ec24057"
interface ApiService {
    @GET("/search?engine=google_jobs&api_key=$apiKey")
    fun getAllListJobs(
        @Query("q") q: String,
    ): Call<JobResponse>

    @GET("/search?engine=google_jobs&api_key=$apiKey")
    fun getLowonganByKeyword(
        @Query("q") q:String,
        @Query("location") location: String,
    ): Call<JobResponse>

    @GET("/search.json?engine=google_jobs_listing&api_key=${apiKey}")
    fun getLowonganDetail(
        @Query("q") q:String,
    ): Call<LowonganDetailResponse>
}