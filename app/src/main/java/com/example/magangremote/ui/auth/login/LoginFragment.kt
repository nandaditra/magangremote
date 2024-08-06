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
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import com.example.magangremote.R
import com.example.magangremote.auth.UserPreferences
import com.example.magangremote.databinding.FragmentLoginBinding
import com.example.magangremote.ui.auth.AuthViewModel
import com.example.magangremote.ui.detail.DetailActivity
import com.example.magangremote.ui.home.HomeActivity
import com.example.magangremote.ui.home.HomeActivity.Companion.TAG
import com.example.magangremote.ui.auth.lupaPassword.LupaPasswordActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth
    private lateinit var dataPreferences: UserPreferences
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        auth = Firebase.auth
        dataPreferences = UserPreferences(requireContext())
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val model = ViewModelProvider(this)[AuthViewModel::class.java]

        binding.apply {
            textForgetpassword.setOnClickListener{
                val intent = Intent(activity, LupaPasswordActivity::class.java)
                startActivity(intent)
            }

            btnMasuk.setOnClickListener {
                val email = binding.inputEmailLogin.text.toString().trim();
                val password = binding.inputPasswordLogin.text.toString().trim();

                if(TextUtils.isEmpty(email)) inputEmailLogin.error = "Email kosong"
                if(TextUtils.isEmpty(password)) inputPasswordLogin.error = "Password kosong"
                if(email.isNotEmpty() && password.isNotEmpty()) {
                    model.login(email, password)
                    model.token.observe(viewLifecycleOwner){ token->
                        dataPreferences.setInput(UserPreferences.TOKEN, token)
                        dataPreferences.setLogin(UserPreferences.IS_LOGIN, true)
                        val intent = Intent(activity, HomeActivity::class.java)
                        startActivity(intent)
                    }
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        if(dataPreferences.getLogin(UserPreferences.IS_LOGIN)) {
            moveActivity()
        }
    }

    private fun moveActivity() {
        val intent = Intent(context, HomeActivity::class.java)
        startActivity(intent)
        activity?.finish()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}