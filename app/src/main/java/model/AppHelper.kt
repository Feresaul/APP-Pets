package model

import android.view.View
import android.widget.CheckBox
import kotlinx.android.synthetic.main.product_item.view.*
import java.lang.StringBuilder

class AppHelper{

    fun <T> checkBoxGroup(index: Int, array: ArrayList<T>, itemArray: ArrayList<View>){
        val item = itemArray[index].findViewWithTag<CheckBox>("checkBox")
        item.isChecked = !item.isChecked

        for (i in 0 until array.size) {
            if (i != index) itemArray[i].findViewWithTag<CheckBox>("checkBox").isChecked = false
        }
    }

    inner class Selection{
        var index: Int ?= null
        var id: Int ?= null

        constructor(index: Int, id: Int){
            this.index = index
            this.id = id
        }
    }

    fun checkBoxSelected(array: ArrayList<ServiceItem>, itemArray: ArrayList<View>): Selection{
        for (i in 0 until array.size){
            if (itemArray[i].findViewWithTag<CheckBox>("checkBox").isChecked) return Selection( i, array[i].id!!.toInt())
        }
        return Selection(-1, -1)
    }

    fun getArrayOfHours(item: ServiceHours): ArrayList<String>{
        val array = ArrayList<String>()

        var hr: Int = item.opens!!.split(":")[0].toInt()
        var min: Int = item.opens!!.split(":")[1].toInt()
        val dur: Int = item.time!!+0
        val max_hr: Int = item.closes!!.split(":")[0].toInt()
        val max_min: Int = item.closes!!.split(":")[1].toInt()

        while (true){
            var date = ""
            if (min <= 0) date += "$hr:0$min"
            else date += "$hr:$min"

            min += dur
            if (min >= 60){
                val _hr = min/60
                hr += _hr
                min %= 60
            }

            var active = false
            for (i in 0 until item.data!!.size) {
                if (date == item.data!![i]) {
                    active = true
                    break
                }
            }

            if (min <= 0) date += " - $hr:0$min"
            else date += " - $hr:$min"

            if (!active) array.add(date)
            if (hr >= max_hr && min >= max_min) break
        }
        return array
    }

    fun timeStrBuild(minutes: Int): String{
        var strtime: String
        var time: Int = minutes
        if (minutes >= 60) {
            val h = time/60
            time %= 60
            strtime = StringBuilder().append(h).append(" h  ").toString()
            if (time != 0) strtime = StringBuilder().append(strtime).append(time).append(" min").toString()
        }else{
            strtime = StringBuilder().append(time).append(" min").toString()
        }
        return strtime
    }
}