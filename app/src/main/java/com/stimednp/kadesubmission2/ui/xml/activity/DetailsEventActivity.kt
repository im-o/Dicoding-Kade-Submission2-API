package com.stimednp.kadesubmission2.ui.xml.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.stimednp.kadesubmission2.CustomesUI.changeDateFormat
import com.stimednp.kadesubmission2.R
import kotlinx.android.synthetic.main.activity_details_event.*

class DetailsEventActivity : AppCompatActivity() {
    companion object{
        val EXTRA_DATA: String = "extra_data"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details_event)
        setToolbar()
        initial()
        setData()
    }

    private fun initial() {
        //head

    }

    private fun setToolbar() {
        setSupportActionBar(toolbar_statis)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        toolbar_statis.setTitle(R.string.app_title)
        toolbar_statis.setNavigationIcon(R.drawable.ic_keyboard_backspace_black_24dp)
        toolbar_statis.setNavigationOnClickListener { finish() }
    }

    private fun setData() {
        val date = "2019-02-19 03:30:00"
        changeDateFormat(date)
    }
}
