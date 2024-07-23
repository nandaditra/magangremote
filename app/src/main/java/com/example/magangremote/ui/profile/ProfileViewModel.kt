package com.example.magangremote.ui.profile

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.magangremote.model.User
import com.example.magangremote.repository.UserRepository

class ProfileViewModel: ViewModel() {
    private val _userProfile = MutableLiveData<User?>()
    val userProfile: LiveData<User?> = _userProfile

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val repository=UserRepository()
//    fun getBiodataProfile(){
//        _isLoading.value = true
//        repository.getBiodataProfile()
//    }
  }