package com.kushalsharma.e_com.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.kushalsharma.e_com.R
import com.kushalsharma.e_com.models.cartProducts
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class cItemsAdapter(
    options: FirestoreRecyclerOptions<cartProducts>
) : FirestoreRecyclerAdapter<cartProducts, cItemsAdapter.cartViewHolder>(
    options
) {
    var overAllPrice: Int = 0

    class cartViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var nameTv: TextView = itemView.findViewById(R.id.itemNameee)
        var priceTv: TextView = itemView.findViewById(R.id.itemPriceee)
        var countTv: TextView = itemView.findViewById(R.id.itemCounttt)
        var imageImg: ImageView = itemView.findViewById(R.id.imageeee)
        var deleteBtn: ImageButton = itemView.findViewById(R.id.cdelete_item_Btn)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): cartViewHolder {

        val viewHolder = cartViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.items_cart_activity, parent, false)
        )
        return viewHolder
    }

    fun deleteItem(positon: Int) {
        snapshots.getSnapshot(positon).reference.delete()
    }


    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: cartViewHolder, position: Int, model: cartProducts) {

        var oneItemPrice: Int = 0

        holder.nameTv.text = model.itemName
        holder.countTv.text = "Count:" + model.itemCount
        holder.priceTv.text = model.itemPrice
        Glide.with(holder.imageImg.context).load(model.itemImage).into(holder.imageImg)

        oneItemPrice = model.itemPrice.toInt()
        overAllPrice += oneItemPrice

        holder.deleteBtn.setOnClickListener {
            //delete the item saved in cart from UI and from the firebase also.
            GlobalScope.launch {
                deleteItem(holder.adapterPosition)
            }
        }
    }
}


