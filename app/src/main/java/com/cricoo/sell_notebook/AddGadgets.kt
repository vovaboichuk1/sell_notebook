package com.cricoo.sell_notebook

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.DialogFragment

class AddGadgets(private val addRestaurantListener: (Gadgets) -> Unit) : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.add_gadgets, container, false)
        val editTextApartamentsName = view.findViewById<EditText>(R.id.editTextName)
        val editTextPrice = view.findViewById<EditText>(R.id.editTextPrice)
        val editTextStreet = view.findViewById<EditText>(R.id.editTextStreet)
        val editTextArea = view.findViewById<EditText>(R.id.editTextArea)
        val buttonAdd = view.findViewById<Button>(R.id.buttonAdd)

        buttonAdd.setOnClickListener {
            val name = editTextApartamentsName.text.toString()
            val price = editTextPrice.text.toString()
            val street = editTextStreet.text.toString()
            val area = editTextArea.text.toString()
            val apartaments = Gadgets(id, name, street, price, "", area)

            addRestaurantListener(apartaments)

            dismiss()
        }

        return view
    }
}