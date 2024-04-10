package com.cricoo.sell_notebook

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class GadgetsListActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var apartamentsAdapter: GadgetsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gadgets_list)

        recyclerView = findViewById(R.id.recyclerViewApartaments)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val apartamentsLists = mutableListOf(
            Gadgets(1, "1", "Bandery 13", "43000$", "photo_url_1", "40"),
            Gadgets(2, "2", "Bandery 13", "60000$", "photo_url_2", "70"),
            Gadgets(3, "3", "Bandery 13", "90000$", "photo_url_3", "120")
        )

        apartamentsAdapter = GadgetsAdapter(apartamentsLists)

        recyclerView.adapter = apartamentsAdapter

        val buttonAddRestaurant: Button = findViewById(R.id.buttonAddApartaments)
        buttonAddRestaurant.setOnClickListener {
            val dialog = AddGadgets { restaurant ->
                apartamentsAdapter.addItem(restaurant)
            }
            dialog.show(supportFragmentManager, "AddRestaurantDialog")
        }
    }
}