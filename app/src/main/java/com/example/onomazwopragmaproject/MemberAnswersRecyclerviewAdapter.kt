package com.example.onomazwopragmaproject

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class MemberAnswersRecyclerviewAdapter(private val answers: MutableList<LinkedHashMap<String, String>>) : RecyclerView.Adapter<MemberAnswersRecyclerviewAdapter.MyViewHolder>() {
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
//        var myMap = answers[position]
//        holder.memberName.text = myMap[]
//        holder.memberNameAnswers.text = answers[myKeys[position]]
//        holder.memberImage.resources.getDrawable(R.drawable.name_image)
    }

    override fun getItemCount(): Int {
        Log.d("RoomAdapter", "getItemCount returns: ${answers.size}")
        return answers.size
    }

}