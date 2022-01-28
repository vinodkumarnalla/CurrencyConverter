package com.android.vinod.data.entities

class ResponseData {
    var success = false
    var terms: String? = null
    var privacy: String? = null
    var timestamp = 0
    var source: String? = null
    var quotes: Map<String, Double>? = null
}