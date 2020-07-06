package com.ijniclohot.logkarmobilechallenge.adapters

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.ijniclohot.logkarmobilechallenge.R
import com.ijniclohot.logkarmobilechallenge.utils.OnRemoveReference

class ReferenceListAdapter(
    context: Context,
    private val layoutId: Int,
    private val listReference: List<String>,
    private val onRemoveReference: OnRemoveReference
) : ArrayAdapter<String>(context, layoutId, listReference) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view = convertView
        var holder = ReferenceHolder()
        if (view == null) {
            val inflater = (context as Activity).layoutInflater
            view = inflater.inflate(layoutId, parent, false)!!
            holder.number = view.findViewById(R.id.tv_reference_number)
            holder.reference = view.findViewById(R.id.tv_reference_string)
            holder.removeBtn = view.findViewById(R.id.remove_reference_btn)

            view.tag = holder
        } else {
            holder = view.tag as ReferenceHolder
        }

        holder.removeBtn?.setOnClickListener {
            onRemoveReference.removeReference(listReference[position])
        }

        holder.reference?.text = listReference[position]

        holder.number?.text = position.toString()

        return view
    }

    inner class ReferenceHolder() {
        var number: TextView? = null
        var reference: TextView? = null
        var removeBtn: ImageView? = null
    }
}