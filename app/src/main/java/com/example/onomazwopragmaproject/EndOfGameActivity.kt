package com.example.onomazwopragmaproject

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.example.onomazwopragmaproject.GlobalsActivity.Companion.database
import kotlinx.android.synthetic.main.card_category_answers.*

class EndOfGameActivity : FragmentActivity() {

    /**
     * The pager widget, which handles animation and allows swiping horizontally to access previous
     * and next wizard steps.
     */
    private lateinit var viewPager: ViewPager2
    private lateinit var memberID: String
    private lateinit var roomID: String
    private val answers: HashMap <String, LinkedHashMap<String, String>> = hashMapOf()
    private lateinit var memberName: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_end_of_game)

        roomID = intent.getStringExtra("ROOM_ID_EXTRA")!!.toString()
        memberID = intent.getStringExtra("MEMBER_ID_EXTRA")!!.toString()


        val membersAnswersChildListener = object : ChildEventListener{
            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                var memberAnswers : LinkedHashMap<String, String> = linkedMapOf()
                for (category in categories){
                    memberAnswers[category] = p0.child(category).value.toString()
                }
                if(p0.key.toString() == memberID) {
                    memberName = p0.child("name").value.toString()
                }
                answers[p0.key.toString()] = memberAnswers
                Log.d("RUNS", "for p0: $p0, and answers is: $answers")
            }
            override fun onChildChanged(p0: DataSnapshot, p1: String?) {
                var memberAnswers : LinkedHashMap<String, String> = linkedMapOf()
                for (category in categories){
                    memberAnswers[category] = p0.child(category).value.toString()
                }
                if(p0.key.toString() == memberID) {
                    memberName = p0.child("name").value.toString()
                }
                answers[p0.key.toString()] = memberAnswers
            }
            override fun onChildRemoved(p0: DataSnapshot) {
                // Do nothing?
            }
            override fun onChildMoved(p0: DataSnapshot, p1: String?) {
                TODO("Not yet implemented")
            }
            override fun onCancelled(p0: DatabaseError) {
                TODO("Not yet implemented")
            }
        }

        database.reference.child("rooms").child(roomID).child("members")
            .addChildEventListener(membersAnswersChildListener)

        /**
         * This should all be on a new thread.
         */
        val handler = Handler()
        val runnable: Runnable = object : Runnable {
            @SuppressLint("UseCompatLoadingForDrawables")
            override fun run() {
                Log.d("ANSWERS", "answers are $answers")

                // Instantiate a ViewPager2 and a PagerAdapter.
                viewPager = findViewById(R.id.pager)
                val categories = intent.getStringArrayListExtra("CATEGORIES_EXTRA")
                memberID = intent.getStringExtra("MEMBER_ID_EXTRA")!!
                roomID = intent.getStringExtra("ROOM_ID_EXTRA")!!
                Log.d("EndOfGameCateg", "Categories: $categories, memberID: $memberID, roomID:, $roomID")

                // The pager adapter, which provides the pages to the view pager widget.
                val pagerAdapter = ScreenSlidePagerAdapter(this@EndOfGameActivity, categories as ArrayList<String>, answers, memberID, memberName)
                viewPager.adapter = pagerAdapter
            }
        }
        handler.postDelayed(runnable, 2000)
        // ------------------------------------------------------------------------------------------------------------------
        // ------------------------------------------------------------------------------------------------------------------


    }

    override fun onBackPressed() {
        if (viewPager.currentItem == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed()
        } else {
            // Otherwise, select the previous step.
            viewPager.currentItem = viewPager.currentItem - 1
        }
    }

    private inner class ScreenSlidePagerAdapter(
        fa: FragmentActivity,
        categories: ArrayList<String>,
        answers: HashMap<String, LinkedHashMap<String, String>>,
        memberID: String,
        memberName: String
    ) : FragmentStateAdapter(fa) {
        override fun getItemCount(): Int = categories.size

        override fun createFragment(position: Int): Fragment = ScreenSlidePageFragment(categories[position], answers, memberID, memberName)
    }
}
