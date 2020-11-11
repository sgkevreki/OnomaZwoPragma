package com.example.onomazwopragmaproject

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class HostOrJoinRoomActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_host_or_join_room)

        var database_test_text = findViewById<TextView>(R.id.database_test)
        var host_button = findViewById<ImageButton>(R.id.host_room)
        var join_button = findViewById<ImageButton>(R.id.join_room)


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

        host_button.setOnClickListener {
            myRef.setValue((0..10).random().toString())
        }

        join_button.setOnClickListener{
            var intent: Intent = Intent(this, GameActivity::class.java)
            startActivity(intent)
        }

    }
}