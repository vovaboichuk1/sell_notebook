package com.cricoo.sell_notebook

import android.app.DatePickerDialog
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.util.Calendar

class ProfileActivity : AppCompatActivity(), EditProfile.EditProfileDialogListener {

    private var name: String = ""
    private var lastName: String = ""
    private var email: String = ""
    private var dob: String = ""

    private lateinit var sharedPreferences: SharedPreferences
    private val GALLERY_REQUEST_CODE = 100
    private lateinit var editTextDOB: EditText
    private lateinit var textViewName: TextView
    private lateinit var textViewLastName: TextView
    private lateinit var textViewEmail: TextView
    private lateinit var imageViewAvatar: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.profile_activity)

        imageViewAvatar = findViewById(R.id.imageViewAvatar)

        textViewName = findViewById(R.id.textViewName)
        textViewLastName = findViewById(R.id.textViewLastName)
        textViewEmail = findViewById(R.id.textViewEmail)
        editTextDOB = findViewById(R.id.editTextDOB)

        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE)
        name = sharedPreferences.getString("name", "Немає ім'я") ?: "Немає ім'я"
        lastName = sharedPreferences.getString("lastName", "Немає прізвища") ?: "Немає прізвища"
        email = sharedPreferences.getString("email", "Немає емейлу") ?: "Немає емейлу"
        dob = sharedPreferences.getString("dob", "Немає дати народження") ?: "Немає дати народження"

        textViewName.text = name
        textViewLastName.text = lastName
        textViewEmail.text = email
        editTextDOB.setText(dob)

        imageViewAvatar.setImageResource(R.drawable.default_avatar1)

        imageViewAvatar.setOnClickListener {
            openGallery()
        }

        textViewName.setOnClickListener {
            showEditDialog("ім'я", name!!)
        }

        textViewLastName.setOnClickListener {
            showEditDialog("прізвище", lastName!!)
        }

        textViewEmail.setOnClickListener {
            showEditDialog("емейл", email!!)
        }

        editTextDOB.setOnClickListener {
            openDatePickerDialog()
        }
    }

    private fun showEditDialog(field: String, value: String) {
        val dialog = EditProfile.newInstance(field, value)
        dialog.show(supportFragmentManager, "EditProfileDialog")
    }

    override fun onDialogPositiveClick(field: String, value: String) {
        saveValueToPreferences(field, value)
        when (field) {
            "ім'я" -> {
                textViewName.text = value
                saveValueToPreferences("name", value)
            }
            "прізвище" -> {
                textViewLastName.text = value
                saveValueToPreferences("lastName", value)
            }
            "емейл" -> {
                textViewEmail.text = value
                saveValueToPreferences("email", value)
            }
            "дата народження" -> {
                editTextDOB.setText(value)
                saveValueToPreferences("dob", value)
            }
        }
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, GALLERY_REQUEST_CODE)
    }

    private fun openDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val dateSetListener = DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
            val selectedDate = String.format("%d-%02d-%02d", year, monthOfYear + 1, dayOfMonth)
            editTextDOB.setText(selectedDate)
            saveValueToPreferences("dob", selectedDate)
        }

        val datePickerDialog = DatePickerDialog(
            this@ProfileActivity,
            dateSetListener,
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.datePicker.maxDate = System.currentTimeMillis()
        datePickerDialog.show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == GALLERY_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            val imageUri: Uri? = data.data
            saveImageLocally(imageUri)
            val ivAvatar = findViewById<ImageView>(R.id.imageViewAvatar)
            ivAvatar.setImageURI(imageUri)
            saveImagePath(imageUri.toString())
        }
    }


    private fun saveImageLocally(imageUri: Uri?) {
        try {
            val inputStream: InputStream? = imageUri?.let { contentResolver.openInputStream(it) }
            val outputStream = FileOutputStream(File(filesDir, "avatar.jpg"))
            inputStream?.copyTo(outputStream)
            inputStream?.close()
            outputStream.close()
            saveImagePath(filesDir.absolutePath + "/avatar.jpg")
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun saveImagePath(imagePath: String?) {
        val editor = sharedPreferences.edit()
        editor.putString("imagePath", imagePath)
        editor.apply()
    }

    private fun getImagePath(): String? {
        return sharedPreferences.getString("imagePath", null)
    }

    private fun saveValueToPreferences(field: String, value: String) {
        val editor = sharedPreferences.edit()
        editor.putString(field, value)
        editor.apply()
    }

    override fun onResume() {
        super.onResume()

        val sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE)
        name = sharedPreferences.getString("name", "Немає ім'я") ?: "Немає ім'я"
        lastName = sharedPreferences.getString("lastName", "Немає прізвища") ?: "Немає прізвища"
        email = sharedPreferences.getString("email", "Немає емейлу") ?: "Немає емейлу"
        dob = sharedPreferences.getString("dob", "Немає дати народження") ?: "Немає дати народження"

        findViewById<TextView>(R.id.textViewName).text = name
        findViewById<TextView>(R.id.textViewLastName).text = lastName
        findViewById<TextView>(R.id.textViewEmail).text = email
        findViewById<EditText>(R.id.editTextDOB).setText(dob)
    }

    override fun onStop() {
        super.onStop()
        saveValueToPreferences("name", textViewName.text.toString())
        saveValueToPreferences("lastName", textViewLastName.text.toString())
        saveValueToPreferences("email", textViewEmail.text.toString())
        saveValueToPreferences("dob", editTextDOB.text.toString())
        val imagePath = getImagePath()
        if (imagePath != null) {
            saveValueToPreferences("imagePath", imagePath)
        }
    }
}