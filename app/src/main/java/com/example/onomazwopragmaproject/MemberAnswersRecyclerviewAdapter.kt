package com.example.onomazwopragmaproject

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.onomazwopragmaproject.GlobalsActivity.Companion.database
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class MemberAnswersRecyclerviewAdapter(private val members: MutableList<String>) : RecyclerView.Adapter<MemberAnswersRecyclerviewAdapter.MyViewHolder>() {
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var memberImage: ImageView = itemView.findViewById(R.id.member_answer_image)
        internal var memberName: TextView = itemView.findViewById(R.id.member_answer_name)
        internal var memberNameAnswers: TextView = itemView.findViewById(R.id.member_answer_value)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val myView = LayoutInflater.from(parent.context).inflate(R.layout.card_member_answer, parent, false) as CardView
        return MyViewHolder(myView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var currentMember = members[position]
        var totalScore = 0
        var memberScoresListener = object : ChildEventListener{
            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                for (category in p0.children){
                    totalScore += category.value.toString().toInt() // ?
                }
                holder.memberNameAnswers.text = totalScore.toString()
            }

            override fun onChildChanged(p0: DataSnapshot, p1: String?) {

            }

            override fun onChildRemoved(p0: DataSnapshot) {

            }

            override fun onChildMoved(p0: DataSnapshot, p1: String?) {
                TODO("Not yet implemented")
            }

            override fun onCancelled(p0: DatabaseError) {
                TODO("Not yet implemented")
            }
        }
//        database.reference. ...///
        // A listener that
    }

    override fun getItemCount(): Int {
        Log.d("RoomAdapter", "getItemCount returns: ${members.size}")
        return members.size
    }

}