package com.ijniclohot.logkarmobilechallenge.models

import com.google.gson.annotations.SerializedName

class CommodityResponse(
    @SerializedName("status") val status: String,
    @SerializedName("data") val commodityList: List<CommodityModel>
)

class CommodityModel(
    @SerializedName("commod_id") val commodityId: Int,
    @SerializedName("commod_category") val commodityCategory: String
)