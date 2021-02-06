package com.example.onomazwopragmaproject

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import com.example.onomazwopragmaproject.GlobalsActivity.Companion.database

class ScreenSlidePageFragment(
    categoryNameInput: String,
    answersInput: HashMap<String, LinkedHashMap<String, String>>,
    memberIDInput: String,
    memberNameInput: String
) : Fragment() {

    private var categoryName = categoryNameInput
    private val answers: HashMap<String, LinkedHashMap<String, String>> = answersInput
    private var memberID = memberIDInput
    private var memberName = memberNameInput

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View
    {
        Log.d("PRINTS", "id: ${memberID}, name: ${memberName.toString()}")
        val myAnswers: LinkedHashMap<String, String> = (answers[memberID] as LinkedHashMap<String, String>?)!!
        val rootView = inflater.inflate(R.layout.card_category_answers, container, false)
        val categoryNameTextView = rootView.findViewById<TextView>(R.id.category_name_answers)
        val memberNameTextView = rootView.findViewById<TextView>(R.id.member_name_answers)
        val answerTextView = rootView.findViewById<TextView>(R.id.answer)
        val pointsGroup = rootView.findViewById<RadioGroup>(R.id.radioGroup)
        val defaultButton = rootView.findViewById<RadioButton>(R.id.radio_button_1)

        categoryNameTextView.text = categoryName
        memberNameTextView.text = memberName
        answerTextView.text = myAnswers[categoryName].toString()

        defaultButton.isChecked = true
        database.reference.child("rooms").child(roomID).child("members").child(memberID).child("categoryscores").child(categoryName)
            .setValue(rootView.findViewById<RadioButton>(pointsGroup.checkedRadioButtonId).text.toString().toInt())

        pointsGroup.setOnCheckedChangeListener { _, _ ->
            database.reference.child("rooms").child(roomID).child("members").child(memberID).child("categoryscores").child(categoryName)
                .setValue(rootView.findViewById<RadioButton>(pointsGroup.checkedRadioButtonId).text.toString().toInt())
        }

        return rootView
    }
}