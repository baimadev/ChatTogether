package com.example.chattogether.adapters

import android.annotation.SuppressLint
import android.app.Activity
import android.graphics.Color
import android.text.InputType
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.bigkoo.pickerview.builder.TimePickerBuilder
import com.example.chattogether.data.User
import com.example.chattogether.viewmodels.UserViewModel
import java.text.SimpleDateFormat
import java.util.*

object BindingAdapters {


    @JvmStatic
    @BindingAdapter("setPicker")
    fun setBorthPicker(dateEdit:EditText, user: UserViewModel) {
        setPicker(dateEdit,user)
    }

    @SuppressLint("ClickableViewAccessibility")
    fun setPicker(dateEdit:EditText,user: UserViewModel){
        dateEdit.setRawInputType(InputType.TYPE_NULL)
        //build timePicker
        val timePicker = TimePickerBuilder(dateEdit.context) { date: Date?, v: View? ->
            date?.let {
                val sdf = SimpleDateFormat("yyyy年M月d日")
                //设置年龄
                val cDate=Date(System.currentTimeMillis())
                val calendar=Calendar.getInstance()
                calendar.time=cDate
                val cYear=calendar.get(Calendar.YEAR)
                calendar.time=date
                val uYear=calendar.get(Calendar.YEAR)
                user.user.age=cYear-uYear
                dateEdit.setText(sdf.format(it))
            }
        }
            .setContentTextSize(25)
            .setSubmitText("确认")
            .setCancelText("取消")
            .setLabel("年", "月", "日", "时", "分", "秒")
            .setSubmitColor(Color.parseColor("#FFF5A623"))
            .setCancelColor(Color.parseColor("#FFF5A623"))
            .build()

        dateEdit.setOnTouchListener { _, _ ->
            val activity = dateEdit.context as Activity
            val imm: InputMethodManager? =
                ContextCompat.getSystemService(dateEdit.context!!, InputMethodManager::class.java)
            imm?.hideSoftInputFromWindow(activity.window.decorView.windowToken, 0)
            timePicker.show()
            true
        }
    }



}