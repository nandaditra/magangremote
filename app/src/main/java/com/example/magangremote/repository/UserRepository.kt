package com.example.magangremote.repository

import android.net.Uri
import com.example.magangremote.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage

class UserRepository {

    private val firebaseAuth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }

    private val fireStoreDatabase: FirebaseFirestore by lazy {
        FirebaseFirestore.getInstance()
    }

    private val storageRef: FirebaseStorage by lazy {
        FirebaseStorage.getInstance()
    }


}