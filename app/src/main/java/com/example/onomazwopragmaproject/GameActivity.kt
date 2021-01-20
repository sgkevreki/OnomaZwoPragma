package com.example.onomazwopragmaproject

import android.graphics.drawable.Drawable
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.onomazwopragmaproject.GlobalsActivity.Companion.database
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

// A list of categories names included in the current game (Proswpo, Pragma, Futo etc) as strings. One card in recyclerview for each category
var categories: MutableList<String> = mutableListOf()
var categoriesList: MutableList<String> = mutableListOf()
var categories_image: MutableList<Drawable> = mutableListOf()
var categories_background: MutableList<Drawable> = mutableListOf()
var roomID: String = ""

class GameActivity : AppCompatActivity() {
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

        // Get roomID
        roomID = intent.getStringExtra("EXTRA_ROOM_ID").toString()


//        DatabaseAsyncTask().execute()

       // categoriesList = intent.getStringArrayListExtra("categoriesList").toMutableList()
        Log.d("categories", categories.toString())
        Log.d("categoriesList", categoriesList.toString())

//        if(categoriesList.contains("Όνομα")) {
//
//            categories_image.add(resources.getDrawable(R.drawable.name_image))
//            categories_background.add(resources.getDrawable(R.drawable.name_background))
//        }


//
//        if(categoriesList.contains("Όνομα")) {
//
//            categories_image.add(resources.getDrawable(R.drawable.name_image))
//            categories_background.add(resources.getDrawable(R.drawable.name_background))
//        }
//        if (categoriesList.contains("Ζώο")) {
//
//            categories_image.add(resources.getDrawable(R.drawable.animal_image))
//            categories_background.add(resources.getDrawable(R.drawable.animal_background))
//        }
//        if (categoriesList.contains("Πράγμα")) {
//
//            categories_image.add(resources.getDrawable(R.drawable.thing_image2))
//            categories_background.add(resources.getDrawable(R.drawable.thing_background2))
//        }
//        if (categoriesList.contains("Μέρος")) {
//
//            categories_image.add(resources.getDrawable(R.drawable.place_image))
//            categories_background.add(resources.getDrawable(R.drawable.place_background))
//        }
//        if (categoriesList.contains("Φυτό")) {
//
//            categories_image.add(resources.getDrawable(R.drawable.plant_image))
//            categories_background.add(resources.getDrawable(R.drawable.plant_background))
//        }
        //for the input categories
        categories_image.add(resources.getDrawable(R.drawable.name_image))
        categories_background.add(resources.getDrawable(R.drawable.name_background))

        categories_image.add(resources.getDrawable(R.drawable.animal_image))
        categories_background.add(resources.getDrawable(R.drawable.animal_background))

        categories_image.add(resources.getDrawable(R.drawable.thing_image))
        categories_background.add(resources.getDrawable(R.drawable.thing_background))



        // Create the objects needed
        // Create a Linear Layout Manager
        recyclerViewLayoutManager = LinearLayoutManager(this)
        // And an instance of an Adapter (note that the 'categories' argument must be of the same type declared in the constructor of the GameRecyclerViewAdapter class (duh))
        recyclerViewAdapter = GameRecyclerviewAdapter(
            categoriesList = categories,
            categoriesList2 = categories_image,
            categoriesList3 = categories_background
        )

        // Associate firstly the recyclerview layout (R.id.recyclerview) with the object reference (private lateinit var recyclerView)
        recyclerView = findViewById<RecyclerView>(R.id.game_recyclerview)
        // and now associate the layoutManager and adapter with this instance of recyclerView
            .apply{
                layoutManager = recyclerViewLayoutManager
                adapter = recyclerViewAdapter
            }

    }
}

class DatabaseAsyncTask : AsyncTask<Int, Void, String>() {
    override fun doInBackground(vararg params: Int?): String {
        database.reference.child("rooms").child(roomID).child("categories").addChildEventListener(
            object : ChildEventListener {
                override fun onChildAdded(p0: DataSnapshot, p1: String?) {

                    // We want this to run for *each* child. Does it tho?
                    Log.d("ok", "jchkfg")
                    Log.d("P0", "${p0.key}, ${p0.children}")
                    categories.add(p0.key.toString())
                    Log.d("categ OnChildAdded", categories.toString())
                    if (parw == halsrh)
                    {

                    }

                }

                override fun onChildChanged(p0: DataSnapshot, p1: String?) {
                    TODO("Not yet implemented")
                }

                override fun onChildRemoved(p0: DataSnapshot) {
                    TODO("Not yet implemented")
                }

                override fun onChildMoved(p0: DataSnapshot, p1: String?) {
                    TODO("Not yet implemented")
                }

                override fun onCancelled(p0: DatabaseError) {
                    TODO("Not yet implemented")
                }
            }
        )
        return("Maybe not ok :(")
    }

    override fun onPostExecute(result: String?) {
        super.onPostExecute(result)
    }
}