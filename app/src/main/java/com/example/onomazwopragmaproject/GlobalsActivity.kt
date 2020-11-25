package com.example.onomazwopragmaproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class GlobalsActivity : AppCompatActivity() {
    companion object{
        var roomIdLength = 4
        val SHARED_PREFS : String = "sharedPrefs"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}