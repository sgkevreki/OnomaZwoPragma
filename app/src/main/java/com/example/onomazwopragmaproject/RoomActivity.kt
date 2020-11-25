package com.example.onomazwopragmaproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.onomazwopragmaproject.GlobalsActivity.Companion.database
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class RoomActivity : AppCompatActivity() {

    var membersList: MutableList<String> = mutableListOf()
    private lateinit var roomId: String


    private lateinit var recyclerView: RecyclerView
    private lateinit var recyclerViewAdapter: RecyclerView.Adapter<*>
    private lateinit var recyclerViewLayoutManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_room)

        roomId = intent.getStringExtra("EXTRA_ROOM_ID").toString()
        Log.d("Room", "roomId: $roomId")

        // Attach a listener to db.roomid.members, so you can get the list of members joining
        database.reference.child(roomId).child("members").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val value = dataSnapshot.value
                // What type is value? (HashMap?)
                Log.d("Room", "${value!!::class.simpleName}")
                // How to iterate its contents?
                Log.d("Room", "RoomActivity dataSnapshot.value is: $value")
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w("Room", "Failed to read value.", error.toException())
            }
        })

        membersList.add("TestUser1")
        // Regular Recyclerview initiation stuff
        recyclerViewLayoutManager = LinearLayoutManager(this)
        recyclerViewAdapter = RoomMembersRecyclerviewAdapter(membersList)

        recyclerView = findViewById<RecyclerView>(R.id.room_members_recyclerview).apply{
                layoutManager = recyclerViewLayoutManager
                adapter = recyclerViewAdapter
            }
    }
}