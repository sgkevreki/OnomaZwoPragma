package com.example.onomazwopragmaproject

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Handler
import android.util.Log
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


        // ------------------------------------------------------------------------------------------------------------------
        // This thread is responsible for the background particle system.
        // It creates an emitter every 1 second that spits out 1 drawable and then destroys it.
        // PROCEED WITH CAUTION WHEN CHANGING THIS PART OF THE CODE
        // ------------------------------------------------------------------------------------------------------------------
        val rootView = findViewById<ConstraintLayout>(R.id.root_constraint_layout)
        val mainDelay : Long = 1000
        val handler = Handler()
        val runnable: Runnable = object : Runnable {
            override fun run() {
                var x: Int = 0
                var y: Int = 0
                when ((0..3).random()) {
                    0 -> {
                        x = -40
                        y = (0..rootView.height).random()
                        }
                    1 -> {
                        x = (0..rootView.width).random()
                        y = -40
                        }
                    2 -> {
                        x = rootView.width + 40
                        y = (0..rootView.height).random()
                    }
                    3 -> {
                        x = (0..rootView.width).random()
                        y = rootView.height + 400
                    }
                }

                var floatingItem: Drawable = resources.getDrawable(R.drawable.compass)
                when ((0..9).random()) {
                    0 -> floatingItem = resources.getDrawable(R.drawable.compass)
                    1 -> floatingItem = resources.getDrawable(R.drawable.eraser)
                    2 -> floatingItem = resources.getDrawable(R.drawable.highlighter)
                    3 -> floatingItem = resources.getDrawable(R.drawable.paper_clip)
                    4 -> floatingItem = resources.getDrawable(R.drawable.pencil)
                    5 -> floatingItem = resources.getDrawable(R.drawable.pens)
                    6 -> floatingItem = resources.getDrawable(R.drawable.protractor)
                    7 -> floatingItem = resources.getDrawable(R.drawable.ruler)
                    8 -> floatingItem = resources.getDrawable(R.drawable.send)
                    9 -> floatingItem = resources.getDrawable(R.drawable.sharpener)
                }

                rootView.post {
                    Log.d("xy", "$x $y")
                    var particleSystem = ParticleSystem(
                        this@MainActivity,
                        500,
                        floatingItem,
                        30000,
                        R.id.frame_anim_background
                    )
                        .setSpeedRange(0.02f, 0.1f)
                        .setRotationSpeed(40F)
                        .setScaleRange(0.2f, 0.2f)
                        .emit(x, y, 1, 1000)
                }
                handler.postDelayed(this, mainDelay)
            }
        }
        handler.postDelayed(runnable, mainDelay)
        // ------------------------------------------------------------------------------------------------------------------
        // ------------------------------------------------------------------------------------------------------------------
    }
}