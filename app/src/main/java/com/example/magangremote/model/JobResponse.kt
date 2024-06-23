package com.example.magangremote.model

import com.google.gson.annotations.SerializedName

data class JobResponse(
	@field:SerializedName("jobs_results")
	val jobsResults: List<JobsResultsItem>,
)
data class JobHighlightsItem(

	@field:SerializedName("items")
	val items: List<String?>? = null,

	@field:SerializedName("title")
	val title: String? = null
)

data class DetectedExtensions(

	@field:SerializedName("schedule_type")
	val scheduleType: String? = null,

	@field:SerializedName("work_from_home")
	val workFromHome: Boolean? = null,

	@field:SerializedName("posted_at")
	val postedAt: String? = null,

	@field:SerializedName("salary")
	val salary: String? = null
)

data class RelatedLinksItem(

	@field:SerializedName("link")
	val link: String? = null,

	@field:SerializedName("text")
	val text: String? = null
)

data class JobsResultsItem(

	@field:SerializedName("job_highlights")
	val jobHighlights: List<JobHighlightsItem?>? = null,

	@field:SerializedName("thumbnail")
	val thumbnail: String,

	@field:SerializedName("extensions")
	val extensions: List<String>,

	@field:SerializedName("related_links")
	val relatedLinks: List<RelatedLinksItem?>? = null,

	@field:SerializedName("detected_extensions")
	val detectedExtensions: DetectedExtensions? = null,

	@field:SerializedName("job_id")
	val jobId: String,

	@field:SerializedName("company_name")
	val companyName: String,

	@field:SerializedName("description")
	val description: String,

	@field:SerializedName("location")
	val location: String,

	@field:SerializedName("title")
	val title: String,

	@field:SerializedName("via")
	val via: String
)
