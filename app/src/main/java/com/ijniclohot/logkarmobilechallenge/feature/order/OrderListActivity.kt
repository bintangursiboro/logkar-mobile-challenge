package com.ijniclohot.logkarmobilechallenge.feature.order

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ijniclohot.logkarmobilechallenge.R
import com.ijniclohot.logkarmobilechallenge.adapters.OrderListAdapter
import com.ijniclohot.logkarmobilechallenge.models.OrderModel
import com.ijniclohot.logkarmobilechallenge.viewmodel.OrderListViewModel
import com.ijniclohot.logkarmobilechallenge.viewmodel.factory.OrderListVMFactory
import kotlinx.android.synthetic.main.activity_order_list.*

class OrderListActivity : AppCompatActivity() {
    private lateinit var orderListAdapter: OrderListAdapter
    private lateinit var orderListViewModel: OrderListViewModel

    companion object {
        const val TAG = "OrderListActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_list)

        initView()
        setObserver()
    }

    private fun initView() {
        recycler_order_list.layoutManager = LinearLayoutManager(this)
        orderListAdapter = OrderListAdapter(ArrayList(), this)
        recycler_order_list.adapter = orderListAdapter

        //VM
        orderListViewModel =
            ViewModelProvider(this, OrderListVMFactory(application))[OrderListViewModel::class.java]
        orderListViewModel.getOrder()
    }

    private fun setObserver() {
        orderListViewModel.getOrderLD().observe(this, Observer {
            if (it.isNotEmpty()) {
                progress_bar.visibility = View.GONE
                recycler_order_list.visibility = View.VISIBLE
                orderListAdapter.appendData(it)
            }
        })

        orderListViewModel.getOrderLocalLD().observe(this, Observer {
            if (it.isNotEmpty()) {
                Log.d(TAG + " Local", it.size.toString())
                orderListAdapter.appendData(it)
            }
        })
    }

}