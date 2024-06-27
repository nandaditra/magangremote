package com.example.magangremote.api

import com.google.gson.annotations.SerializedName

data class LowonganDetailResponse(

	@field:SerializedName("search_metadata")
	val searchMetadata: SearchMetadata? = null,

	@field:SerializedName("search_information")
	val searchInformation: SearchInformation? = null,

	@field:SerializedName("error")
	val error: String? = null,

	@field:SerializedName("search_parameters")
	val searchParameters: SearchParameters? = null
)

data class SearchMetadata(

	@field:SerializedName("raw_html_file")
	val rawHtmlFile: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("processed_at")
	val processedAt: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("prettify_html_file")
	val prettifyHtmlFile: String? = null,

	@field:SerializedName("total_time_taken")
	val totalTimeTaken: Any? = null,

	@field:SerializedName("json_endpoint")
	val jsonEndpoint: String? = null,

	@field:SerializedName("status")
	val status: String? = null,

	@field:SerializedName("google_jobs_listing_url")
	val googleJobsListingUrl: String? = null
)

data class SearchParameters(

	@field:SerializedName("q")
	val q: String? = null,

	@field:SerializedName("engine")
	val engine: String? = null
)

data class SearchInformation(

	@field:SerializedName("jobs_listing_state")
	val jobsListingState: String? = null
)
