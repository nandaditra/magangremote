package com.example.magangremote.model

data class User (
    var uid: String? = "",
    var name :String? = "",
    var email: String? = "",
    var password: String? = "",
    var handphoneNumber: String? = "",
    var imageUri: String? = "",
    var interest: List<String> = listOf()
)