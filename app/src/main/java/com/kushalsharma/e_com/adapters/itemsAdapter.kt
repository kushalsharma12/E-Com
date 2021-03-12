package com.kushalsharma.e_com.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.kushalsharma.e_com.R
import com.kushalsharma.e_com.dao.orderDao
import com.kushalsharma.e_com.models.products
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.lang.String

class itemsAdapter(
    options: FirestoreRecyclerOptions<products>, val listner: mAdapter, var quantity: Int = 10
) :
    FirestoreRecyclerAdapter<products, itemsAdapter.itemViewHolder>(
        options
    ) {
    private lateinit var orderDao: orderDao

    class itemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var productName: TextView = itemView.findViewById(R.id.cItemName)
        var productPrice: TextView = itemView.findViewById(R.id.cItemPrice)
        var productImage: ImageView = itemView.findViewById(R.id.imageCart)
        var plusBtn: TextView = itemView.findViewById(R.id.plusTv)
        var minusBtn: TextView = itemView.findViewById(R.id.minusTv)
        var count: TextView = itemView.findViewById(R.id.cItemCount)
        var cartBtn: Button = itemView.findViewById(R.id.addToCartBtn)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): itemViewHolder {

        orderDao = orderDao()
        val viewHolder = itemViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.items_main_activity, parent, false)
        )

        GlobalScope.launch(Dispatchers.IO) {

            viewHolder.cartBtn.setOnClickListener {
                val productId = snapshots.getSnapshot(viewHolder.adapterPosition).id
                val productName = viewHolder.productName.text.toString()
                val prouctPrice = viewHolder.productPrice.text.toString()
                val productCount = viewHolder.count.text.toString()
                val productImage = viewHolder.productImage.toString()
//                Glide.with(holder.productImage.context).load(model.itemImage).into(holder.productImage)

                orderDao.addOrder(productId, productName, prouctPrice, productCount, productImage)
                listner.onBtnClicked()
            }

        }

        return viewHolder
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(
        holder: itemsAdapter.itemViewHolder,
        position: Int,
        model: products
    ) {
        var singleItemPrice = 15
        var total = 0

        holder.productName.text = model.itemName
        holder.productPrice.text = model.itemPrice

        Glide.with(holder.productImage.context).load(model.itemImage).into(holder.productImage)

        holder.count.setText(Integer.toString(quantity))



        holder.plusBtn.setOnClickListener {
            var value: Int = String.valueOf(holder.count.getText()).toInt()
            value++
            holder.count.setText("" + value)

            singleItemPrice = model.oneItemPrice.toInt()
            total = singleItemPrice.times((value))
            holder.productPrice.text = "$total"


        }

        holder.minusBtn.setOnClickListener {
            var value: Int = String.valueOf(holder.count.getText()).toInt()
            if (value <= 10) {
                holder.count.text = "10"
            } else {
                value--
                holder.count.text = "" + value
                total = singleItemPrice.times((value))
                holder.productPrice.text = "$total"
            }
        }
    }
}

interface mAdapter {

    fun onBtnClicked()
}


