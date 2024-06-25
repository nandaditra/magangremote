package com.example.magangremote.ui.auth.signup

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.magangremote.R
import com.example.magangremote.databinding.FragmentSignUpBinding
import com.example.magangremote.model.User
import com.example.magangremote.ui.auth.AuthActivity
import com.example.magangremote.ui.home.HomeActivity.Companion.TAG
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.app
import java.util.Objects


class SignUpFragment : Fragment() {

    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var fireStoreDatabase: FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        firebaseAuth = Firebase.auth
        fireStoreDatabase = FirebaseFirestore.getInstance()
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            btnLogin.setOnClickListener {
                val name = inputNameSignup.text.toString().trim();
                val email = inputEmailSignup.text.toString().trim();
                val password = inputPasswordSignup.text.toString().trim();
                val confirmPassword = inputConfirmPasswordSignup.text.toString().trim();

                //checking condition variabel
                if(TextUtils.isEmpty(name)) inputNameSignup.error = "Nama kosong"
                if(TextUtils.isEmpty(email)) inputEmailSignup.error = "Email kosong"
                if(TextUtils.isEmpty(password)) inputPasswordSignup.error = "Password kosong"
                if(TextUtils.isEmpty(confirmPassword)) inputConfirmPasswordSignup.error = "Konfirmasi Password kosong"
                if(confirmPassword != password) {
                    inputPasswordSignup.error = "Password tidak sama"
                    inputConfirmPasswordSignup.error = "Konfirmasi Password tidak sama"
                }

                //register user dengan firebase
                if(email.isNotEmpty() &&
                    password.isNotEmpty() &&
                    name.isNotEmpty() &&
                    confirmPassword.isNotEmpty()) {
                    firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(
                        OnCompleteListener { task ->
                            if (task.isSuccessful) {
                                // Sign in success, update UI with the signed-in user's information
                                val userId = firebaseAuth.currentUser?.uid
                                val documentReference = fireStoreDatabase.collection("user").document(userId.toString())
                                val user = User(userId, name, email, handphoneNumber = "",imageUri = null, interest = listOf())
                                documentReference.set(user).addOnSuccessListener(OnSuccessListener {
                                    Log.d(TAG, "user successfully written!")
                                }).addOnFailureListener { e ->
                                    // Handle any errors
                                    Log.w(TAG, "Error writing user", e)
                                }
                                Toast.makeText(
                                    context,
                                    "Pendaftaran Berhasil",
                                    Toast.LENGTH_SHORT,
                                ).show()
                                Log.d(TAG, "createUserWithEmail:success")
                                startActivity(Intent(context, AuthActivity::class.java))
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "createUserWithEmail:failure", task.exception)
                                Toast.makeText(
                                    context,
                                    "Pendaftaran Gagal.",
                                    Toast.LENGTH_SHORT,
                                ).show()
                            }
                        })
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}