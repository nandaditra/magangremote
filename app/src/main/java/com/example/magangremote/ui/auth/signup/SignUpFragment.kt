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
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import com.example.magangremote.databinding.FragmentSignUpBinding
import com.example.magangremote.model.User
import com.example.magangremote.ui.auth.AuthActivity
import com.example.magangremote.ui.auth.AuthViewModel
import com.example.magangremote.ui.detail.DetailViewModel
import com.example.magangremote.ui.home.HomeActivity.Companion.TAG
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase


class SignUpFragment : Fragment() {

    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val model = ViewModelProvider(this)[AuthViewModel::class.java]

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
                    model.register(email, name, password)
                    model.response.observe(context as LifecycleOwner){ response->
                           register(response)
                    }
                }
            }
        }
    }

    private fun register(response:String) {
        if(response == "success") {
            Toast.makeText(context, "Pendaftaran Berhasil", Toast.LENGTH_SHORT,).show()
            startActivity(Intent(context, AuthActivity::class.java))
        } else {
            Toast.makeText(context, "Pendaftaran Gagal.", Toast.LENGTH_SHORT,).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}