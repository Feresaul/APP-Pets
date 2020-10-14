package model

import android.view.View
import kotlinx.android.synthetic.main.product_item.view.*
import java.lang.StringBuilder

class AppHelper{

     fun checkBoxGroup(index: Int, array: ArrayList<ServiceItem>, itemArray: ArrayList<View>){
        val item = itemArray[index].checkBox
        item.isChecked = !item.isChecked

        for (i in 0 until array.size) {
            if (i != index) itemArray[i].checkBox.isChecked = false
        }
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