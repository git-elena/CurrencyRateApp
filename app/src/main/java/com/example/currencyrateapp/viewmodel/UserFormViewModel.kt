package com.example.currencyrateapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencyrateapp.database.User
import com.example.currencyrateapp.repository.UserRepository
import kotlinx.coroutines.launch

class UserFormViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val _user = MutableLiveData<User?>()
    val user: LiveData<User?> get() = _user

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    init {
        loadUser()
    }

    // Load the user data from the database when the ViewModel is initialized
    private fun loadUser() {
        viewModelScope.launch {
            _user.value = userRepository.getUser()
        }
    }

    // Save the user data to the database
    fun saveUser(firstName: String, lastName: String, email: String, age: Int) {
        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || age <= 0) {
            _error.value = "All fields are required and age must be positive"
        } else {
            _error.value = ""
            viewModelScope.launch {
                try {
                    val user = User(firstName = firstName, lastName = lastName, email = email, age = age)
                    userRepository.saveUser(user)
                } catch (e: Exception) {
                    _error.value = "Failed to save user: ${e.message}"
                }
            }
        }
    }
}
