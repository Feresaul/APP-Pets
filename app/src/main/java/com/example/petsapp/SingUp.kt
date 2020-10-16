package com.example.petsapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import kotlinx.android.synthetic.main.activity_sing_up.*

class SingUp : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_sing_up)

        btn_login_r.setOnClickListener{
            finish()
        }

        btn_submit.setOnClickListener {
            //Api Function
            finish()
        }
    }

    override fun onBackPressed() { finish() }
}