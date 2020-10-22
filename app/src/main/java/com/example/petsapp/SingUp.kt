package com.example.petsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_login.*
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
            val res = check()
            if (res.error!!){
                Toast.makeText(applicationContext, res.message, Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            //Api Function

            finish()
        }
    }

    inner class Response{
        var error: Boolean ?= null
        var message: String ?= null
    }

    private fun check(): Response{

        val response = Response()
        response.error = false
        response.message = "Sing Up Completed"

        if (ip_user_r.text.toString() == "" || ip_name_r.text.toString() == ""
            || ip_email_r.text.toString() == "" || ip_phone_r.text.toString() == ""
            || ip_psw_r.text.toString() == "" || ip_psw2_r.text.toString() == ""){

            response.error = true
            response.message = "Complete all fields"
        }

        if (ip_psw_r.text.toString() != ip_psw2_r.text.toString()){
            ip_psw_r.setText("")
            ip_psw2_r.setText("")

            response.error = true
            response.message = "Bad Password Confirmation"
        }

        return response
    }

    override fun onBackPressed() { finish() }
}