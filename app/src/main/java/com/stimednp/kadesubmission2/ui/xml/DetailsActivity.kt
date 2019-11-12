package com.stimednp.kadesubmission2.ui.xml

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.stimednp.kadesubmission2.R

class DetailsActivity : AppCompatActivity() {
    companion object{
        public val EXTRA_DATA: String = "extra_data"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
    }
}
