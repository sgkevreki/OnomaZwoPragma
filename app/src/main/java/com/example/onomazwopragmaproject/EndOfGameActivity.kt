package com.example.onomazwopragmaproject

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2

/**
 * The number of pages (wizard steps) to show in this demo.
 */
private const val NUM_PAGES = 5

class EndOfGameActivity : FragmentActivity() {

    /**
     * The pager widget, which handles animation and allows swiping horizontally to access previous
     * and next wizard steps.
     */
    private lateinit var viewPager: ViewPager2
    private lateinit var memberID: String
    private lateinit var roomID: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_end_of_game)

        // Instantiate a ViewPager2 and a PagerAdapter.
        viewPager = findViewById(R.id.pager)
        val categories = intent.getStringArrayListExtra("CATEGORIES_EXTRA")
        memberID = intent.getStringExtra("MEMBER_ID_EXTRA")!!
        roomID = intent.getStringExtra("ROOM_ID_EXTRA")!!
        Log.d("EndOfGameCateg", "Categories: $categories, memberID: $memberID, roomID:, $roomID")

        // The pager adapter, which provides the pages to the view pager widget.
        val pagerAdapter = ScreenSlidePagerAdapter(this, categories as ArrayList<String>)
        viewPager.adapter = pagerAdapter
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

    private inner class ScreenSlidePagerAdapter(fa: FragmentActivity, categories: ArrayList<String>) : FragmentStateAdapter(fa) {
        override fun getItemCount(): Int = categories.size

        override fun createFragment(position: Int): Fragment = ScreenSlidePageFragment(categories[position])
    }
}
