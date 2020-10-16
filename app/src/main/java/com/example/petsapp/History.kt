package com.example.petsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.BaseAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_history.*
import kotlinx.android.synthetic.main.history_item.view.*
import model.ApiInterface
import model.AppHelper
import model.Post
import model.ServiceItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.StringBuilder

class History : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_history)

        getItems()

        btn_back_h.setOnClickListener {
            finish()
        }
    }

    override fun onBackPressed() { finish() }

    inner class ItemsAdapter: BaseAdapter {

        var listItems = ArrayList<ServiceItem>()

        constructor(list: ArrayList<ServiceItem>):super(){
            this.listItems = list
        }

        override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
            var myView = layoutInflater.inflate(R.layout.history_item, null)
            var myNode = listItems[p0]
            myView.txt_type_h.text = myNode.type
            myView.txt_status_h.text = myNode.status
            myView.txt_service_h.text = myNode.service
            myView.txt_amount_h.text = StringBuilder().append(" $").append(myNode.price)
            myView.txt_time_h.text = AppHelper().timeStrBuild(myNode.time!!.toInt())
            myView.txt_starts_h.text = myNode.starts
            myView.txt_ends_h.text = myNode.ends
            return myView
        }

        override fun getItem(p0: Int): Any {
            return listItems[p0]
        }

        override fun getItemId(p0: Int): Long {
            return p0.toLong()
        }

        override fun getCount(): Int {
            return listItems.size
        }

    }

    private fun getItems(){
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(getString(R.string.api_url_))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val apiInterface = retrofit.create(ApiInterface::class.java)
        val call: Call<ArrayList<Post>> = apiInterface.getPosts()

        println(call.toString())

        var postList: ArrayList<ServiceItem> = ArrayList<ServiceItem>()

        call.enqueue(object: Callback<ArrayList<Post>> {
            override fun onResponse(call: Call<ArrayList<Post>>, response: Response<ArrayList<Post>>) {

                val responseP = response.body()
                if (responseP!!.size > 0){
                    var i = 0
                    while(responseP.size > i){
                        var elemento = ServiceItem(0,responseP[i].title.toString(),responseP[i].body.toString(),
                            responseP[i].id.toString(),120.0.toFloat(),60,"02/08/2020 12:35 am","02/08/2020 02:35 pm")
                        postList.add(elemento)
                        i += 1
                    }
                    list_h.adapter = ItemsAdapter(postList)
                }
            }
            override fun onFailure(call: Call<ArrayList<Post>>, t: Throwable) {
                Toast.makeText(applicationContext, "Failed to load data", Toast.LENGTH_LONG).show()
            }
        })
    }
}