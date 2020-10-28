package com.example.petsapp

import android.annotation.SuppressLint
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.BaseAdapter
import kotlinx.android.synthetic.main.activity_history.*
import kotlinx.android.synthetic.main.history_item.view.*
import model.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.StringBuilder

@SuppressLint("ResourceType")
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

        val idUsuario = this.getSharedPreferences("com.up.storedatasharepreferences", Context.MODE_PRIVATE).getInt("id_user", -1)
        loading_progress_H.visibility = View.VISIBLE

        val apiInterface = RetrofitConnection().getApiInterface(applicationContext)
        val call: Call<ResponseT<ArrayList<ServiceItem>>> = apiInterface!!.getHistory(idUsuario)

        call.enqueue(object: Callback<ResponseT<ArrayList<ServiceItem>>> {
            override fun onResponse(call: Call<ResponseT<ArrayList<ServiceItem>>>, response: Response<ResponseT<ArrayList<ServiceItem>>>) {
                val responseP = response.body()
                if (responseP!!.modelo != null) list_h.adapter = ItemsAdapter(responseP.modelo!!)
                if (responseP.modelo!!.size <= 0)
                    AppHelper().myToast(applicationContext,"no items found", R.drawable.ic_baseline_remove_circle_outline_24, getString(R.color.toast_alert))

                loading_progress_H.visibility = View.GONE
            }
            override fun onFailure(call: Call<ResponseT<ArrayList<ServiceItem>>>, t: Throwable) {
                loading_progress_H.visibility = View.GONE
                AppHelper().myToast(applicationContext,"failed to load data", R.drawable.ic_baseline_cancel_24, getString(R.color.toast_error))
                finish()
            }
        })
    }
}