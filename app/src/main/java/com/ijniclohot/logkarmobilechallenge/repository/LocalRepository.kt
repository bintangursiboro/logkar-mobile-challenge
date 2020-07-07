package com.ijniclohot.logkarmobilechallenge.repository

import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveData
import com.ijniclohot.logkarmobilechallenge.dao.OrderDao
import com.ijniclohot.logkarmobilechallenge.db.ApplicationDatabase
import com.ijniclohot.logkarmobilechallenge.models.OrderModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class LocalRepository(private val application: Application) {
    private val orderDao: OrderDao
    private val orders: LiveData<List<OrderModel>>

    init {
        val database = ApplicationDatabase.invoke(application)
        orderDao = database.currentOrderDao()
        orders = orderDao.getOrder()
    }

    companion object {
        private var instance: LocalRepository? = null
        fun getInstance(application: Application): LocalRepository {
            if (instance == null) {
                instance = LocalRepository(application)
            }
            return instance!!
        }
    }

    fun getOrders(): LiveData<List<OrderModel>> {
        return orders
    }

    fun retrieveOrder(){
        orderDao.getOrder()
    }

    fun saveOrder(orderModel: OrderModel) {
        CoroutineScope(IO).launch {
            orderDao.insertOrder(orderModel)
        }
    }

}