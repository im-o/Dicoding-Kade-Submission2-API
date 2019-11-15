package com.stimednp.kadesubmission2

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable

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
}