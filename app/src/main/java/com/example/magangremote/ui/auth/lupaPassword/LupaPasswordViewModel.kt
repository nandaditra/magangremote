package com.example.magangremote.ui.auth.lupaPassword

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.magangremote.repository.AuthRepository

class LupaPasswordViewModel: ViewModel() {
    private val _status = MutableLiveData<Boolean>()
    val status: LiveData<Boolean> = _status

    private val repository = AuthRepository()
    fun forgotPassword(email:String) {
        repository.forgotPassword(email) { result ->
            result.onSuccess { results ->
                _status.postValue(results)
            }.onFailure { throwable ->
                Log.e("LupaPassword", "onFailure: ${throwable.message}")
              }
            }
    }
}