package com.example.onomazwopragmaproject

import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase

class GlobalsActivity : AppCompatActivity() {
    companion object{
        val database = FirebaseDatabase.getInstance()
        val roomIdSource = ('A'..'Z') + ('0'..'9')
        const val roomIdLength = 4
        val memberIdSource = ('A'..'Z') + ('0'..'9') + ('a'..'z')
        const val memberIdLength = 16
        val SHARED_PREFS : String = "sharedPrefs"
    } }