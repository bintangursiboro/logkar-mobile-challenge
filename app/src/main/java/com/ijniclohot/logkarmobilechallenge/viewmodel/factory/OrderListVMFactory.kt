package com.ijniclohot.logkarmobilechallenge.viewmodel.factory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ijniclohot.logkarmobilechallenge.viewmodel.MainViewModel
import com.ijniclohot.logkarmobilechallenge.viewmodel.OrderListViewModel

class OrderListVMFactory(private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(OrderListViewModel::class.java)) {
            return OrderListViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}