package com.example.onomazwopragmaproject

import android.content.DialogInterface
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.AsyncTask
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.onomazwopragmaproject.GlobalsActivity.Companion.database
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import java.util.*


// A list of categories names included in the current game (Proswpo, Pragma, Futo etc) as strings. One card in recyclerview for each category
var categories: MutableList<String> = mutableListOf()
var categories_image: MutableList<Drawable> = mutableListOf()
var categories_background: MutableList<Drawable> = mutableListOf()
var roomID: String = ""
var memberID: String = ""


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


class GameActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        // Get roomID
        roomID = intent.getStringExtra("EXTRA_ROOM_ID").toString()
        memberID = intent.getStringExtra("EXTRA_MEMBER_ID").toString()

        DatabaseAsyncTask(this).execute()

        Log.d("categories", categories.toString())

    }

    fun initializeCategories(){
        if(categories.contains("Όνομα")) {

            categories_image.add(resources.getDrawable(R.drawable.name_image))
            categories_background.add(resources.getDrawable(R.drawable.name_background))
        }
        if (categories.contains("Ζώο")) {

            categories_image.add(resources.getDrawable(R.drawable.animal_image))
            categories_background.add(resources.getDrawable(R.drawable.animal_background))
        }
        if (categories.contains("Πράγμα")) {

            categories_image.add(resources.getDrawable(R.drawable.thing_image2))
            categories_background.add(resources.getDrawable(R.drawable.thing_background2))
        }
        if (categories.contains("Μέρος")) {

            categories_image.add(resources.getDrawable(R.drawable.place_image))
            categories_background.add(resources.getDrawable(R.drawable.place_background))
        }
        if (categories.contains("Φυτό")) {

            categories_image.add(resources.getDrawable(R.drawable.plant_image))
            categories_background.add(resources.getDrawable(R.drawable.plant_background))
        }

//        categories_image.add(resources.getDrawable(R.drawable.thing_image))
//        categories_background.add(resources.getDrawable(R.drawable.thing_background))


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

        val stopButton = findViewById<ImageButton>(R.id.stop_button)
        Log.d("OUTSIDE LISTENER", "Outside listener")
        database.reference.child("rooms").child(roomID).child("timerisset").addValueEventListener(
            object : ValueEventListener{
                override fun onDataChange(p0: DataSnapshot) {
                    Log.d("p0.value is", ": ${p0.value}. Equal to ? ${p0.value.toString() == "0"}")
                    if (p0.value != null) {
                        stopButton.setOnClickListener(null)
                        findViewById<TextView>(R.id.timer).text = p0.value.toString()
                    }
                    if (p0.value.toString() == "0"){
                        Log.d("INSIDE LISTENER", "in the 'stop pressed' listener")
                        database.reference.child("rooms").child(roomID).child("timerisset").removeEventListener(this)
                        val newIntent = Intent(this@GameActivity, EndOfGameActivity::class.java)
                        uploadAnswers()
                        newIntent.putStringArrayListExtra("CATEGORIES_EXTRA", ArrayList(categories))
                        newIntent.putStringArrayListExtra("EXTRA_MEMBERS_LIST", intent.getStringArrayListExtra("EXTRA_MEMBERS_LIST"))
                        Log.d("MEMBERSLIST", "game: ${intent.getStringArrayListExtra("EXTRA_MEMBERS_LIST")}")
                        newIntent.putExtra("MEMBER_ID_EXTRA", memberID.toString())
                        newIntent.putExtra("ROOM_ID_EXTRA", roomID.toString())

                        Log.d("CategoriesGameAct", "Categories: $categories, and as ArrayList: ${ArrayList(categories)}")
                        startActivity(newIntent)
                        Log.d("Time is up!", "Did the answers get to the database?")
//                        // Emptying these for the next time the activity runs
//                        categories = mutableListOf()
//                        categories_image = mutableListOf()
//                        categories_background = mutableListOf()
                    }
                    Log.d("TESTSTS", "p0.value: ${p0.value}")
                }

                override fun onCancelled(p0: DatabaseError) {
                    TODO("Not yet implemented")
                }
            }
        )
        stopButton.setOnClickListener {

            val countDownTimer: CountDownTimer = object : CountDownTimer(10 * 1000, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    findViewById<TextView>(R.id.timer).text = "${millisUntilFinished / 1000}"
                    database.reference.child("rooms").child(roomID).child("timerisset").setValue(millisUntilFinished / 1000)
                }

                override fun onFinish() {
                    findViewById<TextView>(R.id.timer).text = "0"
//                    val newIntent = Intent(this@GameActivity, EndOfGameActivity::class.java)
//                    uploadAnswers()
//                    newIntent.putStringArrayListExtra("CATEGORIES_EXTRA", ArrayList(categories))
//                    newIntent.putExtra("MEMBER_ID_EXTRA", memberID.toString())
//                    newIntent.putExtra("ROOM_ID_EXTRA", roomID.toString())
//                    Log.d("CategoriesGameAct", "Categories: $categories, and as ArrayList: ${ArrayList(categories)}")
//                    startActivity(newIntent)
                }
            }
            countDownTimer.start()
        }

    }

    private fun uploadAnswers(){
        Log.d(
            "Upload Tests",
            "recyclerView.layoutmanager!!.itemcounte: ${recyclerView.layoutManager!!.itemCount}"
        )
        for (position in 0 until recyclerView.layoutManager!!.itemCount) {
            Log.d("Upload Tests", "position: $position")
            val currentViewCategory = recyclerView.layoutManager!!
                .findViewByPosition(position)!!
                .findViewById<com.google.android.material.textview.MaterialTextView>(R.id.category_name)
            val currentViewInput = recyclerView.layoutManager!!
                .findViewByPosition(position)!!
                .findViewById<EditText>(R.id.category_user_input)
            Log.d("Upload Tests", "currentView text: ${currentViewInput.text.toString()}")
            database.reference.child("rooms").child(roomID).child("members").child(memberID)
                .child(currentViewCategory.text.toString())
                .setValue(currentViewInput.text.toString())
        }
    }

    override fun onBackPressed() {

        // Create the object of AlertDialog Builder class
        val builder: android.app.AlertDialog.Builder = android.app.AlertDialog.Builder(this@GameActivity)

        // Set Cancelable false for when the user clicks on the outside the Dialog Box then it will remain show
        builder.setCancelable(false)


        // Set the message show for the Alert time
        builder.setMessage("Αμα πας πίσω θα βγείς απο το παιχνίδι και απο το δωμάτιο!")

            // Set Alert Title
        builder.setTitle("ΕΠ!")

        builder
            .setPositiveButton(
                "Θέλω να φύγω! :(",
                DialogInterface.OnClickListener { dialog, which -> // When the user click yes button
                    // then app will close
                    database.reference.child("rooms").child(roomID).child("members")
                        .child(memberID).removeValue()

                    // Deleting the local variables for the next time the activity runs
                    categories = mutableListOf()
                    categories_background = mutableListOf()
                    categories_image = mutableListOf()

                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                })

        builder
            .setNegativeButton(
                "Θέλω να μείνω τελικα! :)",
                DialogInterface.OnClickListener { dialog, which -> // If user click no
                    // then dialog box is canceled.
                    dialog.cancel()
                })


        // Show the Alert Dialog box
        builder.create()?.show()
    }


}

class DatabaseAsyncTask(private val gameActivity: GameActivity) : AsyncTask<Void, Void, String>() {

    override fun doInBackground(vararg params: Void?): String {
        val myListener = object : ChildEventListener {
            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                // We want this to run for *each* child. Does it tho?
                categories.add(p0.key.toString())
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
        database.reference.child("rooms").child(roomID).child("categories").addChildEventListener(myListener)
        // Block so that 'categories' is filled
        Thread.sleep(1000)
        database.reference.child("rooms").child(roomID).child("categories").removeEventListener(myListener)
        return("Maybe OK")
    }

    override fun onPostExecute(result: String?) {
        super.onPostExecute(result)
        gameActivity.initializeCategories()
        Log.d("OnPostExe", "Categories here is: $categories")
    }

}