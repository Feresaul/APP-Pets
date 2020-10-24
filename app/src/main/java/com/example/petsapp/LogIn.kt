package com.example.petsapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.widget.Toast
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_login.*
import model.ApiInterface
import model.ResponseT
import model.Usuario
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LogIn : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_login)

        btn_login.setOnClickListener {
            if (ip_user.text.toString() == "" || ip_psw.text.toString() == ""){
                Toast.makeText(applicationContext, "Complete all fields", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            //getAccess()
        }

        btn_singup.setOnClickListener{
            val intent = Intent(applicationContext, SingUp::class.java)
            startActivity(intent)
        }
    }

    override fun onStart() {
        val sharedPreferences = this.getSharedPreferences("com.up.storedatasharepreferences", Context.MODE_PRIVATE)
        val usuario = sharedPreferences.getInt("id_user", -1)
        if (usuario != -1){
            home()
        }
        super.onStart()
    }

    fun home(){
        val intent = Intent(applicationContext, Home::class.java)
        startActivity(intent)
        finish()
    }

    override fun onBackPressed(){}

    /*private fun getAccess(){
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(getString(R.string.api_url))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val apiInterface = retrofit.create(ApiInterface::class.java)
        val call: Call<ResponseT<Usuario>> = apiInterface.getUser(ip_user.text.toString().toInt())

        call.enqueue(object: Callback<ResponseT<Usuario>> {
            override fun onResponse(call: Call<ResponseT<Usuario>>, response: Response<ResponseT<Usuario>>) {
                val responseP = response.body()
                println(Gson().toJson(responseP))
                if (responseP!!.modelo != null) {
                    if (responseP.modelo!!.contrasenia == ip_psw.text.toString()){
                        val sharedPreferences = getSharedPreferences("com.up.storedatasharepreferences", Context.MODE_PRIVATE)
                        sharedPreferences.edit().putInt("id_user", responseP.modelo!!.idUsuario!!.toInt()).apply()
                        home()
                    }
                    else{
                        Toast.makeText(applicationContext, "Incorrect Password", Toast.LENGTH_LONG).show()
                    }
                }
                else{
                    Toast.makeText(applicationContext, "Username does not exist", Toast.LENGTH_LONG).show()
                }
            }
            override fun onFailure(call: Call<ResponseT<Usuario>>, t: Throwable) {
                Toast.makeText(applicationContext, "Failed to load data", Toast.LENGTH_LONG).show()
            }
        })
    }*/
}