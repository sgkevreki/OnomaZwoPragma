package com.example.onomazwopragmaproject

import android.content.Intent
import android.os.Bundle
import android.view.ViewTreeObserver
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.plattysoft.leonids.ParticleSystem


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


        val rootView = findViewById<ConstraintLayout>(R.id.root_constraint_layout)
        val viewTreeObserver: ViewTreeObserver = rootView.viewTreeObserver
        if (viewTreeObserver.isAlive) {
            viewTreeObserver.addOnGlobalLayoutListener(object : OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    rootView.viewTreeObserver.removeOnGlobalLayoutListener(this)
//                    viewWidth = root_view.width
//                    viewHeight = root_view.height
                    var particleSystem = ParticleSystem(this@MainActivity, 500, R.drawable.ic_launcher_foreground, 10000)
                        .setSpeedRange(0.2f, 0.5f)
                        .emit(rootView, 50)

                }
            })
        }



    }
}