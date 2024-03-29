package com.example.petsapp

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.CalendarView
import android.widget.CalendarView.OnDateChangeListener
import android.widget.CheckBox
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_service.*
import kotlinx.android.synthetic.main.product_item.view.*
import model.AppHelper
import model.ResponseT
import model.RetrofitConnection
import model.ServiceItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat


@SuppressLint("ResourceType")
class Service : AppCompatActivity() {

    val itemArray = ArrayList<View>()
    val array = ArrayList<ServiceItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_service)

        getItems()
    }

    @SuppressLint("SimpleDateFormat")
    private fun completeView(){
        for (i in 0 until array.size){
            val appHelper = AppHelper()
            val item = layoutInflater.inflate(R.layout.product_item, null)
            itemArray.add(item)
            val element = array[i]
            item.txt_e_service_item.text = element.service
            val strtime: String = appHelper.timeStrBuild(element.time!!.toInt())
            item.txt_e_time_item.text = StringBuilder().append(strtime)
            item.txt_e_price_item.text = element.price.toString()
            service_items.addView(item)

            item.item_e.setOnClickListener {
                appHelper.checkBoxGroup(i, array, itemArray)
            }
        }

        val indexCheckbox = intent.getIntExtra("indexCheckbox", -1)
        if (indexCheckbox != -1){
            itemArray[indexCheckbox].findViewWithTag<CheckBox>("checkBox").isChecked = true
        }

        val calendar = CalendarView(this)
        calendar.minDate = calendar.date
        service_items.addView(calendar)
        val datetime = intent.getStringExtra("calendar")
        if (datetime != null && datetime != ""){
            calendar.setDate(SimpleDateFormat("yyyy/MM/dd").parse(datetime).time,false,false)
        }

        calendar.setOnDateChangeListener { view, year, month, dayOfMonth ->
            val monthI = month + 1
            calendar.setDate(SimpleDateFormat("yyyy/MM/dd").parse("$year/$monthI/$dayOfMonth").time,false,false)
        }

        btn_back.setOnClickListener {
            finish()
        }

        btn_proceed.setOnClickListener {
            val selection: AppHelper.Selection = AppHelper().checkBoxSelected(array, itemArray)
            val index = selection.index
            val idService = selection.id
            if (idService != -1){
                val typeService: String = intent.getStringExtra("typeService").toString()
                val intent = Intent(applicationContext, Time::class.java)
                val date: String = SimpleDateFormat("yyyy/MM/dd").format(calendar.date)
                intent.putExtra("idService", idService)
                intent.putExtra("typeService", typeService)
                intent.putExtra("indexCheckbox", index)
                intent.putExtra("calendar", date)
                startActivity(intent)
                finish()
            }else{
                AppHelper().myToast(
                    applicationContext,
                    "select a service",
                    R.drawable.ic_baseline_error_outline_24,
                    getString(
                        R.color.toast_alert
                    )
                )
                return@setOnClickListener
            }
        }
    }

    override fun onBackPressed() { finish() }

    private fun getItems(){

        loading_progress.visibility = View.VISIBLE

        val apiInterface = RetrofitConnection().getApiInterface(applicationContext)
        val call: Call<ResponseT<ArrayList<ServiceItem>>> = apiInterface!!.getServicesByType(
            intent.getStringExtra(
                "typeService"
            ).toString()
        )

        call.enqueue(object : Callback<ResponseT<ArrayList<ServiceItem>>> {
            override fun onResponse(
                call: Call<ResponseT<ArrayList<ServiceItem>>>,
                response: Response<ResponseT<ArrayList<ServiceItem>>>
            ) {
                val responseP = response.body()
                if (!responseP!!.error!!) {
                    if (responseP.modelo != null)
                        array.addAll(responseP.modelo!!)
                    completeView()
                    loading_progress.visibility = View.GONE
                }
            }

            override fun onFailure(call: Call<ResponseT<ArrayList<ServiceItem>>>, t: Throwable) {
                loading_progress.visibility = View.GONE
                AppHelper().myToast(
                    applicationContext,
                    "failed to load data",
                    R.drawable.ic_baseline_cancel_24,
                    getString(
                        R.color.toast_error
                    )
                )
                finish()
            }
        })
    }
}