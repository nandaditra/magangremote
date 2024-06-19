package com.example.magangremote.ui.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.magangremote.R
import com.example.magangremote.ui.auth.signup.SignUpFragment

class AuthActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        val fragmentManager = supportFragmentManager
        val signUpFragment = SignUpFragment()
        val fragment = fragmentManager.findFragmentByTag(SignUpFragment::class.java.simpleName)

        if (fragment !is SignUpFragment) {
            Log.d("MyFlexibleFragment", "Fragment Name :" + SignUpFragment::class.java.simpleName)
            fragmentManager
                .beginTransaction()
                .add(R.id.frame_container, signUpFragment, SignUpFragment::class.java.simpleName)
                .commit()
        }
    }
}