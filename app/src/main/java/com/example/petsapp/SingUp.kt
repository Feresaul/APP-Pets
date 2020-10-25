package com.example.petsapp

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.widget.Toast
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_sing_up.*
import model.ApiInterface
import model.ResponseT
import model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

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
            getAccess()
        }
    }

    inner class Response{
        var error: Boolean ?= null
        var message: String ?= null
    }

    private fun check(): Response{

        val response = Response()
        response.error = false
        response.message = "sing up completed"

        if (ip_user_r.text.toString() == "" || ip_name_r.text.toString() == ""
            || ip_email_r.text.toString() == "" || ip_phone_r.text.toString() == ""
            || ip_psw_r.text.toString() == "" || ip_psw2_r.text.toString() == ""){

            response.error = true
            response.message = "complete all fields"
        }

        if (ip_psw_r.text.toString() != ip_psw2_r.text.toString()){
            ip_psw_r.setText("")
            ip_psw2_r.setText("")

            response.error = true
            response.message = "bad password confirmation"
        }

        return response
    }

    override fun onBackPressed() { finish() }

    private fun getAccess(){

        val user = User()
        user.usuario = ip_user_r.text.toString()
        user.nombre = ip_name_r.text.toString()
        user.correo = ip_email_r.text.toString()
        user.telefono = ip_phone_r.text.toString()
        user.contrasenia = ip_psw_r.text.toString()

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(getString(R.string.api_url))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val apiInterface = retrofit.create(ApiInterface::class.java)
        val call: Call<ResponseT<Int>> = apiInterface.singUp(user)

        call.enqueue(object: Callback<ResponseT<Int>> {
            override fun onResponse(call: Call<ResponseT<Int>>, response: retrofit2.Response<ResponseT<Int>>) {
                val responseP = response.body()
                if (!responseP!!.respuesta!!) {
                    val sharedPreferences = getSharedPreferences("com.up.storedatasharepreferences", Context.MODE_PRIVATE)
                    sharedPreferences.edit().putInt("id_user", responseP.modelo!!).apply()
                    finish()
                }
                else{
                    Toast.makeText(applicationContext, responseP.mensaje, Toast.LENGTH_LONG).show()
                }
            }
            override fun onFailure(call: Call<ResponseT<Int>>, t: Throwable) {
                Toast.makeText(applicationContext, "failed to load data", Toast.LENGTH_LONG).show()
            }
        })
    }
}