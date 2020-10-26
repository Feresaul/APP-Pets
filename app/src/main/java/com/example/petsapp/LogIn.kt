package com.example.petsapp

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.widget.Toast
import androidx.core.graphics.toColor
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_login.*
import model.ApiInterface
import model.AppHelper
import model.ResponseT
import model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@SuppressLint("ResourceType")
class LogIn : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_login)

        btn_login.setOnClickListener {
            if (ip_user.text.toString() == "" || ip_psw.text.toString() == ""){
                AppHelper().myToast(applicationContext,"complete all fields", R.drawable.ic_baseline_error_outline_24, getString(R.color.toast_alert))
                return@setOnClickListener
            }
            getAccess()
        }

        btn_singup.setOnClickListener{
            val intent = Intent(applicationContext, SingUp::class.java)
            startActivity(intent)
        }
    }

    override fun onStart() {
        val sharedPreferences = this.getSharedPreferences("com.up.storedatasharepreferences", Context.MODE_PRIVATE)
        val idUser = sharedPreferences.getInt("id_user", -1)
        if (idUser != -1){
            home()
        }
        super.onStart()
    }

    fun home(){
        val intent = Intent(applicationContext, Home::class.java)
        startActivity(intent)
        finish()
    }

    private fun getAccess(){

        val user = User()
        user.usuario = ip_user.text.toString()
        user.contrasenia = ip_psw.text.toString()

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(getString(R.string.api_url))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val apiInterface = retrofit.create(ApiInterface::class.java)
        val call: Call<ResponseT<Int>> = apiInterface.logIn(user)

        call.enqueue(object: Callback<ResponseT<Int>> {

            override fun onResponse(call: Call<ResponseT<Int>>, response: Response<ResponseT<Int>>) {
                val responseP = response.body()
                if (responseP != null)
                    if (!responseP.error!!) {
                        val sharedPreferences = getSharedPreferences("com.up.storedatasharepreferences", Context.MODE_PRIVATE)
                        sharedPreferences.edit().putInt("id_user", responseP.modelo!!).apply()
                        home()
                    }
                    else{
                        AppHelper().myToast(applicationContext, responseP.mensaje!! , R.drawable.ic_baseline_cancel_24, getString(R.color.toast_error))
                        ip_psw.setText("")
                    }
            }

            override fun onFailure(call: Call<ResponseT<Int>>, t: Throwable) {
                AppHelper().myToast(applicationContext,"failed to load data", R.drawable.ic_baseline_cancel_24, getString(R.color.toast_error))
            }
        })
    }
}