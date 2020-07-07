package com.ijniclohot.logkarmobilechallenge.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ijniclohot.logkarmobilechallenge.models.OrderModel
import com.ijniclohot.logkarmobilechallenge.repository.ApiRepository
import com.ijniclohot.logkarmobilechallenge.repository.LocalRepository

class OrderListViewModel(application: Application) : ViewModel() {
    private val repository = ApiRepository.getInstance()
    private val localRepository = LocalRepository.getInstance(application)

    fun getOrderLD(): LiveData<List<OrderModel>> {
        return repository.orders
    }

    fun getOrder() {
        repository.getOrderList()
    }

    fun getOrderLocalLD(): LiveData<List<OrderModel>> {
        return localRepository.getOrders()
    }

    fun getOrdersLocal(){
        localRepository.getOrders()
    }

}