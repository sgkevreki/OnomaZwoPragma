package com.example.onomazwopragmaproject

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

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
        categoryNameTextView.text = categoryName
        return rootView
    }

}
