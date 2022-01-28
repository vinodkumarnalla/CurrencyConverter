package com.android.vinod.data

import com.android.vinod.data.entities.ResponseData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface APIInterface {
    @GET(APIConstants.END_POINT)
    suspend fun getData(
        @Query("access_key") path: String,
    ): Response<ResponseData>

}