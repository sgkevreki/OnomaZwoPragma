package com.example.onomazwopragmaproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class FinalResultsActivity : AppCompatActivity() {
    var membersList: MutableList<String> = mutableListOf()

    private lateinit var recyclerView: RecyclerView
    private lateinit var recyclerViewAdapter: RecyclerView.Adapter<*>
    private lateinit var recyclerViewLayoutManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_final_results)

        recyclerViewLayoutManager = LinearLayoutManager(this)
        recyclerViewAdapter = RoomMembersRecyclerviewAdapter(membersList)

        recyclerView = findViewById<RecyclerView>(R.id.room_members_recyclerview).apply{
            layoutManager = recyclerViewLayoutManager
            adapter = recyclerViewAdapter
        }
    }
}