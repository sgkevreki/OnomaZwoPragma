package com.example.onomazwopragmaproject

import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.example.onomazwopragmaproject.GlobalsActivity.Companion.database
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener


class JoinRoomActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join_room)

        val roomEdittext = findViewById<EditText>(R.id.RoomCode)
        val roomIdInput = roomEdittext.text

        val playButton = findViewById<ImageButton>(R.id.playButton)
        playButton.setOnClickListener {

            /*
            var intent: Intent = Intent(this, GameActivity::class.java)
            startActivity(intent)
            Let's create a listener on the Room level so everytime anything that is a child of the Room layer changes, we get notified.
            We handle the changes of everything inside this one adapter
            */
            val roomReference = database.reference.child(roomIdInput.toString())
                roomReference.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    /*
                    This method is called once with the initial value and again
                    whenever data at this location is updated.
                    */
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