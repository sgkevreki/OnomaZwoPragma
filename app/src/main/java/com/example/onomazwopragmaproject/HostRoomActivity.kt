package com.example.onomazwopragmaproject

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.onomazwopragmaproject.GlobalsActivity.Companion.roomIdLength
import com.example.onomazwopragmaproject.GlobalsActivity.Companion.roomIdSource
import com.example.onomazwopragmaproject.GlobalsActivity.Companion.database
import com.google.firebase.database.*

class HostRoomActivity : AppCompatActivity() {

    private lateinit var roomId: String
    private lateinit var roomReference: DatabaseReference
    //GameActivity Categories!
    var categoriesList2: MutableList<String> = mutableListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_host_room)

        val userCategory1 = findViewById<EditText>(R.id.category1).text.toString()
//        val userCategory2 = findViewById<EditText>(R.id.category2).text.toString()
//        val userCategory3 = findViewById<EditText>(R.id.category3).text.toString()
//        val userCategory4 = findViewById<EditText>(R.id.category4).text.toString()
//        val userCategory5 = findViewById<EditText>(R.id.category5).text.toString()
//        val userCategory6 = findViewById<EditText>(R.id.category6).text.toString()


        val roomButton = findViewById<ImageButton>(R.id.the_room)

        val nameHost = findViewById<EditText>(R.id.nickname)

        //The user gets to decide what categories they want
        //The decision changes every time the user presses the category button
        clickListenerName()
        clickListenerAnimal()
        clickListenerThing()
        clickListenerPlace()
        clickListenerPlant()

        //The users also get to add their own categories!
//        categoriesList2.add(userCategory1)
//        Log.d("MY LIST", "${categoriesList2.toString()}")


        roomButton.setOnClickListener {

            if (nameHost.text.isNullOrBlank()) {
                Toast.makeText(
                    applicationContext,
                    "Πρέπει να βάλεις Ψευδώνυμο!",
                    Toast.LENGTH_SHORT
                )
                    .show()
            } else {


                val hostUser = Member(nameHost.text.toString())
                createRoom()
                // and then go to RoomActivity
                val intent = Intent(this, RoomActivity::class.java)
                intent.putExtra("activity", "host");
                intent.putExtra("EXTRA_ROOM_ID", roomId)
                intent.putExtra("EXTRA_MEMBER_ID", hostUser.memberId)

                intent.putExtra("categoriesList2",ArrayList(categoriesList2))

                Log.d("Host", "roomId: $roomId")
                startActivity(intent)
            }

        }
    }



    private fun clickListenerName() {
        val nameB = findViewById<ImageButton>(R.id.name_b)

        nameB.setOnClickListener {
            categoriesList2.add("Όνομα")
            nameB.setImageResource(R.drawable.name_b_pressed2)
            nameB.setOnClickListener {
                categoriesList2.remove("Όνομα")
                nameB.setImageResource(R.drawable.name_b3)
                clickListenerName()
            }
        }
    }
    private fun clickListenerAnimal() {
        val nameB = findViewById<ImageButton>(R.id.animal_b)

        nameB.setOnClickListener {
            categoriesList2.add("Αζώο")
            nameB.setImageResource(R.drawable.animal_b_pressed)
            nameB.setOnClickListener {
                categoriesList2.remove("Αζώο")
                nameB.setImageResource(R.drawable.animal_b)
                clickListenerAnimal()
            }
        }
    }
    private fun clickListenerThing() {
        val thingB = findViewById<ImageButton>(R.id.thing_b)
        thingB.setOnClickListener {
            categoriesList2.add("Πράγμα")
            thingB.setImageResource(R.drawable.thing_b_pressed)
            thingB.setOnClickListener {
                categoriesList2.remove("Πράγμα")
                thingB.setImageResource(R.drawable.thing_b)
                clickListenerThing()
            }
        }
    }
    private fun clickListenerPlace() {
        val placeB = findViewById<ImageButton>(R.id.place_b)
        placeB.setOnClickListener {
            categoriesList2.add("Μέρος")
            placeB.setImageResource(R.drawable.place_b_pressed)
            placeB.setOnClickListener {
                categoriesList2.remove("Μέρος")
                placeB.setImageResource(R.drawable.place_b)
                clickListenerPlace()
            }
        }
    }
    private fun clickListenerPlant() {
        val placeB = findViewById<ImageButton>(R.id.plant_b)
        placeB.setOnClickListener {
            categoriesList2.add("Φυτό")
            placeB.setImageResource(R.drawable.plant_b_pressed)
            placeB.setOnClickListener {
                categoriesList2.remove("Φυτό")
                placeB.setImageResource(R.drawable.plant_b)
                clickListenerPlant()
            }
        }
    }
    //private fun clickListenerJob() {
    //    val placeB = findViewById<ImageButton>(R.id.job_b)
    //    placeB.setOnClickListener {
    //        categoriesList2.add("Επάγγελμα")
    //        placeB.setImageResource(R.drawable.job_b_pressed)
    //        placeB.setOnClickListener {
    //            categoriesList2.remove("Επάγγελμα")
    //            placeB.setImageResource(R.drawable.job_b)
    //            clickListenerJob()
    //        }
    //    }
    //}
//private fun clickListenerColor() {
//    val placeB = findViewById<ImageButton>(R.id.color_b)
//    placeB.setOnClickListener {
//        categoriesList2.add("Χρώμα")
//        placeB.setImageResource(R.drawable.color_b_pressed)
//        placeB.setOnClickListener {
//            categoriesList2.remove("Χρώμα")
//            placeB.setImageResource(R.drawable.color_b)
//            clickListenerColor()
//        }
//    }
//}
// private fun clickListenerFood() {
//    val placeB = findViewById<ImageButton>(R.id.food_b)
//    placeB.setOnClickListener {
//        categoriesList2.add("Φαγητό")
//        placeB.setImageResource(R.drawable.food_b_pressed)
//        placeB.setOnClickListener {
//            categoriesList2.remove("Φαγητό")
//            placeB.setImageResource(R.drawable.food_b)
//            clickListenerFood()
//        }
//    }
//}


    private fun createRoom(){
        // Create random roomId
        // TODO: Make sure there are no duplicate roomIDs!
        roomId = (1..roomIdLength).map { roomIdSource.random() }.joinToString("")
        // Get a database reference from the point of the room and beyond (you don't need the upper layers from here!)
        roomReference = database.reference.child("rooms").child(roomId)

        // Create host user Member object

        val nameHost = findViewById<EditText>(R.id.nickname).text.toString()

        var hostUser = Member(nameHost)
        // Add host to room members
        // TODO: Make sure there are no duplicate memberIDs!
        roomReference.child("members").child(hostUser.memberId).setValue(hostUser)

    }

}