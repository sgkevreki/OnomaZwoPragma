package com.example.onomazwopragmaproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import com.google.firebase.database.*
import com.example.onomazwopragmaproject.GlobalsActivity.Companion.roomIdLength

class HostRoomActivity : AppCompatActivity() {

    private lateinit var roomId: String
    private lateinit var roomReference: DatabaseReference
    private val database = FirebaseDatabase.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_host_room)

        var test_button = findViewById<Button>(R.id.test_room)

        var database_test_text = findViewById<TextView>(R.id.database_test)

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
                database_test_text.text = value
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w("hostorjoin", "Failed to read value.", error.toException())
            }
        })

        test_button.setOnClickListener {
//            myRef.setValue((0..10).random().toString())
            createRoom()

        }

    }

    fun createRoom(){

        roomId = "ABCD"
        roomReference = database.getReference(roomId)
        // Add host to room members
        roomReference.child("members").child("memberID").setValue("HostPlayerIDHere!")
        // TESTS
        roomReference.child("settings").child("thisIsATestOption").setValue("ThisIsSoTrue!")

    }
}