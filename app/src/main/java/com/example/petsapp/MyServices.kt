package com.example.petsapp

import android.annotation.SuppressLint
import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.BaseAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_my_services.*
import kotlinx.android.synthetic.main.service_item.view.*
import model.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.StringBuilder

@SuppressLint("ResourceType")
class MyServices : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_my_services)

        getItems()

        btn_back_s.setOnClickListener {
            finish()
        }
    }

    override fun onBackPressed() { finish() }

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

    private fun cancelService(index: Int, list: ArrayList<ServiceItem>){
        println("DATA")
        val idCita: Int = list[index].id!!.toInt()
        deleteItem(idCita)
        list.removeAt(index)
        println(idCita)
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
            myView.txt_time_s.text = AppHelper().timeStrBuild(myNode.time!!.toInt())
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

    private fun getItems(){
        val idUsuario = this.getSharedPreferences("com.up.storedatasharepreferences", Context.MODE_PRIVATE).getInt("id_user", -1)
        loading_progress_S.visibility = View.VISIBLE

        val apiInterface = RetrofitConnection(applicationContext).getApiInterface()
        val call: Call<ResponseT<ArrayList<ServiceItem>>> = apiInterface!!.getServices(idUsuario)

        call.enqueue(object: Callback<ResponseT<ArrayList<ServiceItem>>> {
            override fun onResponse(call: Call<ResponseT<ArrayList<ServiceItem>>>, response: Response<ResponseT<ArrayList<ServiceItem>>>) {
                val responseP = response.body()
                if (responseP!!.modelo != null) list_s.adapter = ItemsAdapter(responseP.modelo!!)
                if (responseP.modelo!!.size <= 0)
                    AppHelper().myToast(applicationContext,"no items found", R.drawable.ic_baseline_remove_circle_outline_24, getString(R.color.toast_alert))
                loading_progress_S.visibility = View.GONE
            }
            override fun onFailure(call: Call<ResponseT<ArrayList<ServiceItem>>>, t: Throwable) {
                loading_progress_S.visibility = View.GONE
                AppHelper().myToast(applicationContext,"failed to load data", R.drawable.ic_baseline_cancel_24, getString(R.color.toast_error))
                finish()
            }
        })
    }

    private fun deleteItem(idCita: Int){
        val apiInterface = RetrofitConnection(applicationContext).getApiInterface()
        val call: Call<ResponseT<Int>> = apiInterface!!.deleteService(idCita)

        call.enqueue(object: Callback<ResponseT<Int>> {
            override fun onResponse(call: Call<ResponseT<Int>>, response: Response<ResponseT<Int>>) {
                Toast.makeText(applicationContext, response.body()!!.modelo!! , Toast.LENGTH_LONG).show()
            }
            override fun onFailure(call: Call<ResponseT<Int>>, t: Throwable) {
                AppHelper().myToast(applicationContext,"cancellation error", R.drawable.ic_baseline_cancel_24, getString(R.color.toast_error))
                finish()
            }
        })
    }
}