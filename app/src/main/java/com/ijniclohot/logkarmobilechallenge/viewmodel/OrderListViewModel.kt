package com.ijniclohot.logkarmobilechallenge.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ijniclohot.logkarmobilechallenge.models.OrderModel
import com.ijniclohot.logkarmobilechallenge.repository.ApiRepository

class OrderListViewModel : ViewModel() {
    private val repository = ApiRepository.getInstance()

    fun getOrderLD(): LiveData<List<OrderModel>> {
        return repository.orders
    }

    fun getOrder() {
        repository.getOrderList()
    }

}