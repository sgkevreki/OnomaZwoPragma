package com.example.onomazwopragmaproject

import android.os.Bundle
import android.text.BoringLayout
import android.util.Log
import android.widget.EditText
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class JoinRoomActivity : AppCompatActivity() {

    var database = FirebaseDatabase.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join_room)

        var roomEdittext = findViewById<EditText>(R.id.RoomCode)
        var roomInput = roomEdittext.text

        var playButton = findViewById<ImageButton>(R.id.playButton)
        playButton.setOnClickListener {
//            var intent: Intent = Intent(this, GameActivity::class.java)
//            startActivity(intent)
                // Let's create a listener on the Room level so everytime anything that is a child
                // of the Room layer changes, we get notified.
                // We handle the changes of everything inside this one adapter

                var room_reference = database.reference.child("ABDC")
//                    .child("settings")
//                    .child("thisIsATestOption")
                room_reference.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    val value = dataSnapshot.value
                    Log.d("Setting", "Setting is: $value")
                }

                override fun onCancelled(error: DatabaseError) {
                    // Failed to read value
                    Log.w("Setting", "Failed to read value.", error.toException())
                }
            })

        }

    }
}