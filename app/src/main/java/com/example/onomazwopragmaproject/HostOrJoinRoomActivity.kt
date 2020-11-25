package com.example.onomazwopragmaproject

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity


class HostOrJoinRoomActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_host_or_join_room)

      //  var database_test_text = findViewById<TextView>(R.id.database_test)
        val hostButton = findViewById<ImageButton>(R.id.host_room)
        val joinButton = findViewById<ImageButton>(R.id.join_room)

//
//        // TEST THE DATABASE
//        val database = FirebaseDatabase.getInstance()
//        val myRef = database.getReference("message")
//
//        myRef.setValue("Hello, World!")
//
//        // Read from the database
//        myRef.addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(dataSnapshot: DataSnapshot) {
//                // This method is called once with the initial value and again
//                // whenever data at this location is updated.
//                val value = dataSnapshot.getValue(String::class.java)!!
//                Log.d("hostorjoin", "Value is: $value")
//                database_test_text.text = value
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                // Failed to read value
//                Log.w("hostorjoin", "Failed to read value.", error.toException())
//            }
//        })
//
//        host_button.setOnClickListener {
//            myRef.setValue((0..10).random().toString())
//        }

        hostButton.setOnClickListener{
            val intent = Intent(this, HostRoomActivity::class.java)
            startActivity(intent)
        }

        joinButton.setOnClickListener{
            val intent = Intent(this, JoinRoomActivity::class.java)
            startActivity(intent)
        }

    }
}