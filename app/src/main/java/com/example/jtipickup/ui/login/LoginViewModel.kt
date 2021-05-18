package com.example.jtipickup.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class LoginViewModel {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Login Fragment"
    }
    val text: LiveData<String> = _text
}