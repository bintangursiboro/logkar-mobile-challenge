package com.ijniclohot.logkarmobilechallenge.models

import androidx.room.Entity
import com.google.gson.annotations.SerializedName

class OrderResponse(
    @SerializedName("status") val status: String,
    @SerializedName("code") val code: Int,
    @SerializedName("data") val orderList: List<OrderModel>
)

class OrderModel(
    @SerializedName("origin") val origin: OriginModel,
    @SerializedName("destination") val destination: DestinationModel,
    @SerializedName("transporter") val transporter: TransporterModel
)

class OriginModel(
    @SerializedName("user") val user: String,
    @SerializedName("phone") val phone: String,
    @SerializedName("district") val district: String
)

class DestinationModel(
    @SerializedName("user") val user: String,
    @SerializedName("phone") val phone: String,
    @SerializedName("district") val district: String
)

class TransporterModel(
    @SerializedName("truck_type") val truckType: String,
    @SerializedName("capacity") val capacity: Int,
    @SerializedName("unit") val unit: String,
    @SerializedName("ref_no") val refNumber: List<String>,
    @SerializedName("start_delivery_date") val startDeliveryDate: String,
    @SerializedName("note") val note: String,
    @SerializedName("commodity_name") val commodityName: String,
    @SerializedName("qty_order") val quantityOrder: Int
)