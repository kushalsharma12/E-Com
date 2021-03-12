package com.kushalsharma.e_com

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.kushalsharma.e_com.adapters.orderDetailsAdapter
import com.kushalsharma.e_com.models.cartProducts
import kotlinx.android.synthetic.main.activity_order_details.*

class orderDetails : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()
    private lateinit var adapter: orderDetailsAdapter
    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_details)

        supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24)
        title = "Order Details"

        auth = Firebase.auth

//        val name = intent.getStringExtra("name")
//        val count = intent.getStringExtra("count")
        val price = intent.getStringExtra("totalPrice")
//
//        textView.setText(name.toString())
//        textView2.setText(count.toString())
        oTotal.setText(price.toString())
        settingRecyclerView()
    }

    private fun settingRecyclerView() {


        val currentUser = auth.currentUser

        val query = db.collection("cartProducts")
            .whereEqualTo("userId", currentUser!!.uid)
            .orderBy("itemName").limit(100)

        //.whereEqualTo is used to show the specific data selected by the user


        val options = FirestoreRecyclerOptions.Builder<cartProducts>()
            .setQuery(query, cartProducts::class.java)
            .setLifecycleOwner(this).build()

        adapter = orderDetailsAdapter(options)
        rv_order_details.adapter = adapter
        rv_order_details.layoutManager = LinearLayoutManager(this)


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