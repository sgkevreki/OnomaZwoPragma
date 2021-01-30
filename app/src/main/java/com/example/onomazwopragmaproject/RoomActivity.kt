package com.example.onomazwopragmaproject

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
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


class RoomActivity : AppCompatActivity() {

    var membersList: MutableList<String> = mutableListOf()
    // Categories must be set by this activity, so we can create the categories we need in this activity!
    // categoriesList is probably passed as an argument to the next activity, `GameActivity`
    var categoriesList: MutableList<String> = mutableListOf()
    private lateinit var roomId: String
    private lateinit var memberId: String
    private lateinit var type: String

    private lateinit var recyclerView: RecyclerView
    private lateinit var recyclerViewAdapter: RecyclerView.Adapter<*>
    private lateinit var recyclerViewLayoutManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_room)


        // Get roomId, needed in next function, `handleCategories`
        roomId = intent.getStringExtra("EXTRA_ROOM_ID").toString()
        memberId = intent.getStringExtra("EXTRA_MEMBER_ID").toString()
        type = intent.getStringExtra("activity")


        //Display the Room Id so all the players can enter.
        val roomName: TextView = findViewById(R.id.roomName)
        roomName.text = "Δωμάτιο  " + roomId + "!"


        // Function that handles categories
        handleCategories()


        Log.d("Room", "roomId: $roomId")

        // Attach a listener to db.roomid.members, so you can get the list of members joining
        val myListener = object : ChildEventListener {
            override fun onChildAdded(dataSnapshot: DataSnapshot, p1: String?) {
                // OMG this is called once for every item in member list!
                // member added
                var userObject = dataSnapshot.getValue(Member::class.java)
                membersList.add(userObject!!.name)
                recyclerViewAdapter.notifyDataSetChanged()
            }

            override fun onChildChanged(dataSnapshot: DataSnapshot, p1: String?) {
                var userObject = dataSnapshot.getValue(Member::class.java)
                membersList.remove(userObject!!.name)
                recyclerViewAdapter.notifyDataSetChanged()
            }

            override fun onChildRemoved(dataSnapshot: DataSnapshot) {
                var userObject = dataSnapshot.getValue(Member::class.java)
                membersList.remove(userObject!!.name)
                recyclerViewAdapter.notifyDataSetChanged()
            }

            override fun onChildMoved(p0: DataSnapshot, p1: String?) {
                TODO("Not yet implemented")
            }

            override fun onCancelled(p0: DatabaseError) {
                TODO("Not yet implemented")
            }
        }
        database.reference.child("rooms").child(roomId).child("members").addChildEventListener(myListener)


        // Regular Recyclerview initiation stuff
        recyclerViewLayoutManager = LinearLayoutManager(this)
        recyclerViewAdapter = RoomMembersRecyclerviewAdapter(membersList)

        recyclerView = findViewById<RecyclerView>(R.id.room_members_recyclerview).apply{
                layoutManager = recyclerViewLayoutManager
                adapter = recyclerViewAdapter
            }


        val buttonPlayGame = findViewById<Button>(R.id.play_game)
        if (type == "join"){
            buttonPlayGame.visibility = View.GONE
            val myStartGameListener = object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
//                    val value = dataSnapshot.getValue(String::class.java)!!
//                    Log.d(TAG, "Value is: $value")
                    Log.d("datasnapshot is", "${ dataSnapshot.key}, ${dataSnapshot.value}")
                    if (dataSnapshot.value == true) {
                        database.reference.child("rooms").child(roomId).child("startflag")
                            .removeEventListener(this)
                        val intent = Intent(applicationContext, GameActivity::class.java)
                        intent.putExtra("EXTRA_ROOM_ID", roomId)
                        intent.putExtra("EXTRA_MEMBER_ID", memberId)
                        Log.d(
                            "ROOM",
                            "categories list as arrayList: ${categoriesList as ArrayList<String>?} \n and classic: $categoriesList"
                        )
                        Log.d("ROOMLISTENER", "################################# THIS IS THE LISTENER THAT STARTS THE GAME ACTIVITY #########################################################################")
                        // Remove the listeners!
                        database.reference.child("rooms").child(roomId).child("members").removeEventListener(myListener)
                        database.reference.child("rooms").child(roomId).child("startflag").removeEventListener(this)
                        Log.d("ROOMLISTENER", "################## UNDER HERE I SHALL NOT SEE THE OTHE ### AGAIN ########################")
                        startActivity(intent)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    // Failed to read value
                    Log.w(
                        "Oops!",
                        "Failed to read value. In startflag reader.",
                        error.toException()
                    )
                }
            }
            database.reference.child("rooms").child(roomId).child("startflag").addValueEventListener(myStartGameListener)
        }
        else {
            buttonPlayGame.setOnClickListener {
                database.reference.child("rooms").child(roomId).child("startflag").setValue(true)
                val intent = Intent(this, GameActivity::class.java)
                intent.putExtra("EXTRA_ROOM_ID", roomId)
                intent.putExtra("EXTRA_MEMBER_ID", memberId)
                Log.d(
                    "ROOM",
                    "categories list as arrayList: ${categoriesList as ArrayList<String>?} \n and classic: $categoriesList"
                )
                startActivity(intent)
            }
        }

    }

    private fun handleCategories() {

        if (type == "host") {
            categoriesList = intent.getStringArrayListExtra("categoriesList2").toMutableList()


            // Create database field for each category under the "room" child.
            for (element in categoriesList) {
                database.reference.child("rooms").child(roomId).child("categories").child(element)
                    .setValue(true)
            }
            database.reference.child("rooms").child(roomId).child("categsize")
                .setValue(categoriesList.size)
        }
    }


    //what happens when u press back?
    override fun onBackPressed() {

        // Create the object of AlertDialog Builder class
        val builder: android.app.AlertDialog.Builder = android.app.AlertDialog.Builder(this@RoomActivity)

        // Set Cancelable false for when the user clicks on the outside the Dialog Box then it will remain show
        builder.setCancelable(false)


        if (type == "host") {

            // Set the message show for the Alert time
            builder.setMessage("Άμα πας πίσω θα διαγράψεις το δωμάτιο!")

            // Set Alert Title
            builder.setTitle("ΕΠ!")

            builder
                .setPositiveButton(
                    "Διέγραψε το! :(",
                    DialogInterface.OnClickListener { dialog, which -> // When the user click yes button
                        // then app will close
                        database.reference.child("rooms").child(roomId).removeValue()
                        finish()
                    })

            builder
                .setNegativeButton(
                    "Θέλω να μείνω τελικα! :)",
                    DialogInterface.OnClickListener { dialog, which -> // If user click no
                        // then dialog box is canceled.
                        dialog.cancel()
                    })
        }


        if (type == "join") {

            // Set the message show for the Alert time
            builder.setMessage("Άμα πας πίσω θα διαγραφτείς απο το δωμάτιο!")

            // Set Alert Title
            builder.setTitle("ΕΠ!")

            builder
                .setPositiveButton(
                    "Διέγραψε με! :(",
                    DialogInterface.OnClickListener { dialog, which -> // When the user click yes button
                        // then app will close2

                        database.reference.child("rooms").child(roomId).child("members")
                            .child(memberId).removeValue()

                        finish()
                    })

            builder
                .setNegativeButton(
                    "Θέλω να μείνω τελικα! :)",
                    DialogInterface.OnClickListener { dialog, which -> // If user click no
                        // then dialog box is canceled.
                        dialog.cancel()
                    })

        }
        // Show the Alert Dialog box
        builder.create()?.show()
    }

}