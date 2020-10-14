package com.example.petsapp

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import kotlinx.android.synthetic.main.activity_home.*

class Home : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_home)

        btn_logout.setOnClickListener {
            val sharedPreferences = this.getSharedPreferences("com.up.storedatasharepreferences", Context.MODE_PRIVATE)
            sharedPreferences.edit().remove("id_usuario").apply()
            changeView(LogIn::class.java)
        }

        btn_medica.setOnClickListener {
            changeView(Service::class.java)
        }

        btn_estetica.setOnClickListener {
            changeView(Service::class.java)
        }

        btn_citas.setOnClickListener {
            changeView(MyServices::class.java)
        }

        btn_hospedaje.setOnClickListener {
            changeView(Service::class.java)
        }

        btn_historial.setOnClickListener {
            changeView(History::class.java)
        }
    }

    private fun changeView(view: Class<out Activity>){
        val intent = Intent(applicationContext, view)
        startActivity(intent)
    }
}