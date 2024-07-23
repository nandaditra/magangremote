package com.example.magangremote.api

import com.google.gson.annotations.SerializedName

data class LowonganDetailResponse(
	@field:SerializedName("apply_options")
	val applyOptions: List<ApplyOptionsItem>,
)
data class ApplyOptionsItem(

	@field:SerializedName("link")
	val link: String? = null,

	@field:SerializedName("title")
	val title: String? = null
)
