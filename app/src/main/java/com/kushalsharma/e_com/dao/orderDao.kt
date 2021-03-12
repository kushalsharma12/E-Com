package com.kushalsharma.e_com.dao


import com.google.android.gms.tasks.Task
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.kushalsharma.e_com.models.cartProducts
import com.kushalsharma.e_com.models.user
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class orderDao {

    private val db = FirebaseFirestore.getInstance()
    private val collection = db.collection("cartProducts")


    val auth = Firebase.auth

    fun addOrder(
        itemId: String,
        itemName: String,
        itemPrice: String,
        itemCount: String,
        itemImage: String
    ) {
        GlobalScope.launch {
            val currentUserId = auth.currentUser!!.uid
            val userDao = UserDao()
            val user = userDao.getUserById(currentUserId).await().toObject(user::class.java)!!

            val order =
                cartProducts(itemId, itemName, user, itemPrice, itemCount, user.uid, itemImage)
            collection.document().set(order)

        }

    }

    fun getOrderById(orderId: String): Task<DocumentSnapshot> {

        return collection.document(orderId).get()
        //here get() is used to get the data from firebase like we used set()
        // to set data in firebase.
    }


}


