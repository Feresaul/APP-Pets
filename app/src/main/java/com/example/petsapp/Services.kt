package com.example.petsapp

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.BaseAdapter
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_history.*
import kotlinx.android.synthetic.main.activity_services.*
import kotlinx.android.synthetic.main.service_item.view.*
import model.ApiInterface
import model.Post
import model.ServiceItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.StringBuilder

class Services : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_services)

        getPosts()

        btn_back_s.setOnClickListener {
            finish()
        }
    }

    private fun cancelAlert(index: Int, list: ArrayList<ServiceItem>){
        val alertDialog: AlertDialog = this.let {
            val builder = AlertDialog.Builder(it)
            builder.apply {
                setMessage("Are you sure? Once canceled you will not be able to revert the changes ")
                setPositiveButton("Proceed",
                    DialogInterface.OnClickListener { dialog, id ->
                        cancelService(index, list)
                    })
                setNegativeButton("Cancel",
                    DialogInterface.OnClickListener { dialog, id ->
                    })
            }
            builder.create()
        }
        alertDialog.show()
    }

    private fun cancelService(id: Int, list: ArrayList<ServiceItem>){
        list.removeAt(id)
        list_s.adapter = ItemsAdapter(list)
    }

    inner class ItemsAdapter: BaseAdapter {

        var listItems = ArrayList<ServiceItem>()

        constructor(list: ArrayList<ServiceItem>):super(){
            this.listItems = list
        }

        override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
            val myView = layoutInflater.inflate(R.layout.service_item, null)
            val myNode = listItems[p0]
            myView.txt_type_s.text = myNode.type
            myView.txt_status_s.text = myNode.status
            myView.txt_service_s.text = myNode.service
            myView.txt_amount_s.text = StringBuilder().append(" $").append(myNode.price)
            myView.txt_time_s.text = StringBuilder().append(myNode.time).append(" min")
            myView.txt_starts_s.text = myNode.starts
            myView.txt_ends_s.text = myNode.ends
            myView.btn_cancel_s.setOnClickListener { cancelAlert(p0, listItems) }
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

    private fun getPosts(){
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(getString(R.string.api_url))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val apiInterface = retrofit.create(ApiInterface::class.java)
        val call: Call<List<Post>> = apiInterface.getPosts()

        var postList: ArrayList<ServiceItem> = ArrayList<ServiceItem>()

        call.enqueue(object: Callback<List<Post>> {
            override fun onResponse(call: Call<List<Post>>, response: Response<List<Post>>) {

                val responseP = response.body()
                if (responseP!!.size > 0){
                    var i = 0
                    while(responseP.size > i){
                        var elemento = ServiceItem(0,responseP[i].title.toString(),responseP[i].body.toString(),
                            responseP[i].id.toString(),120.0.toFloat(),60,"02/08/2020 12:35 am","02/08/2020 02:35 pm")
                        postList.add(elemento)
                        i += 1
                    }
                    list_s.adapter = ItemsAdapter(postList)
                }
            }
            override fun onFailure(call: Call<List<Post>>, t: Throwable) {
            }
        })
    }
}