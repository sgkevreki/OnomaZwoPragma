package com.example.onomazwopragmaproject

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView

class RoomMembersRecyclerviewAdapter(private val membersList: List<String>) : RecyclerView.Adapter<RoomMembersRecyclerviewAdapter.MyViewHolder>() {
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var memberImage: ImageView = itemView.findViewById(R.id.member_image)
        internal var memberName: TextView = itemView.findViewById(R.id.member_name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val myView = LayoutInflater.from(parent.context).inflate(R.layout.card_member, parent, false) as CardView
        return MyViewHolder(myView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.memberName.text = membersList[position]
        holder.memberImage.resources.getDrawable(R.drawable.name_image)
    }

    override fun getItemCount() = membersList.size

}