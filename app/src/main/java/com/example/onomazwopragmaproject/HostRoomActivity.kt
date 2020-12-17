package com.example.onomazwopragmaproject

import android.content.Intent
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



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_host_room)

        val testButton = findViewById<ImageButton>(R.id.the_room)

        val databaseTestText = findViewById<TextView>(R.id.database_test)

        val nameHost = findViewById<EditText>(R.id.nickname)


        testButton.setOnClickListener {

            if (nameHost.text.isNullOrBlank()) {
                Toast.makeText(
                    applicationContext,
                    "Πρέπει να βάλεις Ψευδώνυμο!",
                    Toast.LENGTH_SHORT
                )
                    .show()
            } else {

                var hostUser = Member(nameHost.text.toString())
                createRoom()
                // and then go to RoomActivity
                val intent = Intent(this, RoomActivity::class.java)
                intent.putExtra("activity", "host");
                intent.putExtra("EXTRA_ROOM_ID", roomId)
                intent.putExtra("EXTRA_MEMBER_ID", hostUser.memberId)
                Log.d("Host", "roomId: $roomId")
                startActivity(intent)
            }

        }
    }



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