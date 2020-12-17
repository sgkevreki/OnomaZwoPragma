package com.example.onomazwopragmaproject

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
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
        database.reference.child("rooms").child(roomId).child("members").addChildEventListener(
            object : ChildEventListener {
                override fun onChildAdded(dataSnapshot: DataSnapshot, p1: String?) {
                    // OMG this is called once for every item in member list!
                    // member added
                    Log.d(
                        "Room",
                        "dataSnapshot.key: ${dataSnapshot.key?.get(0)}, dataSnapshot.value: ${
                            dataSnapshot.getValue(
                                Member::class.java
                            )
                        }, dataSnapsot.value.hashCode?: ${dataSnapshot.value}"
                    )
                    var userObject = dataSnapshot.getValue(Member::class.java)
                    Log.d("Room", "userObject: $userObject")
                    membersList.add(userObject!!.name)
                    Log.d("Room", "userObject!!.name: ${userObject!!.name}")
                    recyclerViewAdapter.notifyDataSetChanged()
                }

                override fun onChildChanged(dataSnapshot: DataSnapshot, p1: String?) {
                    Log.d(
                        "Room",
                        "dataSnapshot.key: ${dataSnapshot.key?.get(0)}, dataSnapshot.value: ${
                            dataSnapshot.getValue(
                                Member::class.java
                            )
                        }, dataSnapsot.value.hashCode?: ${dataSnapshot.value}"
                    )
                    var userObject = dataSnapshot.getValue(Member::class.java)
                    Log.d("Room", "userObject: $userObject")
                    membersList.remove(userObject!!.name)
                    Log.d("Room", "userObject!!.name: ${userObject!!.name}")
                    recyclerViewAdapter.notifyDataSetChanged()
                }

                override fun onChildRemoved(dataSnapshot: DataSnapshot) {
                    Log.d(
                        "Room",
                        "dataSnapshot.key: ${dataSnapshot.key?.get(0)}, dataSnapshot.value: ${
                            dataSnapshot.getValue(
                                Member::class.java
                            )
                        }, dataSnapsot2.value.hashCode?: ${dataSnapshot.value}"
                    )
                    var userObject = dataSnapshot.getValue(Member::class.java)
                    Log.d("Room", "userObject: $userObject")
                    membersList.remove(userObject!!.name)
                    Log.d("Room", "userObject!!.name: ${userObject!!.name}")
                    recyclerViewAdapter.notifyDataSetChanged()
                }

                override fun onChildMoved(p0: DataSnapshot, p1: String?) {
                    TODO("Not yet implemented")
                }

                override fun onCancelled(p0: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })


        // Regular Recyclerview initiation stuff
        recyclerViewLayoutManager = LinearLayoutManager(this)
        recyclerViewAdapter = RoomMembersRecyclerviewAdapter(membersList)

        recyclerView = findViewById<RecyclerView>(R.id.room_members_recyclerview).apply{
                layoutManager = recyclerViewLayoutManager
                adapter = recyclerViewAdapter
            }


        val buttonPlayGame = findViewById<Button>(R.id.play_game)
        buttonPlayGame.setOnClickListener{
            val intent = Intent(this, GameActivity::class.java)
            intent.putExtra("EXTRA_ROOM_ID", roomId)
            Log.d(
                "ROOM",
                "categories list as arrayList: ${categoriesList as ArrayList<String>?} \n and classic: $categoriesList"
            )
            startActivity(intent)
        }

    }

    private fun handleCategories() {
        // Actually we will get the categories from the 'settings' activity or wherever the settings are, so this is only temporary to test the application with some categories!
        categoriesList.add("Όνομα")
        categoriesList.add("Ζώο")
        categoriesList.add("Πράγμα")
        categoriesList.add("Μέρος")

        // Create database field for each category under the "room" child.
        for (element in categoriesList) {
            database.reference.child("rooms").child(roomId).child("categories").child(element).setValue(true)
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