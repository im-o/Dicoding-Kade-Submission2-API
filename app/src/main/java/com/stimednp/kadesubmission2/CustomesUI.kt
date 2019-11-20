package com.stimednp.kadesubmission2

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log.e
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by rivaldy on 11/15/2019.
 */

object CustomesUI {
    lateinit var showProgress: Dialog

    fun showProgressDialog(context: Context) {
        showProgress = Dialog(context, R.style.AppTheme_NoActionBar)
        showProgress.window?.setBackgroundDrawable(ColorDrawable(Color.argb(100, 0, 0, 0)))
        showProgress.setContentView(R.layout.custome_progress)
        showProgress.setCancelable(true)
    }

    @SuppressLint("SimpleDateFormat")
    fun changeDateFormat(data: String): String {
        val dateFormat: DateFormat? = SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
        val simpleFormat = SimpleDateFormat("dd MMMM yyyy hh:mm:ss aa zz")
        val date: Date = dateFormat?.parse(data)!!
        simpleFormat.timeZone = TimeZone.getTimeZone("GMT+7")
        return simpleFormat.format(date)
    }
}