package com.example.onomazwopragmaproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class HostRoomActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_host_room)

        var test_button = findViewById<Button>(R.id.test_room)

        var database_test_text = findViewById<TextView>(R.id.database_test)

        // TEST THE DATABASE
        val database = FirebaseDatabase.getInstance()
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
            var roomId = database.getReference("ABDC")
            roomId.child("members").child("memberID").setValue("HostPlayerIDHere!")
            roomId.child("settings").child("thisIsATestOption").setValue("ThisIsSoTrue!")
        }

    }
}