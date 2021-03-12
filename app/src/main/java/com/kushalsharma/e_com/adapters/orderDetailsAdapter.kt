package com.kushalsharma.e_com.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.kushalsharma.e_com.R
import com.kushalsharma.e_com.models.cartProducts

class orderDetailsAdapter(options: FirestoreRecyclerOptions<cartProducts>) :
    FirestoreRecyclerAdapter<cartProducts, orderDetailsAdapter.oDViewHolder>(options) {

    class oDViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.oItemName)
        val price: TextView = itemView.findViewById(R.id.oItemPrice)
        val count: TextView = itemView.findViewById(R.id.oItemCount)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): oDViewHolder {
        val viewHolder = oDViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.items_order_details, parent, false)
        )

        return viewHolder

    }

    override fun onBindViewHolder(holder: oDViewHolder, position: Int, model: cartProducts) {

        holder.name.text = model.itemName
        holder.count.text = "Count:" + model.itemCount
        holder.price.text = model.itemPrice

    }
}