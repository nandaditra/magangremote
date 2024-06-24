package com.example.magangremote.ui.auth

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.magangremote.ui.auth.login.LoginFragment
import com.example.magangremote.ui.auth.signup.SignUpFragment

class SectionsPagerAdapter(activity:AppCompatActivity): FragmentStateAdapter(activity){
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = LoginFragment()
            1 -> fragment = SignUpFragment()
        }
        return fragment as Fragment
    }
}