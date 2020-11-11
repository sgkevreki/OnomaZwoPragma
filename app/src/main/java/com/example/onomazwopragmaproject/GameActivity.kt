package com.example.onomazwopragmaproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class GameActivity : AppCompatActivity() {

    // A list of categories names included in the current game (Proswpo, Pragma, Futo etc) as strings. One card in recyclerview for each category
    var categories: MutableList<String> = mutableListOf()

    // Initialize references to needed elements:
    // recyclerView -> The actual recyclerView object. You need one of these to show of a list of data.
    // recyclerViewAdapter -> An adapter object that contains 3 core functions (OnCreateViewHolder, OnBindViewHolder and getCount).
    //      It is responsible for creating a cardView for every item in 'categories', and populating them with data.
    //      Must be associated with the recyclerView it will help "run".
    //      You also need one of these for every recyclerView you have.
    // recyclerViewLayoutManager -> An extra object that is a LayoutManager that *I think* is responsible for placing the recyclerview inside the general view of the activity. (May be unnecessary in a constraint layout?)
    private lateinit var recyclerView: RecyclerView
    private lateinit var recyclerViewAdapter: RecyclerView.Adapter<*>
    private lateinit var recyclerViewLayoutManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        // Setting a few names in categories for testing
        categories.add("proswpo")
        categories.add("zwo")
        categories.add("pragma")

        // Create the objects needed
        // Create a Linear Layout Manager
        recyclerViewLayoutManager = LinearLayoutManager(this)
        // And an instance of an Adapter (note that the 'categories' argument must be of the same type declared in the constructor of the GameRecyclerViewAdapter class (duh))
        recyclerViewAdapter = GameRecyclerviewAdapter(categoriesList = categories)

        // Associate firstly the recyclerview layout (R.id.recyclerview) with the object reference (private lateinit var recyclerView)
        recyclerView = findViewById<RecyclerView>(R.id.game_recyclerview)
        // and now associate the layoutManager and adapter with this instance of recyclerView
            .apply{
                layoutManager = recyclerViewLayoutManager
                adapter = recyclerViewAdapter
            }

    }
}