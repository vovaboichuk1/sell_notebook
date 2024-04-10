package com.cricoo.sell_notebook

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.DialogFragment

class AddManager(private val addDishListener: (Manager) -> Unit) : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.add_manager, container, false)
        val editTextCustomerName = view.findViewById<EditText>(R.id.editTextCustomerName)
        val editTextCustomerLastName = view.findViewById<EditText>(R.id.editTextCustomerLastName)
        val editTextPhoneNumber = view.findViewById<EditText>(R.id.editTextPhoneNumber)
        val buttonAdd = view.findViewById<Button>(R.id.buttonAdd)

        buttonAdd.setOnClickListener {
            val name = editTextCustomerName.text.toString()
            val lastName = editTextCustomerLastName.text.toString()
            val phoneNumber = editTextPhoneNumber.text.toString()
            val customer = Manager(0, name, lastName, phoneNumber)

            addDishListener(customer)

            dismiss()
        }

        return view
    }
}