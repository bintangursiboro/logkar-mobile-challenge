package com.ijniclohot.logkarmobilechallenge.repository

import androidx.lifecycle.LiveData
import com.ijniclohot.logkarmobilechallenge.network.ApiClient

class ApiRepository {
    private val apiClient = ApiClient.getInstance()

    val trucks = apiClient.trucks
    val capacities = apiClient.capacities
    val commodities = apiClient.commodities
    val orders = apiClient.orders

    companion object {
        private var instance: ApiRepository? = null

        fun getInstance(): ApiRepository {
            if (instance == null) {
                instance = ApiRepository()
            }
            return instance!!
        }
    }


    fun getDistrict(name: String) {
        apiClient.getDistrict(name)
    }

    fun getTruckList() {
        apiClient.getTruckList()
    }

    fun getTruckCapacities(id: Int) {
        apiClient.getCapacityList(id)
    }

    fun getTruckCommodities(id: Int) {
        apiClient.getCommodityList(id)
    }

    fun getOrderList() {
        apiClient.getOrderList()
    }

    fun getDistrictLD(): LiveData<List<String>> {
        return apiClient.district
    }

}