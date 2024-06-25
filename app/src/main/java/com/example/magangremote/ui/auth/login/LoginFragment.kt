package com.example.magangremote.ui.auth.login

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.magangremote.R
import com.example.magangremote.databinding.FragmentLoginBinding
import com.example.magangremote.ui.detail.DetailActivity
import com.example.magangremote.ui.home.HomeActivity
import com.example.magangremote.ui.home.HomeActivity.Companion.TAG
import com.example.magangremote.ui.lupaPassword.LupaPasswordActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

//import com.google.firebase.Firebase
//import com.google.firebase.auth.FirebaseAuth
//import com.google.firebase.auth.auth

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        auth = Firebase.auth
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            textForgetpassword.setOnClickListener{
                val intent = Intent(activity, LupaPasswordActivity::class.java)
                startActivity(intent)
            }

            btnMasuk.setOnClickListener {
                val email = binding.inputEmailLogin.text.toString().trim();
                val password = binding.inputPasswordLogin.text.toString().trim();

                Toast.makeText(
                    context,
                    "$email and $password",
                    Toast.LENGTH_SHORT,
                ).show()

                if(TextUtils.isEmpty(email)) {
                    inputEmailLogin.error = "Email kosong"
                }

                if(TextUtils.isEmpty(password)) {
                    inputPasswordLogin.error = "Password kosong"
                }

                if(email.isNotEmpty() && password.isNotEmpty()) {
                    auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener( OnCompleteListener { task ->
                            if (task.isSuccessful) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "Login berhasil")
                                val intent = Intent(activity, HomeActivity::class.java)
                                startActivity(intent)
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "login gagal", task.exception)
                                Toast.makeText(
                                    context,
                                    "Login gagal",
                                    Toast.LENGTH_SHORT,
                                ).show()
                            } })
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}