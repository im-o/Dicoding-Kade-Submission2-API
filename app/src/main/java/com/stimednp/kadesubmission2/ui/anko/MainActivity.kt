package com.stimednp.kadesubmission2.ui.anko

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.stimednp.kadesubmission2.R
import com.stimednp.kadesubmission2.model.Leagues
import org.jetbrains.anko.setContentView

class MainActivity : AppCompatActivity() {
    var leagues = ArrayList<Leagues>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MainUI(leagues).setContentView(this)
    }
}
