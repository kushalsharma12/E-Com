package com.kushalsharma.e_com.adapters

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.kushalsharma.e_com.R
import com.kushalsharma.e_com.models.products
import java.lang.String

class itemsAdapter(
    options: FirestoreRecyclerOptions<products>, val listner: onBtnClicked, var quantity: Int = 10
) :
    FirestoreRecyclerAdapter<products, itemsAdapter.itemViewHolder>(
        options
    ) {
    class itemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var productName: TextView = itemView.findViewById(R.id.itemName)
        var productPrice: TextView = itemView.findViewById(R.id.itemPrice)
        var productImage: ImageView = itemView.findViewById(R.id.imagee)
        var plusBtn: TextView = itemView.findViewById(R.id.plusTv)
        var minusBtn: TextView = itemView.findViewById(R.id.minusTv)
        var count: EditText = itemView.findViewById(R.id.count_ET)
        var cartBtn: Button = itemView.findViewById(R.id.itemCartBtn)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): itemViewHolder {

        val viewHolder = itemViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.items_main_activity, parent, false)
        )
        viewHolder.cartBtn.setOnClickListener {
            Log.d("btnClicked", " here it is ")

            listner.onbtnClicked()
        }

        return viewHolder
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: itemViewHolder, position: Int, model: products) {

        holder.productName.text = model.itemName
        holder.productPrice.text = model.itemPrice

        Glide.with(holder.productImage.context).load(model.itemImage).into(holder.productImage)

        holder.count.setText(Integer.toString(quantity))

        holder.plusBtn.setOnClickListener {
            var value: Int = String.valueOf(holder.count.getText()).toInt()
            value++
            holder.count.setText("" + value)

            val singleItemPrice = model.oneItemPrice
            val total = singleItemPrice + value


        }
        holder.minusBtn.setOnClickListener {
            var value: Int = String.valueOf(holder.count.getText()).toInt()
            if (value <= 10) {
                holder.count.setText("10")
            } else {
                value--
                holder.count.setText("" + value)
            }
        }


    }
}

interface onBtnClicked {

    fun onbtnClicked()

}

