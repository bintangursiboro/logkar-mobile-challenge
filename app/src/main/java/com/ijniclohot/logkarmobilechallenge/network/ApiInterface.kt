package com.ijniclohot.logkarmobilechallenge.network

import com.ijniclohot.logkarmobilechallenge.models.*
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET("truck-type/list")
    suspend fun getTruckList() : TruckListResponse

    @GET("truck-capacity/list")
    suspend fun getTruckCapacity(@Query("id") id : String) : CapacityResponse

    @GET("commodity/list")
    suspend fun getTruckCommodity(@Query("id") idd : String) : CommodityResponse

    @GET("district/list")
    suspend fun getDistrictList(@Query("keyword") keyword : String) : KecamatanResponse

    @GET("order/list")
    suspend fun getOrderList() : OrderResponse
}