package com.example.magangremote.model

import java.sql.Timestamp

data class Notifikasi(
    var id: String? = "",
    var jobId :String? = "",
    var title: String? = "",
    var message: String? = "",
    var timestamp: Timestamp?,
)