package com.example.onomazwopragmaproject

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.onomazwopragmaproject.GlobalsActivity.Companion.roomIdLength
import com.example.onomazwopragmaproject.GlobalsActivity.Companion.roomIdSource
import com.example.onomazwopragmaproject.GlobalsActivity.Companion.database
import com.google.firebase.database.*

class HostRoomActivity : AppCompatActivity() {

    private lateinit var roomId: String
    private lateinit var roomReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_host_room)

        val testButton = findViewById<Button>(R.id.test_room)

        val databaseTestText = findViewById<TextView>(R.id.database_test)

        // TEST THE DATABASE
        val myRef = database.getReference("message")

        myRef.setValue("Hello, World!")

        // Read from the database
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                val value = dataSnapshot.getValue(String::class.java)!!
                Log.d("hostorjoin", "Value is: $value")
                databaseTestText.text = value
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w("hostorjoin", "Failed to read value.", error.toException())
            }
        })

        testButton.setOnClickListener {
            createRoom()
            // and then go to RoomActivity
            Log.d("Host", "Host test output")
            val intent = Intent(this, RoomActivity::class.java)
            intent.putExtra("EXTRA_ROOM_ID", roomId)
            Log.d("Host", "roomId: $roomId")
            startActivity(intent)
        }

    }

    private fun createRoom(){
        // Create random roomId
        // TODO: Make sure there are no duplicate roomIDs!
        roomId = (1..roomIdLength).map { roomIdSource.random() }.joinToString("")
        // Get a database reference from the point of the room and beyond (you don't need the upper layers from here!)
        roomReference = database.getReference(roomId)
        // Add host to room members
        // TODO: Make sure there are no duplicate memberIDs!
        roomReference.child("members").setValue("HostPlayerIDHere!")
        // TESTS
        roomReference.child("settings").child("thisIsATestOption").setValue("ThisIsSoTrue!")

    }
}