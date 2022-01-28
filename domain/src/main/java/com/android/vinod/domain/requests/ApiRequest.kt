package com.android.vinod.domain.requests

 data class ApiRequest (
    val enteredNumber: Double,
    val state: State,
    val source: String,
)

enum class State{
    NEW,
    UPDATE,
    UPDATED
}