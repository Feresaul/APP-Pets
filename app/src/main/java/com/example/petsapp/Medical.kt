package com.example.petsapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import kotlinx.android.synthetic.main.activity_medical.*

class Medical : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_medical)

        btn_back_m.setOnClickListener {
            val intent = Intent(applicationContext, Home::class.java)
            startActivity(intent)
        }
    }
}