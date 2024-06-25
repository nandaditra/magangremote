package com.example.magangremote.ui.auth.signup

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.magangremote.R
import com.example.magangremote.databinding.FragmentLoginBinding
import com.example.magangremote.databinding.FragmentSignUpBinding
import com.example.magangremote.ui.home.HomeActivity
import com.example.magangremote.ui.lupaPassword.LupaPasswordActivity

class SignUpFragment : Fragment() {

    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!
//    private lateinit var auth: FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        auth = Firebase.auth

        binding.apply {
            btnLogin.setOnClickListener {
                 val nameUser = inputNameSignup.text.toString().trim();
                 val emailUser = inputEmailSignup.text.toString().trim();
                 val password = inputPasswordSignup.text.toString().trim();
                val confirmPassword = inputConfirmPasswordSignup.text.toString().trim();

                if(TextUtils.isEmpty(nameUser)) {
                    inputNameSignup.error = "Name Required"
                }

                if(TextUtils.isEmpty(emailUser)) {
                    inputEmailSignup.error = "Email Required"
                }

                if(TextUtils.isEmpty(password)) {
                    inputEmailSignup.error = "Password Required"
                }

                if(TextUtils.isEmpty(confirmPassword)) {
                    inputEmailSignup.error = "Confirm Password Required"
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}