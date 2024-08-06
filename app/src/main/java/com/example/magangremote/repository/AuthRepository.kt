package com.example.magangremote.repository

import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.example.magangremote.auth.UserPreferences
import com.example.magangremote.model.User
import com.example.magangremote.ui.auth.AuthActivity
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

    fun login(email:String, password:String, callback: (Result<String>) -> Unit) {
        try {
            firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener( OnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(HomeActivity.TAG, "Login berhasil")
                        val token =  firebaseAuth.currentUser?.uid.toString()
                        callback(Result.success(token))
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(HomeActivity.TAG, "login gagal", task.exception)
                    } })
        }catch (e:Error) {
            Log.e(HomeActivity.TAG, "login gagal",e)
        }

    }

    fun forgotPassword(email:String, callback: (Result<Boolean>) -> Unit) {
        try {
            firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener{
                if (it.isSuccessful){
                    callback(Result.success(true))
//                    Toast.makeText(this, "Check email to reset password", Toast.LENGTH_SHORT).show()
//                    startActivity(Intent(this, AuthActivity::class.java))
                }else{
                    callback(Result.success(false))
                 //   Toast.makeText(this, "Failed to reset password", Toast.LENGTH_SHORT).show()
                }
            }
        } catch (e:Error) {
            Log.w(HomeActivity.TAG, "Email salah", e)
        }
    }
}