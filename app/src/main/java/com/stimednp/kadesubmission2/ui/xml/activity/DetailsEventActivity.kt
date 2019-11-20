package com.stimednp.kadesubmission2.ui.xml.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.stimednp.kadesubmission2.CustomesUI.changeDateFormat
import com.stimednp.kadesubmission2.R
import kotlinx.android.synthetic.main.activity_search.*

class DetailsEventActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details_event)
        setToolbar()
        testing()
    }

    private fun setToolbar() {
    }

    private fun testing() {
        val date = "2019-02-19 03:30:00"
        changeDateFormat(date)
    }
}
