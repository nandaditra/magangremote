package com.example.magangremote.ui.auth.login

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.magangremote.R
import com.example.magangremote.databinding.FragmentLoginBinding
import com.example.magangremote.ui.detail.DetailActivity
import com.example.magangremote.ui.home.HomeActivity
import com.example.magangremote.ui.lupaPassword.LupaPasswordActivity
//import com.google.firebase.Firebase
//import com.google.firebase.auth.FirebaseAuth
//import com.google.firebase.auth.auth

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.textForgetpassword.setOnClickListener{
           val intent = Intent(activity, LupaPasswordActivity::class.java)
           startActivity(intent)
        }

        binding.apply {
            textForgetpassword.setOnClickListener{
                val intent = Intent(activity, LupaPasswordActivity::class.java)
                startActivity(intent)
            }

            btnMasuk.setOnClickListener {
                val intent = Intent(activity, HomeActivity::class.java)
                startActivity(intent)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}