package com.ijniclohot.logkarmobilechallenge.models

import com.google.gson.annotations.SerializedName

class KecamatanResponse(
    @SerializedName("status") val status: String,
    @SerializedName("data") val kecamatanList: List<KecamatanModel>,
    @SerializedName("code") val code: Int
)

class KecamatanModel(
    @SerializedName("city_id") val cityId: Int,
    @SerializedName("city_name") val cityName: String,
    @SerializedName("district_id") val districtId: Int,
    @SerializedName("district_name") val districtName: String
)