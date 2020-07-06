package com.ijniclohot.logkarmobilechallenge.network

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ijniclohot.logkarmobilechallenge.models.CapacityResponse
import com.ijniclohot.logkarmobilechallenge.models.CommodityModel
import com.ijniclohot.logkarmobilechallenge.models.OrderModel
import com.ijniclohot.logkarmobilechallenge.models.TruckModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import java.lang.Exception

class ApiClient {
    private val _district: MutableLiveData<List<String>> by lazy {
        MutableLiveData<List<String>>()
    }
    val district: LiveData<List<String>>
        get() = _district

    private val _trucks: MutableLiveData<List<TruckModel>> by lazy {
        MutableLiveData<List<TruckModel>>()
    }
    val trucks: LiveData<List<TruckModel>>
        get() = _trucks

    private val _capacities: MutableLiveData<List<Int>> by lazy {
        MutableLiveData<List<Int>>()
    }
    val capacities: LiveData<List<Int>>
        get() = _capacities

    private val _commodities: MutableLiveData<List<CommodityModel>> by lazy {
        MutableLiveData<List<CommodityModel>>()
    }
    val commodities: LiveData<List<CommodityModel>>
        get() = _commodities

    private val _orders: MutableLiveData<List<OrderModel>> by lazy {
        MutableLiveData<List<OrderModel>>()
    }
    val orders: LiveData<List<OrderModel>>
        get() = _orders

    private val coroutineScope = CoroutineScope(IO)

    companion object {
        private var instance: ApiClient? = null

        fun getInstance(): ApiClient {
            if (instance == null) {
                instance = ApiClient()
            }
            return instance!!
        }
    }

    private val apiInterface by lazy {
        NetworkConnection.apiInterface
    }

    fun getDistrict(name: String) {
        coroutineScope.launch {
            try {
                val districtResponse = apiInterface.getDistrictList(name)
                val districtList = ArrayList<String>()
                if (districtResponse.code == 200) {
                    districtResponse.kecamatanList.forEach { district ->
                        districtList.add(district.districtName)
                    }
                    _district.postValue(districtList)
                }
            } catch (e: Exception) {
                _district.postValue(ArrayList())
            }
        }
    }

    fun getTruckList() {
        coroutineScope.launch {
            try {
                val truckResponse = apiInterface.getTruckList()
                if (truckResponse.code == 200) {
                    _trucks.postValue(truckResponse.truckList)
                } else {
                    _trucks.postValue(ArrayList())
                }
            } catch (e: Exception) {
                _trucks.postValue(ArrayList())
            }
        }
    }

    fun getCapacityList(id: Int) {
        coroutineScope.launch {
            try {
                val capacityResponse = apiInterface.getTruckCapacity(id.toString())
                if (capacityResponse.code == 200) {
                    _capacities.postValue(capacityResponse.capacityList)
                } else {
                    _capacities.postValue(ArrayList())
                }
            } catch (e: Exception) {
                _capacities.postValue(ArrayList())
            }
        }
    }

    fun getCommodityList(id: Int) {
        coroutineScope.launch {
            try {
                val commodityResponse = apiInterface.getTruckCommodity(id.toString())
                if (commodityResponse.status == "OK") {
                    _commodities.postValue(commodityResponse.commodityList)
                } else {
                    _commodities.postValue(ArrayList())
                }
            } catch (e: Exception) {
                _commodities.postValue(ArrayList())
            }
        }
    }

    fun getOrderList() {
        coroutineScope.launch {
            try {
                val orderResponse = apiInterface.getOrderList()
                if (orderResponse.code == 200) {
                    _orders.postValue(orderResponse.orderList)
                } else {
                    _orders.postValue(ArrayList())
                }
            } catch (e: Exception) {
                _orders.postValue(ArrayList())
            }
        }
    }

}