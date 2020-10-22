package com.example.petsapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.CheckBox
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_time.*
import kotlinx.android.synthetic.main.hour_item.view.*
import model.AppHelper
import model.ServiceHours

class Time : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_time)

        var datetime: String = intent.getStringExtra("date").toString()
        val idService: Int = intent.getIntExtra("idService", -1)

        val itemArray =  java.util.ArrayList<View>()

        //Pedir horas ocupadas API

        var arrayList = ArrayList<String>()
        arrayList.add("10:30")
        arrayList.add("15:00")

        val serviceHours = ServiceHours("9:00", "18:00", 45, arrayList)
        val array = AppHelper().getArrayOfHours(serviceHours)

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

        btn_proceed_hr.setOnClickListener {
            var id = -1
            for (i in 0 until itemArray.size){
                if (itemArray[i].findViewWithTag<CheckBox>("checkBox").isChecked){
                    val time = array[i]
                    datetime += " $time"
                    id = i
                    break
                }
            }

            if (id !=-1){
                // agendar cita API

                val intent = Intent(applicationContext, Home::class.java)
                startActivity(intent)
                finish()
            }
            else{
            Toast.makeText(applicationContext, "Select time", Toast.LENGTH_LONG).show()
            return@setOnClickListener
            }
        }
    }

    override fun onBackPressed() { finish() }
}