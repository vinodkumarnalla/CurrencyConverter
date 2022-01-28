package com.android.vinod.data.mapper

interface BaseMapper<DATA, MODEL> {
    fun transform(data: DATA): MODEL
}