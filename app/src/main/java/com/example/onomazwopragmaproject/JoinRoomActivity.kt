package com.example.onomazwopragmaproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton

class JoinRoomActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join_room)

        var playButton = findViewById<ImageButton>(R.id.playButton)
        playButton.setOnClickListener {
            var intent: Intent = Intent(this, GameActivity::class.java)
            startActivity(intent)
        }

    }
}