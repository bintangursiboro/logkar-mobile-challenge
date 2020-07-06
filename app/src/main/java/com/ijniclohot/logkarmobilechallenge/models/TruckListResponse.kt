package com.ijniclohot.logkarmobilechallenge.models

import com.google.gson.annotations.SerializedName

class TruckListResponse(
    @SerializedName("status") val status: String,
    @SerializedName("data") val truckList: List<TruckModel>,
    @SerializedName("code") val code: Int
) {
}

class TruckModel(
    @SerializedName("truck_type_id") val truckTypeId: Int,
    @SerializedName("truck_type") val truckType: String,
    @SerializedName("unit") val unitList: List<String>
)
