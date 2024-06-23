package com.example.magangremote.api

import com.example.magangremote.model.JobResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

const val apiKey="3fa1b0e9fe7d96b88150aade5e2c4cc37bdf79cb1431e4545f9d31624c7f3183"
interface ApiService {
    @GET("/search?engine=google_jobs&api_key=$apiKey")
    fun getAllListJobs(
        @Query("q") q: String,
        @Query("location") location: String,
        @Query("ltype") ltype: Int,
    ): Call<JobResponse>
    fun getLowonganByKeyword()
    fun getLowonganDetail()
}