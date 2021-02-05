package com.example.onomazwopragmaproject

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

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

    private lateinit var recyclerView: RecyclerView
    private lateinit var recyclerViewAdapter: RecyclerView.Adapter<*>
    private lateinit var recyclerViewLayoutManager: RecyclerView.LayoutManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View
    {
        Log.d("PRINTS", "id: ${memberID}, name: ${memberName.toString()}")
        // ALL PRAISE OUR LORD AND SAVIOR LINKEDHASHMAP!
        val myAnswers: LinkedHashMap<String, String> = (answers[memberID] as LinkedHashMap<String, String>?)!!
        val rootView = inflater.inflate(R.layout.card_category_answers, container, false)
        val categoryNameTextView = rootView.findViewById<TextView>(R.id.category_name_answers)
        val memberNameTextView = rootView.findViewById<TextView>(R.id.member_name_answers)
        val answerTextView = rootView.findViewById<TextView>(R.id.answer)

        categoryNameTextView.text = categoryName
        memberNameTextView.text = memberName
        answerTextView.text = myAnswers[categoryName].toString()

//        // Need to pass a *List* of *Hashmaps* so that the adapter can iterate over the list!
//        val answersList: MutableList<LinkedHashMap<String, String>> = mutableListOf()
//        for (key in answers.keys){
//            answersList.add(answers[key]!!)
//        }
//
//        // Regular Recyclerview initiation stuff
//        recyclerViewLayoutManager = LinearLayoutManager(context)
//        recyclerViewAdapter = MemberAnswersRecyclerviewAdapter(answersList)
//
//        recyclerView = rootView.findViewById<RecyclerView>(R.id.other_answers_recyclerview).apply{
//            layoutManager = recyclerViewLayoutManager
//            adapter = recyclerViewAdapter
//        }

        return rootView
    }

}
