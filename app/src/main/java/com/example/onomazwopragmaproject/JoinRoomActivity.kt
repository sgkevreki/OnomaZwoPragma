package com.example.onomazwopragmaproject

import android.content.Intent
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




        val playButton = findViewById<ImageButton>(R.id.playButton)
        playButton.setOnClickListener {
            val roomIdInput = roomEdittext.text.toString()
            Log.d("Join", "RoomId: $roomIdInput")
            // Create Member()
            var user = Member("IAMGUEST")
            database.reference.child("rooms").child(roomIdInput).child(user.memberId).setValue(user)
            var intent = Intent(this, RoomActivity::class.java)
            intent.putExtra("EXTRA_ROOM_ID", roomIdInput)
            intent.putExtra("EXTRA_IS_HOST", false)
            startActivity(intent)


        }

    }
}