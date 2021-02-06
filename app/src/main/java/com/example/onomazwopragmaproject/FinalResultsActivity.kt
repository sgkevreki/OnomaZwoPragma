package com.example.onomazwopragmaproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError

class FinalResultsActivity : AppCompatActivity() {
    var membersList: MutableList<String> = mutableListOf()
    var membersPoints: MutableList<String> = mutableListOf()

    private lateinit var recyclerView: RecyclerView
    private lateinit var recyclerViewAdapter: RecyclerView.Adapter<*>
    private lateinit var recyclerViewLayoutManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_final_results)
//        val membersList = intent.getStringArrayListExtra("EXTRA_MEMBERS_LIST")
//        Log.d("MEMBERSLIST", "finalresults: ${intent.getStringArrayListExtra("EXTRA_MEMBERS_LIST")}")

        val myListener = object : ChildEventListener {
            override fun onChildAdded(dataSnapshot: DataSnapshot, p1: String?) {
                // OMG this is called once for every item in member list!
                // member added
                var userObject = dataSnapshot.getValue(Member::class.java)
                membersList.add(userObject!!.name)
                var finalResult = 0
                for (child in (dataSnapshot.child("categoryscores").children)){
                    finalResult += child.value.toString().toInt()
                }
                membersPoints.add(finalResult.toString())
                recyclerViewAdapter.notifyDataSetChanged()
            }

            override fun onChildChanged(dataSnapshot: DataSnapshot, p1: String?) {
                var userObject = dataSnapshot.getValue(Member::class.java)
                membersList.remove(userObject!!.name)
                recyclerViewAdapter.notifyDataSetChanged()

            }

            override fun onChildRemoved(dataSnapshot: DataSnapshot) {
                var userObject = dataSnapshot.getValue(Member::class.java)
                membersList.remove(userObject!!.name)
                recyclerViewAdapter.notifyDataSetChanged()
            }

            override fun onChildMoved(p0: DataSnapshot, p1: String?) {
                TODO("Not yet implemented")
            }

            override fun onCancelled(p0: DatabaseError) {
                TODO("Not yet implemented")
            }
        }
        GlobalsActivity.database.reference.child("rooms").child(roomID).child("members").addChildEventListener(myListener)


        recyclerViewLayoutManager = LinearLayoutManager(this)
        recyclerViewAdapter = MemberAnswersRecyclerviewAdapter(membersList, membersPoints)

        recyclerView = findViewById<RecyclerView>(R.id.room_members_recyclerview).apply{
            layoutManager = recyclerViewLayoutManager
            adapter = recyclerViewAdapter
        }

        val playAgainButton = findViewById<Button>(R.id.play_again_button)
        playAgainButton.setOnClickListener {
            membersList = mutableListOf()
            membersPoints = mutableListOf()
            val newIntent = Intent(this, MainActivity::class.java)
            startActivity(newIntent)
            finish()
        }

    }
}