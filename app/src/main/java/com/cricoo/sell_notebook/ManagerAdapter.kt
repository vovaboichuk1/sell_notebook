package com.cricoo.sell_notebook
import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ManagerAdapter(private val customerList: MutableList<Manager>) : RecyclerView.Adapter<ManagerAdapter.CustomersViewHolder>() {

    class CustomersViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.textViewCustomerName)
        val customerLastNameTextView: TextView = itemView.findViewById(R.id.textViewCustomerLastName)
        val phoneNumberTextView: TextView = itemView.findViewById(R.id.textViewDescription)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomersViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_managers, parent, false)
        return CustomersViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CustomersViewHolder, position: Int) {
        val currentItem = customerList[position]
        holder.nameTextView.text = currentItem.name
        holder.customerLastNameTextView.text = currentItem.lastName

        holder.itemView.setOnClickListener {
            showDescriptionPopup(currentItem.phoneNumber, holder.itemView.context)
        }
    }

    private fun showDescriptionPopup(description: String, context: Context) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Номер телефону клієнта")
        builder.setMessage(description)
        builder.setPositiveButton("Закрити") { dialog, _ ->
            dialog.dismiss()
        }
        builder.create().show()
    }

    override fun getItemCount(): Int {
        return customerList.size
    }


    fun addItem(customer: Manager) {
        customerList.add(customer)
        notifyDataSetChanged()
    }
}