package com.example.onomazwopragmaproject

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Handler
import android.widget.ImageButton
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.plattysoft.leonids.ParticleSystem


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button1 = findViewById<ImageButton>(R.id.paikse_button)
        button1.setOnClickListener {
            val intent = Intent(this, HostOrJoinRoomActivity::class.java)
            startActivity(intent)
        }

        val instructionsButton = findViewById<ImageView>(R.id.instructions_button)
        instructionsButton.setOnClickListener {
            val intent = Intent(this, InstructionsActivity::class.java)
            startActivity(intent)
        }

        val exitButton = findViewById<ImageView>(R.id.exit_button)
        exitButton.setOnClickListener {
//            val homeIntent = Intent(Intent.ACTION_MAIN)
//            homeIntent.addCategory(Intent.CATEGORY_HOME)
//            homeIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
//            startActivity(homeIntent)
        }



        val rootView = findViewById<ConstraintLayout>(R.id.root_constraint_layout)

        // ------------------------------------------------------------------------------------------------------------------
        // When the app starts, we create a thread that is responsible for updating the main currency *view* every X seconds.
        // This thread is posting changes for the UI thread.
        // PROCEED WITH CAUTION WHEN CHANGING THIS PART OF THE CODE
        // ------------------------------------------------------------------------------------------------------------------
        val mainDelay : Long = 300
        val handler = Handler()
        val runnable: Runnable = object : Runnable {
            @SuppressLint("UseCompatLoadingForDrawables")
            override fun run() {
                var x = 0
                var y = 0
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


                var floatingItem: Drawable =
                    resources.getDrawable(R.drawable.compass)
//                    ResourcesCompat.getDrawable(resources, R.drawable.compass, null)!!
                when ((0..8).random()) {
                    0 -> floatingItem = resources.getDrawable(R.drawable.compass)
                    1 -> floatingItem = resources.getDrawable(R.drawable.eraser)
                    2 -> floatingItem = resources.getDrawable(R.drawable.highlighter)
                    3 -> floatingItem = resources.getDrawable(R.drawable.pencil)
                    4 -> floatingItem = resources.getDrawable(R.drawable.pens)
                    5 -> floatingItem = resources.getDrawable(R.drawable.protractor)
                    6 -> floatingItem = resources.getDrawable(R.drawable.ruler)
                    7 -> floatingItem = resources.getDrawable(R.drawable.send)
                    8 -> floatingItem = resources.getDrawable(R.drawable.sharpener)
                }

                rootView.post {
//                    Log.d("xy", "$x $y")
                    var particleSystem = ParticleSystem(
                        this@MainActivity,
                        500,
                        floatingItem,
                        30000,
                        R.id.frame_anim_background
                    )
//                        .setSpeedModuleAndAngleRange(0.02f, 0.1f, 0, 180)
                        .setSpeedRange(0.02f, 0.1f)
                        .setRotationSpeed(40F)
                        .setScaleRange(0.2f, 0.2f)
                        .emit(x, y, 1, 700)
                }
                handler.postDelayed(this, mainDelay)
            }
        }
        handler.postDelayed(runnable, mainDelay)
        // ------------------------------------------------------------------------------------------------------------------
        // ------------------------------------------------------------------------------------------------------------------
    }
}