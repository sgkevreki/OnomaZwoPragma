package com.example.onomazwopragmaproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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
        val membersList = intent.getStringArrayListExtra("EXTRA_MEMBERS_LIST")
        Log.d("MEMBERSLIST", "finalresults: ${intent.getStringArrayListExtra("EXTRA_MEMBERS_LIST")}")

        recyclerViewLayoutManager = LinearLayoutManager(this)
        recyclerViewAdapter = MemberAnswersRecyclerviewAdapter(membersList)

        recyclerView = findViewById<RecyclerView>(R.id.room_members_recyclerview).apply{
            layoutManager = recyclerViewLayoutManager
            adapter = recyclerViewAdapter
        }
    }
}