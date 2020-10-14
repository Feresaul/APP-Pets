package com.example.petsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import kotlinx.android.synthetic.main.activity_esthetic.*
import kotlinx.android.synthetic.main.product_item.view.*
import model.ServiceItem
import java.lang.StringBuilder

class Esthetic : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_esthetic)

        val array = ArrayList<ServiceItem>()
        val itemArray = ArrayList<View>()
        var post = ServiceItem(0, "","Basic Service", "", 150.0.toFloat(), 30, "", "")
        array.add(post)
        var post2 = ServiceItem(1, "","Medium Service", "", 250.0.toFloat(), 45, "", "")
        array.add(post2)
        var post3 = ServiceItem(2, "","Full Service", "", 350.0.toFloat(), 180, "", "")
        array.add(post3)

        for (i in 0 until array.size){
            val item = layoutInflater.inflate(R.layout.product_item, null)
            itemArray.add(item)
            val element = array[i]
            item.txt_e_service_item.text = element.service
            item.txt_e_time_item.text = StringBuilder().append(element.time).append(" min")
            item.txt_e_price_item.text = element.price.toString()
            e_service_items.addView(item)
            item.item_e.setOnClickListener {
                checkBoxListener(i, array, itemArray)
            }
        }

        btn_back_e.setOnClickListener {
            finish()
        }

        btn_proceed_e.setOnClickListener {
            //to-do
        }
    }

    private fun checkBoxListener(index: Int, array: ArrayList<ServiceItem>, itemArray: ArrayList<View>){
        val item = itemArray[index].checkBox
        item.isChecked = !item.isChecked

        for (i in 0 until array.size) {
            if (i != index) itemArray[i].checkBox.isChecked = false
        }
    }

    override fun onBackPressed() { finish() }
}