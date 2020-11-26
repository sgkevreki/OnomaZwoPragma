package com.example.onomazwopragmaproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.onomazwopragmaproject.GlobalsActivity.Companion.database
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError

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
        database.reference.child("rooms").child(roomId).child("members").addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(dataSnapshot: DataSnapshot, p1: String?) {
                // OMG this is called once for every item in member list!
                // member added
                Log.d("Room", "dataSnapshot.key: ${dataSnapshot.key?.get(0)}, dataSnapshot.value: ${dataSnapshot.getValue(Member::class.java)}, dataSnapsot.value.hashCode?: ${dataSnapshot.value}")
                var userObject = dataSnapshot.getValue(Member::class.java)
                Log.d("Room", "userObject: $userObject")
                membersList.add(userObject!!.name)
                Log.d("Room", "userObject!!.name: ${userObject!!.name}")
                recyclerViewAdapter.notifyDataSetChanged()
            }

            override fun onChildChanged(p0: DataSnapshot, p1: String?) {
                TODO("Not yet implemented")
            }

            override fun onChildRemoved(p0: DataSnapshot) {
                TODO("Not yet implemented")
            }

            override fun onChildMoved(p0: DataSnapshot, p1: String?) {
                TODO("Not yet implemented")
            }

            override fun onCancelled(p0: DatabaseError) {
                TODO("Not yet implemented")
            }
        })


        // Regular Recyclerview initiation stuff
        recyclerViewLayoutManager = LinearLayoutManager(this)
        recyclerViewAdapter = RoomMembersRecyclerviewAdapter(membersList)

        recyclerView = findViewById<RecyclerView>(R.id.room_members_recyclerview).apply{
                layoutManager = recyclerViewLayoutManager
                adapter = recyclerViewAdapter
            }
    }
}