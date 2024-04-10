package com.cricoo.sell_notebook

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class MenuActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.second_layout)

        val buttonLogout = findViewById<Button>(R.id.buttonLogout)
        val buttonApartaments = findViewById<Button>(R.id.buttonApartments)
        val buttonCustomers = findViewById<Button>(R.id.buttonCustomers)
        val imageViewProfile = findViewById<ImageView>(R.id.imageViewProfile)

        sharedPreferences = getSharedPreferences(Const.MY_PREFS, Context.MODE_PRIVATE)

        buttonLogout.setOnClickListener {
            sharedPreferences.edit().putBoolean(Const.IS_AUTH, false).apply()
            val intent = Intent(this, AuthActivity::class.java)
            startActivity(intent)
            finish()
        }

        buttonApartaments.setOnClickListener {
            val intent = Intent(this, GadgetsListActivity::class.java)
            startActivity(intent)
        }

        buttonCustomers.setOnClickListener {
            val intent = Intent(this, ManagersListActivity::class.java)
            startActivity(intent)
        }

        imageViewProfile.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }


    }
}