package com.kushalsharma.e_com

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.transition.Fade
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.kushalsharma.e_com.adapters.itemsAdapter
import com.kushalsharma.e_com.adapters.mAdapter
import com.kushalsharma.e_com.dao.orderDao
import com.kushalsharma.e_com.models.products
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), mAdapter
//    onBtnClicked
{

    private val db = FirebaseFirestore.getInstance()
    private lateinit var auth: FirebaseAuth

    private lateinit var orderDao: orderDao

    private lateinit var adapter: itemsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        orderDao = orderDao()
        setupRecyclerView()


    }

    private fun setupRecyclerView() {

        val query = db.collection("products")
            .orderBy("itemName").limit(100)

        val options = FirestoreRecyclerOptions.Builder<products>()
            .setQuery(query, products::class.java)
            .setLifecycleOwner(this).build()

        adapter = itemsAdapter(options, this)
        rv_activity_main.adapter = adapter
        rv_activity_main.layoutManager = LinearLayoutManager(this)


    }


    override fun onStart() {
        super.onStart()
        adapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter.stopListening()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.cart_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        val id = item.itemId

        if (id == R.id.logOut) {
            val dialogBuilder = AlertDialog.Builder(this)


            dialogBuilder.setMessage("Are you sure?")

                .setCancelable(false)

                .setPositiveButton("Proceed", DialogInterface.OnClickListener { dialog, id ->
                    Firebase.auth.signOut()
                    val Intent = Intent(this, LogInActivity::class.java)
                    startActivity(Intent)
                    finish()


                })
                // negative button text and action
                .setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, id ->
                    dialog.cancel()
                })


            val alert = dialogBuilder.create()

            alert.setTitle("Log Out")

            alert.show()

        }

        if (id == R.id.About) {
//            val Intent = Intent(this,)
        }

        if (id == R.id.cart) {
            val cartIntent = Intent(this, CartActivity::class.java)
            startActivity(cartIntent)
        }

        return super.onOptionsItemSelected(item)

    }

    @SuppressLint("WrongConstant")
    override fun onBtnClicked() {

        Snackbar.make(
            constraintlayout,
            "E-Com || Check out the cart to order",
            Snackbar.LENGTH_LONG
        )
            .setAction("View Cart", View.OnClickListener {
                val intent = Intent(this, CartActivity::class.java)
                startActivity(intent)
            })
            .setActionTextColor(Color.RED)
            .setBackgroundTint(Color.YELLOW)
            .setTextColor(Color.BLUE)
            .setAnimationMode(Fade.MODE_IN)
            .show()
    }

}


