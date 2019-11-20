package com.stimednp.kadesubmission2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log.e
import com.stimednp.kadesubmission2.api.ApiClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jetbrains.anko.toast

class TestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
        search()
    }

    private fun search() {
        val tsdb = ApiClient.iServiceTsdb
        GlobalScope.launch(Dispatchers.Main){
            val listData = tsdb.getSearchEvent("B")
            val response = listData.await()
            val resBodey = response.body()
            toast("HASILLLLL : ${resBodey?.events?.size}")
            toast("HASILLLLL BODY : ${resBodey}")
            e("INIII","INII HASIL : ${resBodey?.events?.size}")
        }
    }
}
