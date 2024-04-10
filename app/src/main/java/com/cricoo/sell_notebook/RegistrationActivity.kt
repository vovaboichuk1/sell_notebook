package com.cricoo.sell_notebook

import android.app.DatePickerDialog
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.Calendar
import java.util.regex.Pattern

class RegistrationActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)
        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE)
        val editTextName = findViewById<EditText>(R.id.editTextName)
        val editTextLastName = findViewById<EditText>(R.id.editTextLastName)
        val editTextEmail = findViewById<EditText>(R.id.editTextEmail)
        val editTextDateOfBirth = findViewById<EditText>(R.id.editTextDateOfBirth)
        val editTextLogin = findViewById<EditText>(R.id.editTextLogin)
        val editTextPassword = findViewById<EditText>(R.id.editTextPassword)
        val editTextConfirmPassword = findViewById<EditText>(R.id.editTextConfirmPassword)
        val buttonRegister = findViewById<Button>(R.id.buttonRegister)

        buttonRegister.setOnClickListener {
            val name = editTextName.text.toString()
            val lastName = editTextLastName.text.toString()
            val email = editTextEmail.text.toString()
            val dob = editTextDateOfBirth.text.toString()
            val login = editTextLogin.text.toString()
            val password = editTextPassword.text.toString()
            val confirmPassword = editTextConfirmPassword.text.toString()

            // Валідація введених даних
            if (validateInput(name, lastName, email, dob, login, password, confirmPassword)) {
                val editor = sharedPreferences.edit()
                editor.putString("name", name)
                editor.putString("lastName", lastName)
                editor.putString("email", email)
                editor.putString("dob", dob)
                editor.putString("login", login)
                editor.putString("password", password)
                editor.apply()

                // Перехід на сторінку авторизації після успішної реєстрації
                val intent = Intent(this, AuthActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

        val calendar = Calendar.getInstance()
        val dateSetListener = DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
            val selectedDate = String.format("%d-%02d-%02d", year, monthOfYear + 1, dayOfMonth)
            editTextDateOfBirth.setText(selectedDate)
        }

        editTextDateOfBirth.setOnClickListener {
            val datePickerDialog = DatePickerDialog(
                this@RegistrationActivity,
                dateSetListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )
            datePickerDialog.datePicker.maxDate = System.currentTimeMillis()
            datePickerDialog.show()
        }
    }

    private fun validateInput(
        name: String,
        lastName: String,
        email: String,
        dob: String,
        login: String,
        password: String,
        confirmPassword: String
    ): Boolean {
        if (name.isEmpty() || lastName.isEmpty() || email.isEmpty() || dob.isEmpty() || login.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            showToast("Будь ласка, заповніть всі поля")
            return false
        }

        if (!isValidEmail(email)) {
            showToast("Будь ласка, введіть коректну адресу електронної пошти")
            return false
        }

        if (!isValidPassword(password)) {
            showToast("Пароль повинен містити принаймні 8 символів")
            return false
        }

        if (password != confirmPassword) {
            showToast("Паролі не збігаються")
            return false
        }

        return true
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun isValidPassword(password: String): Boolean {
        val passwordPattern = Pattern.compile("^.{8,}$")
        return passwordPattern.matcher(password).matches()
    }
}