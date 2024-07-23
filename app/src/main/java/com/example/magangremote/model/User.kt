package com.example.magangremote.model

data class User(
    var uid: String? = "",
    var name:String? = "",
    var email: String? = "",
    var handphoneNumber: String? = "",
    var imageUrl: String,
    var interest: String? = ""
)