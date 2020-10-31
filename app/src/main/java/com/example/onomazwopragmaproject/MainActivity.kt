package com.example.onomazwopragmaproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var button1 = findViewById<Button>(R.id.button1)
        button1.setOnClickListener {
            var intent: Intent = Intent(this, HostOrJoinRoomActivity::class.java)
            startActivity(intent)
        }

        var button2 = findViewById<Button>(R.id.button2)
        button2.setOnClickListener {
            // ...
        }


    }
}