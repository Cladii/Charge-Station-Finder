package com.example.g52010mobg5.app.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.g52010mobg5.app.database.email.Email
import com.example.g52010mobg5.app.database.email.EmailDatabaseDao
import kotlinx.coroutines.launch
import java.util.*

class HomeFragmentViewModel(
    private val database: EmailDatabaseDao,
    application: Application
) : AndroidViewModel(application) {

    private val _name = MutableLiveData<String>()
    val name: LiveData<String>
        get() = _name

    val status = MutableLiveData<Boolean>()

    val emails = database.getAll()

    fun onVerifyEmail(emailText: String) {
        if (!isEmailValid(emailText)) {
            status.value = false
        } else {
            status.value = true
            _name.value = emailText
            viewModelScope.launch {
                val email = Email(
                    emailText,
                    Date().toString()
                )
                insert(email)
            }
        }
    }

    private fun isEmailValid(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private suspend fun insert(email: Email) {
        if (database.get(email.emailId) == null) {
            database.insert(email)
        } else {
            database.update(email)
        }
    }
}