package com.example.currencyrateapp.ui

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.lifecycle.ViewModelProvider
import com.example.currencyrateapp.R
import com.example.currencyrateapp.database.AppDatabase
import com.example.currencyrateapp.repository.UserRepository
import com.example.currencyrateapp.viewmodel.UserFormViewModel
import com.example.currencyrateapp.viewmodel.UserFormViewModelFactory

class UserFormActivity : ComponentActivity() {

    private lateinit var viewModel: UserFormViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_form)

        // Initialize Database and ViewModel
        val database = AppDatabase.getDatabase(applicationContext)
        val userRepository = UserRepository(database)
        viewModel = ViewModelProvider(this, UserFormViewModelFactory(userRepository))[UserFormViewModel::class.java]

        // Initialize UI components
        val editTextFirstName = findViewById<EditText>(R.id.editTextFirstName)
        val editTextLastName = findViewById<EditText>(R.id.editTextLastName)
        val editTextEmail = findViewById<EditText>(R.id.editTextEmail)
        val editTextAge = findViewById<EditText>(R.id.editTextAge)
        val buttonSave = findViewById<Button>(R.id.buttonSave)
        val textViewError = findViewById<TextView>(R.id.textViewError)

        // Observe changes in the error message
        viewModel.error.observe(this) { errorMessage ->
            textViewError.visibility = if (errorMessage.isNotEmpty()) View.VISIBLE else View.GONE
            textViewError.text = errorMessage
        }

        // Observe changes in the user object
        viewModel.user.observe(this) { user ->
            user?.let {
                editTextFirstName.setText(it.firstName)
                editTextLastName.setText(it.lastName)
                editTextEmail.setText(it.email)
                editTextAge.setText(it.age.toString())
            }
        }

        // Set click listener for the "Save" button
        buttonSave.setOnClickListener {
            val firstName = editTextFirstName.text.toString()
            val lastName = editTextLastName.text.toString()
            val email = editTextEmail.text.toString()
            val age = editTextAge.text.toString().toIntOrNull() ?: -1

            viewModel.saveUser(firstName, lastName, email, age)
        }
    }
}

