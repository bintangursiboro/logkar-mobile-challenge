package com.ijniclohot.logkarmobilechallenge.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ijniclohot.logkarmobilechallenge.R
import com.ijniclohot.logkarmobilechallenge.models.OrderModel
import kotlinx.android.synthetic.main.order_item_layout.view.*

class OrderListAdapter(private var orderList: ArrayList<OrderModel>, private val context: Context) :
    RecyclerView.Adapter<OrderListAdapter.ViewHolder>() {

    inner class ViewHolder(var view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.order_item_layout, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return orderList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.view.tv_sender_name.text = orderList[position].origin.user
        holder.view.tv_sender_district.text = orderList[position].origin.district
        holder.view.tv_sender_phone.text = orderList[position].origin.phone

        holder.view.tv_receiver_district.text = orderList[position].destination.district
        holder.view.tv_receiver_name.text = orderList[position].destination.user
        holder.view.tv_receiver_phone.text = orderList[position].destination.phone

        holder.view.tv_qty_order.text = orderList[position].transporter.quantityOrder.toString()
        holder.view.tv_capacity.text = orderList[position].transporter.capacity.toString()
        holder.view.tv_commodity.text = orderList[position].transporter.commodityName
        holder.view.tv_delivery_date.text = orderList[position].transporter.startDeliveryDate
        holder.view.tv_notes.text = orderList[position].transporter.note
        holder.view.tv_truck_type.text = orderList[position].transporter.truckType
        holder.view.tv_unit.text = orderList[position].transporter.unit
    }

    fun setData(newList: List<OrderModel>) {
        orderList.clear()
        orderList.addAll(newList)
        notifyDataSetChanged()
    }

    fun appendData(newList: List<OrderModel>) {
        orderList.addAll(newList)
        notifyDataSetChanged()
    }
}