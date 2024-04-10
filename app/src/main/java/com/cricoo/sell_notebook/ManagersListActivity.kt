package com.cricoo.sell_notebook

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ManagersListActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var customerAdapter: ManagerAdapter

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_managers_list)

        recyclerView = findViewById(R.id.recyclerViewCustomers)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val customerLists = mutableListOf(
            Manager(1, "Микола", "Лесюк", "0666834648"),
            Manager(2, "Володимир", "Бойчук", "0507328234"),
            Manager(3, "Арсен", "Чуйко", "0992314234")
        )

        customerAdapter = ManagerAdapter(customerLists)

        recyclerView.adapter = customerAdapter

        val buttonAddCustomers: Button = findViewById(R.id.buttonAddCustomers)
        buttonAddCustomers.setOnClickListener {
            val dialog = AddManager { dish ->
                customerAdapter.addItem(dish)
            }
            dialog.show(supportFragmentManager, "AddDishDialog")
        }
    }
}