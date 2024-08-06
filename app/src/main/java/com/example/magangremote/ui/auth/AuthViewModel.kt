package com.example.magangremote.ui.auth

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.magangremote.repository.AuthRepository
import com.example.magangremote.repository.LowonganRepository
import com.example.magangremote.ui.home.HomeActivity

class AuthViewModel: ViewModel() {
    private val _token = MutableLiveData<String>()
    val token: LiveData<String> = _token

    private val _response = MutableLiveData<String>()
    val response: LiveData<String> = _response

    private val repository = AuthRepository()

    fun login(email:String, password:String){
        repository.login(email, password) { result ->
            result.onSuccess { results ->
                _token.postValue(results)
            }.onFailure { throwable ->
                Log.d("LoginFragment", "onFailure: ${throwable.message}")
            }
        }
    }

    fun register(email:String, name:String, password:String){
        repository.register(email, name, password){ result ->
            result.onSuccess { results ->
                _response.postValue(results)
            }.onFailure { throwable ->
                Log.e("SignUpFragment", "onFailure: ${throwable.message}")
            }
        }
    }
}