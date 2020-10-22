package com.example.petsapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.CalendarView
import android.widget.CheckBox
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_service.*
import kotlinx.android.synthetic.main.product_item.view.*
import model.AppHelper
import model.ServiceItem
import java.lang.StringBuilder
import java.text.SimpleDateFormat
import kotlin.collections.ArrayList

class Service : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_service)

        val array = ArrayList<ServiceItem>()
        val itemArray = ArrayList<View>()

        val typeService: String = intent.getStringExtra("typeService").toString()

        //cargar Servicios de Tipo API

        var post = ServiceItem(0, "","Basic Service", "", 150.0.toFloat(), 30, "", "")
        array.add(post)
        var post2 = ServiceItem(1, "","Medium Service", "", 250.0.toFloat(), 45, "", "")
        array.add(post2)
        var post3 = ServiceItem(2, "","Full Service", "", 350.0.toFloat(), 90, "", "")
        array.add(post3)

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
        service_items.addView(calendar)

        btn_back.setOnClickListener {
            finish()
        }

        btn_proceed.setOnClickListener {
            val selection: AppHelper.Selection = AppHelper().checkBoxSelected(array, itemArray)
            val index = selection.index
            val idService = selection.id
            if (idService != -1){
                val intent = Intent(applicationContext, Time::class.java)
                val date: String = SimpleDateFormat("yyyy/MM/dd").format(calendar.date)
                intent.putExtra("idService", idService)
                intent.putExtra("typeService", typeService)
                intent.putExtra("indexCheckbox", index)
                intent.putExtra("calendar", date)
                startActivity(intent)
                finish()
            }else{
                Toast.makeText(applicationContext, "Select Service", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
        }
    }

    override fun onBackPressed() { finish() }
}