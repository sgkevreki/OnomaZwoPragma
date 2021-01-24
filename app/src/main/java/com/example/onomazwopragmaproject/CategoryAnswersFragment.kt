package com.example.onomazwopragmaproject

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.onomazwopragmaproject.GlobalsActivity.Companion.database
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError

class ScreenSlidePageFragment(categoryNameInput: String) : Fragment() {

    private var categoryName = categoryNameInput

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View
    {
        val rootView = inflater.inflate(R.layout.card_category_answers, container, false)
        val categoryNameTextView = rootView.findViewById<TextView>(R.id.category_name_answers)
        val memberNameTextView = rootView.findViewById<TextView>(R.id.member_name_answers)
        val answerTextView = rootView.findViewById<TextView>(R.id.answer)

        categoryNameTextView.text = categoryName
        memberNameTextView.text = memberID

        database.reference.child("rooms").child(roomID).child("members").addChildEventListener(
            object : ChildEventListener{
                override fun onChildAdded(p0: DataSnapshot, p1: String?) {
//                    val answers = p0.child()

                    Log.d("Answers Database Tests", "${p0.child(categoryName).value}}")
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
            }
        )




        return rootView
    }

}
