package com.example.petsapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import model.ApiInterface
import model.Post
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LogIn : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_main)

        val sharedPreferences = this.getSharedPreferences("com.up.storedatasharepreferences", Context.MODE_PRIVATE)
        val usuario = sharedPreferences.getString("usuario","")
        if (usuario != null && usuario != ""){
            home()
        }

        btn_login.setOnClickListener {
            getPosts()
        }

        btn_singup.setOnClickListener{
            val intent = Intent(applicationContext, SingUp::class.java)
            startActivity(intent)
        }
    }

    fun home(){
        val sharedPreferences = this.getSharedPreferences("com.up.storedatasharepreferences", Context.MODE_PRIVATE)
        sharedPreferences.edit().putString("usuario", ip_user.text.toString()).apply()
        val intent = Intent(applicationContext, Home::class.java)
        startActivity(intent)
    }

    override fun onBackPressed(){}

    private fun getPosts(){
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(getString(R.string.api_url))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val apiInterface = retrofit.create(ApiInterface::class.java)
        val call: Call<List<Post>> = apiInterface.getPosts()

        var postList: ArrayList<Post> = ArrayList<Post>()

        call.enqueue(object: Callback<List<Post>> {
            override fun onResponse(call: Call<List<Post>>, response: Response<List<Post>>) {

                val responseP = response.body()
                if (responseP!!.size > 0){
                    var i = 0
                    while(responseP.size > i){
                        var elemento = Post(responseP[i].userId!!.toInt(),responseP[i].id!!.toInt(),responseP[i].title.toString(),responseP[i].body.toString())
                        postList.add(elemento)
                        i += 1
                    }
                    home()
                }
            }
            override fun onFailure(call: Call<List<Post>>, t: Throwable) {
            }
        })
    }
}