package com.example.petsapp

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.CheckBox
import android.widget.Toast
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_time.*
import kotlinx.android.synthetic.main.hour_item.view.*
import model.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@SuppressLint("ResourceType")
class Time : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_time)

        val datetime: String = intent.getStringExtra("calendar").toString()
        val idService: Int = intent.getIntExtra("idService", -1)

        val serviceHours = ServiceHours()
        serviceHours.id = idService
        serviceHours.date = datetime
        val itemArray = ArrayList<View>()
        addHours(serviceHours, itemArray)

        btn_back_hr.setOnClickListener {
            val typeService: String = intent.getStringExtra("typeService").toString()
            val indexCheckbox: Int = intent.getIntExtra("indexCheckbox", -1)
            val intent = Intent(applicationContext, Service::class.java)
            intent.putExtra("typeService", typeService)
            intent.putExtra("indexCheckbox", indexCheckbox)
            intent.putExtra("calendar", datetime)
            startActivity(intent)
            finish()
        }
    }

    fun addItemsView(datetimeI: String, itemArray: ArrayList<View>, serviceHours: ServiceHoursOut){
        var datetime = datetimeI
        val array = AppHelper().getArrayOfHours(serviceHours)
        if (array.size <= 0)
            AppHelper().myToast(applicationContext,"no space available", R.drawable.ic_baseline_error_outline_24, getString(R.color.toast_alert))

        for (i in 0 until array.size){
            val appHelper = AppHelper()
            val item = layoutInflater.inflate(R.layout.hour_item, null)
            itemArray.add(item)
            val element = array[i]
            item.txt_time_hr.text = element
            hour_items.addView(item)

            item.item_hr.setOnClickListener {
                appHelper.checkBoxGroup(i, array, itemArray)
            }
        }

        btn_proceed_hr.setOnClickListener {
            var id = -1
            for (i in 0 until itemArray.size){
                if (itemArray[i].findViewWithTag<CheckBox>("checkBox").isChecked){
                    val time = array[i].split("-")
                    datetime += " "+time[0]
                    id = i
                    break
                }
            }

            if (id !=-1){
                val service = AddServiceItem()
                service.idService = intent.getIntExtra("idService", -1)
                service.starts = datetime
                val sharedPreferences = this.getSharedPreferences("com.up.storedatasharepreferences", Context.MODE_PRIVATE)
                service.idUser = sharedPreferences.getInt("id_user", -1)
                addItem(service)
            }
            else{
                AppHelper().myToast(applicationContext, "select time", R.drawable.ic_baseline_error_outline_24, getString(R.color.toast_alert))
                return@setOnClickListener
            }
        }
    }

    private fun addHours(serviceHours: ServiceHours, itemArray: ArrayList<View>){
        loading_progress_h.visibility = View.VISIBLE
        scroll_time.visibility = View.GONE

        val apiInterface = RetrofitConnection().getApiInterface(applicationContext)
        val call: Call<ResponseT<ServiceHoursOut>> = apiInterface!!.getServiceHours(serviceHours)

        call.enqueue(object: Callback<ResponseT<ServiceHoursOut>> {
            override fun onResponse(call: Call<ResponseT<ServiceHoursOut>>, response: Response<ResponseT<ServiceHoursOut>>) {
                val responseP = response.body()
                if (!responseP!!.error!!){
                    loading_progress_h.visibility = View.GONE
                    scroll_time.visibility = View.VISIBLE
                    addItemsView(serviceHours.date!!, itemArray, responseP.modelo!!)
                }
            }
            override fun onFailure(call: Call<ResponseT<ServiceHoursOut>>, t: Throwable) {
                loading_progress_h.visibility = View.GONE
                scroll_time.visibility = View.VISIBLE
                AppHelper().myToast(applicationContext, "error", R.drawable.ic_baseline_cancel_24, getString(R.color.toast_error))
            }
        })
    }

    private fun addItem(service: AddServiceItem){
        loading_progress_h.visibility = View.VISIBLE
        scroll_time.visibility = View.GONE

        val apiInterface = RetrofitConnection().getApiInterface(applicationContext)
        val call: Call<ResponseT<Int>> = apiInterface!!.addService(service)

        call.enqueue(object: Callback<ResponseT<Int>> {
            override fun onResponse(call: Call<ResponseT<Int>>, response: Response<ResponseT<Int>>) {
                val responseP = response.body()
                if (!responseP!!.error!!){
                    loading_progress_h.visibility = View.GONE
                    scroll_time.visibility = View.VISIBLE
                    AppHelper().myToast(applicationContext, responseP.mensaje!!, R.drawable.ic_baseline_assignment_turned_in_24, getString(R.color.colorAccent))
                    val intent = Intent(applicationContext, Home::class.java)
                    startActivity(intent)
                    finish()
                }
            }
            override fun onFailure(call: Call<ResponseT<Int>>, t: Throwable) {
                loading_progress_h.visibility = View.GONE
                scroll_time.visibility = View.VISIBLE
                AppHelper().myToast(applicationContext, "error", R.drawable.ic_baseline_cancel_24, getString(R.color.toast_error))
            }
        })
    }

    override fun onBackPressed() { finish() }
}