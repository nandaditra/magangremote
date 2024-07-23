package com.example.magangremote.repository

import android.util.Log
import com.example.magangremote.model.User
import com.example.magangremote.ui.home.HomeActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class AuthRepository {

    private val firebaseAuth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }

    private val fireStoreDatabase: FirebaseFirestore by lazy {
        FirebaseFirestore.getInstance()
    }

    fun register(email:String, name:String, password:String, callback: (Result<String>) -> Unit){
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(
            OnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val userId = firebaseAuth.currentUser?.uid
                    val documentReference = fireStoreDatabase.collection("user").document(userId.toString())
                    val user = User(userId, name, email, handphoneNumber = "", "https://exoffender.org/wp-content/uploads/2016/09/empty-profile.png", interest = "")
                    documentReference.set(user).addOnSuccessListener(OnSuccessListener {
                        Log.d(HomeActivity.TAG, "User successfully written!")
                    }).addOnFailureListener { e ->
                        Log.w(HomeActivity.TAG, "Error writing user", e)
                    }
                    callback(Result.success("success"))
                } else {
                    callback(Result.success("failed"))
                }
            })
    }
}