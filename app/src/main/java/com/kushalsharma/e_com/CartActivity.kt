package com.kushalsharma.e_com

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.kushalsharma.e_com.adapters.cItemsAdapter
import com.kushalsharma.e_com.models.cartProducts
import com.kushalsharma.e_com.models.user
import kotlinx.android.synthetic.main.activity_cart.*

class CartActivity : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()
    private lateinit var adapter: cItemsAdapter
    private lateinit var auth: FirebaseAuth

    var total : String? = null
    private lateinit var cartViewHolder: cItemsAdapter.cartViewHolder

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24)
        title = "My Cart"

        auth = Firebase.auth


        settingRecyclerView()


        total_Cart_cost.setOnClickListener {

            total = adapter.overAllPrice.toString()
            total_Cart_cost.text = "Total Price: ₹$total"

        }
        oooo.setOnClickListener {
            Log.d("jojo","sdffff")
            Toast.makeText(this, "Your order is booked", Toast.LENGTH_SHORT).show()

//            val productName = cartViewHolder.nameTv.text.toString()
//            val productCount = cartViewHolder.countTv.text.toString()
//            val productPrice = cartViewHolder.priceTv.text.toString()



            val orderDetailsIntent = Intent(this,orderDetails::class.java)
//            orderDetailsIntent.putExtra("name",productName)
//            orderDetailsIntent.putExtra("count", productCount)
//            orderDetailsIntent.putExtra("price",productPrice)
            orderDetailsIntent.putExtra("totalPrice",total)
            startActivity(orderDetailsIntent)

        }


    }


    private fun settingRecyclerView() {
        val user = user()


        val currentUser = auth.currentUser

        val query = db.collection("cartProducts")
            .whereEqualTo("userId", currentUser!!.uid)
            .orderBy("itemName").limit(100)

        //.whereEqualTo is used to show the specific data selected by the user


        val options = FirestoreRecyclerOptions.Builder<cartProducts>()
            .setQuery(query, cartProducts::class.java)
            .setLifecycleOwner(this).build()

        adapter = cItemsAdapter(options)
        cartRv.adapter = adapter
        cartRv.layoutManager = LinearLayoutManager(this)



    }


    override fun onStart() {
        super.onStart()
        adapter.startListening()

    }

    override fun onStop() {
        super.onStop()
        adapter.stopListening()
    }
}