package com.cricoo.sell_notebook

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast


class AuthActivity : AppCompatActivity() {
    private lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
        val buttonRegistration = findViewById<Button>(R.id.buttonRegistration)
        buttonRegistration.setOnClickListener {
            openRegistration()
        }
        val editTextLogin = findViewById<EditText>(R.id.editTextLogin)
        val editTextPassword = findViewById<EditText>(R.id.editTextPassword)
        val buttonLogin = findViewById<Button>(R.id.buttonLogin)

        sharedPreferences = getSharedPreferences(Const.MY_PREFS, Context.MODE_PRIVATE)
        val isAuth = sharedPreferences.getBoolean(Const.IS_AUTH, false)

        if (isAuth == true) {
            openMenuActivity(
                sharedPreferences = sharedPreferences
            )
        }
        buttonLogin.setOnClickListener {
            val inputLogin = editTextLogin.text.toString()
            val inputPassword = editTextPassword.text.toString()

            val savedLogin = sharedPreferences.getString(Const.LOGIN, "")
            val savedPassword = sharedPreferences.getString(Const.PASSWORD, "")

            if (inputLogin == savedLogin && inputPassword == savedPassword) {
                openMenuActivity(
                    sharedPreferences = sharedPreferences
                )

            } else {
                Toast.makeText(this, "Невірний логін або пароль!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun openMenuActivity(sharedPreferences: SharedPreferences) {
        sharedPreferences.edit().putBoolean(Const.IS_AUTH, true).apply()
        val intent = Intent(this, MenuActivity::class.java)
        startActivity(intent)
        finish()
    }


    fun openRegistration() {
        val intent = Intent(this, RegistrationActivity::class.java)
        startActivity(intent)
    }
}