package com.example.onomazwopragmaproject

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.ViewTreeObserver
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.plattysoft.leonids.ParticleSystem
import kotlin.math.pow


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var button1 = findViewById<ImageButton>(R.id.paikse_button)
        button1.setOnClickListener {
            var intent: Intent = Intent(this, HostOrJoinRoomActivity::class.java)
            startActivity(intent)
        }



//        val viewTreeObserver: ViewTreeObserver = rootView.viewTreeObserver
//        if (viewTreeObserver.isAlive) {
//            viewTreeObserver.addOnGlobalLayoutListener(object : OnGlobalLayoutListener {
//                override fun onGlobalLayout() {
//                    rootView.viewTreeObserver.removeOnGlobalLayoutListener(this)
//
//                    var particleSystem = ParticleSystem(this@MainActivity, 500, R.drawable.compass, 20000)
//                        .setSpeedRange(0.02f, 0.1f)
//                        .setRotationSpeed(40F)
//                        .setScaleRange(0.3f, 0.4f)
//                        .emit((0..200).random(), (0..200).random(), 1)
//
//
//                }
//            })
//        }

        // ------------------------------------------------------------------------------------------------------------------
        // When the app starts, we create a thread that is responsible for updating the main currency *view* every X seconds.
        // This thread is posting changes for the UI thread.
        // PROCEED WITH CAUTION WHEN CHANGING THIS PART OF THE CODE
        // ------------------------------------------------------------------------------------------------------------------
        val rootView = findViewById<ConstraintLayout>(R.id.root_constraint_layout)
        val mainDelay : Long = 300
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

                var floating_item: Drawable = resources.getDrawable(R.drawable.compass)
                when ((0..8).random()) {
                    0 -> floating_item = resources.getDrawable(R.drawable.compass)
                    1 -> floating_item = resources.getDrawable(R.drawable.eraser)
                    2 -> floating_item = resources.getDrawable(R.drawable.highlighter)
                    3 -> floating_item = resources.getDrawable(R.drawable.pencil)
                    4 -> floating_item = resources.getDrawable(R.drawable.pens)
                    5 -> floating_item = resources.getDrawable(R.drawable.protractor)
                    6 -> floating_item = resources.getDrawable(R.drawable.ruler)
                    7 -> floating_item = resources.getDrawable(R.drawable.send)
                    8 -> floating_item = resources.getDrawable(R.drawable.sharpener)
                }

                rootView.post {
                    Log.d("xy", "$x $y")
                    var particleSystem = ParticleSystem(
                        this@MainActivity,
                        500,
                        floating_item,
                        30000,
                        R.id.frame_anim_background
                    )
//                        .setSpeedModuleAndAngleRange(0.02f, 0.1f, 0, 180)
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