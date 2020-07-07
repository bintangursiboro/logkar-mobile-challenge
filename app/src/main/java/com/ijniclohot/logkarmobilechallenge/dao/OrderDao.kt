package com.ijniclohot.logkarmobilechallenge.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ijniclohot.logkarmobilechallenge.models.OrderModel

@Dao
interface OrderDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrder(orderModel: OrderModel)

    @Query("select * from order_list")
    fun getOrder() : LiveData<List<OrderModel>>
}