package com.kushalsharma.e_com.dao
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import com.google.firebase.firestore.DocumentSnapshot
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.w3c.dom.Document
import android.app.DownloadManager
import androidx.core.app.TaskStackBuilder
import com.google.android.gms.tasks.Task
import com.kushalsharma.e_com.models.user

class UserDao {

    private val db = FirebaseFirestore.getInstance()
    private val userCollection = db.collection("users")

    fun addUser(user: user?)
    {
        user?.let{
            GlobalScope.launch(Dispatchers.IO)
            {
                userCollection.document(user.uid).set(it)
            }
        }
    }
    fun getUserById(uid : String) : Task<DocumentSnapshot>{
        return userCollection.document(uid).get()
    }
}