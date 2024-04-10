package com.cricoo.sell_notebook

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class GadgetsAdapter(private val apartamentsList: MutableList<Gadgets>) : RecyclerView.Adapter<GadgetsAdapter.RestaurantViewHolder>() {

    class RestaurantViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextCountRoom: TextView = itemView.findViewById(R.id.textViewCountRoom)
        val streetTextView: TextView = itemView.findViewById(R.id.textViewStreet)
        val priceTextView: TextView = itemView.findViewById(R.id.textViewDescription)
        val areaTextView: TextView = itemView.findViewById(R.id.textViewArea)
        val deleteButton: Button = itemView.findViewById(R.id.buttonDelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_gadgets, parent, false)
        return RestaurantViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RestaurantViewHolder, position: Int) {
        val currentItem = apartamentsList[position]
        holder.nameTextCountRoom.text = currentItem.countRoom
        holder.streetTextView.text = currentItem.street
        holder.priceTextView.text = currentItem.price
        holder.areaTextView.text = currentItem.area

        holder.deleteButton.setOnClickListener {
            apartamentsList.removeAt(position)
            notifyDataSetChanged()
        }
        holder.itemView.setOnClickListener {
            showEditRestaurantDialog(currentItem, position, holder.itemView.context)
        }

    }
    fun updateRestaurant(position: Int, updatedApartaments: Gadgets) {
        apartamentsList[position] = updatedApartaments
        notifyDataSetChanged()
    }
    private fun showEditRestaurantDialog(apartaments: Gadgets, position: Int, context: Context) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Редагувати")

        val view = LayoutInflater.from(context).inflate(R.layout.edit_gadgets, null)
        builder.setView(view)

        val editTextName = view.findViewById<EditText>(R.id.editTextName)
        val editTextStreet = view.findViewById<EditText>(R.id.editTextStreet)
        val editTextPrice = view.findViewById<EditText>(R.id.editTextPrice)
        val editTextArea = view.findViewById<EditText>(R.id.editTextArea)

        editTextName.setText(apartaments.countRoom)
        editTextStreet.setText(apartaments.street)
        editTextPrice.setText(apartaments.price)
        editTextArea.setText(apartaments.area)

        builder.setPositiveButton("Оновити") { dialog, _ ->
            val countRoom = editTextName.text.toString()
            val street = editTextStreet.text.toString()
            val price = editTextPrice.text.toString()
            val area = editTextArea.text.toString()


            if (countRoom.isNotEmpty() && street.isNotEmpty() && price.isNotEmpty() &&
                area.isNotEmpty()) {
                val updatedApartaments = Gadgets(
                    apartaments.id,
                    countRoom,
                    street,
                    price,
                    "",
                    area,
                )
                updateRestaurant(position, updatedApartaments)
            } else {
                Toast.makeText(context, "Заповніть всі поля", Toast.LENGTH_SHORT).show()
            }
            dialog.dismiss()
        }

        builder.setNegativeButton("Скасувати") { dialog, _ ->
            dialog.dismiss()
        }

        builder.create().show()
    }

    override fun getItemCount(): Int {
        return apartamentsList.size
    }

    fun addItem(apartaments: Gadgets) {
        apartamentsList.add(apartaments)
        notifyDataSetChanged()
    }
}