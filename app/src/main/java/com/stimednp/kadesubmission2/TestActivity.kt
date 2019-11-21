package com.stimednp.kadesubmission2

import android.os.Bundle
import android.util.Log.e
import androidx.appcompat.app.AppCompatActivity
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
//        CoroutineScope(Dispatchers.IO).launch {
//            val response = tsdb.getSearchEvent("B")
//            withContext(Dispatchers.Main){
//                try {
//                    if (response.isCompleted){
//                        toast("successs")
//                        val res = response.await()
//                        val body = res.body()
//                        toast("HASIL : ${body?.events?.size}")
//                    }else{
//                        toast("not completes error : ${response.hashCode()}")
//                    }
//                }catch (er: HttpException){
//                    toast("errrorrr : ${er.message()}")
//                }catch (er: Throwable){
//                    toast("errorr : ${er.message}")
//                }
//            }
//        }
        GlobalScope.launch(Dispatchers.Main) {
            val listData = tsdb.getSearchEvent("B")
            try {
                val response = listData.await()
                val resBodey = response.body()
                toast("HASILLLLL : ${resBodey?.event?.size}")
                toast("HASILLLLL BODY : ${resBodey?.event?.get(0)?.idHomeTeam}")
                e("INIII", "INII HASIL : ${resBodey?.event?.size}")
            } catch (e: Exception) {
                toast("error : ${e.message}")
            }
        }
    }
}
