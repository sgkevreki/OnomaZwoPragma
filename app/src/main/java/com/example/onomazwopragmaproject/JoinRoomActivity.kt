package com.example.onomazwopragmaproject

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.onomazwopragmaproject.GlobalsActivity.Companion.database
import com.google.firebase.database.*


class JoinRoomActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join_room)

        val roomEdittext = findViewById<EditText>(R.id.RoomCode)

        val nameUser = findViewById<EditText>(R.id.username)

        val playButton = findViewById<ImageButton>(R.id.playButton)

        playButton.setOnClickListener {

            if (roomEdittext.text.isNullOrBlank()) {
                Toast.makeText(
                    applicationContext,
                    "Πρέπει να βάλεις κωδικό δωματίου!",
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
            else if (nameUser.text.isNullOrBlank()) {

                Toast.makeText(
                    applicationContext,
                    "Πρέπει να βάλεις ψευδώνυμο!",
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
            else  {

                val ref = database.getReference("rooms")

                val intent = Intent(this, RoomActivity::class.java)

                val user = Member(nameUser.text.toString())

                // Create Member()
                database.reference.child("rooms").child(roomEdittext.text.toString()).child("members")
                    .child(user.memberId).setValue(user)


                intent.putExtra("activity","join");
                intent.putExtra("EXTRA_ROOM_ID", roomEdittext.text.toString())
                intent.putExtra("EXTRA_MEMBER_ID", user.memberId)


                ref.addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {

                        if (!dataSnapshot.hasChild(roomEdittext.text.toString())) {

                            Toast.makeText(
                                applicationContext,
                                "Δεν υπάρχει το δωμάτιο!",
                                Toast.LENGTH_SHORT
                            )
                                .show()

                        }

                        else {
                            Log.d("Join", "RoomId: $roomEdittext.text.toString()")

                            startActivity(intent)

                        }

                    }


                    override fun onCancelled(error: DatabaseError) {
                        // Failed to read value
                        Log.w("join", "Failed to read value.", error.toException())
                    }

                })

            }

        }
    }
}