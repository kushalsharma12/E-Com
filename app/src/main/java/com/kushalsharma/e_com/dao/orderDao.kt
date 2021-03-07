package com.kushalsharma.e_com.dao


import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class orderDao {

    private val db = FirebaseFirestore.getInstance()
       val auth = Firebase.auth
}