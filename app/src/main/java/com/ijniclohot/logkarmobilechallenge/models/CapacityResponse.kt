package com.ijniclohot.logkarmobilechallenge.models

import com.google.gson.annotations.SerializedName

data class CapacityResponse(
    @SerializedName("status") val status: String,
    @SerializedName("data") val capacityList: List<Int>,
    @SerializedName("code") val code: Int
) {}