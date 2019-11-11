package com.stimednp.kadesubmission2.ui.anko

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log.d
import android.util.Log.e
import android.widget.ProgressBar
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.stimednp.kadesubmission2.R
import com.stimednp.kadesubmission2.api.ApiClient
import com.stimednp.kadesubmission2.api.IServiceTsdb
import com.stimednp.kadesubmission2.invisible
import com.stimednp.kadesubmission2.model.Leagues
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.find
import org.jetbrains.anko.setContentView
import org.jetbrains.anko.toast
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    var leagueList = ArrayList<Leagues>()
    lateinit var rv_main: RecyclerView
    lateinit var progress: ProgressBar
    lateinit var swipeRefresh: SwipeRefreshLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MainUI(leagueList).setContentView(this)
        rv_main = find(R.id.rv_main)
        swipeRefresh = find(R.id.swipe_main)
        progress = find(R.id.progress_main)

        getIdListLeague()
    }

    private fun getIdListLeague() {
        val tsdbService = ApiClient.iServiceTsdb
        GlobalScope.launch(Dispatchers.Main){
            val listIdLeagues = tsdbService.getListLeagues()
            try {
                val respose = listIdLeagues.await()
                val responseBody = respose.body()
                doInbg(responseBody?.leagues!!)
            }catch (er: Exception){
                e("INIII","Error getDataById ${er.message}")
                progress.invisible()
            }
        }
    }

    private fun getDataById(id: Int) {
        val tsdbService = ApiClient.iServiceTsdb
        GlobalScope.launch(Dispatchers.Main){
            val listIdLeagues = tsdbService.getDetailById(id)
            try {
                val respose = listIdLeagues.await()
                val responseBody = respose.body()
                setAdapter(responseBody?.leagues!!)
            }catch (er: Exception){
                e("INIII","Error getDataById ${er.message}")
                progress.invisible()
            }
        }
    }

    private fun doInbg(leagues: ArrayList<Leagues>) {
        doAsync {
            for (i in leagues.indices){
                val id: Int = leagues[i].idLeague.toInt()
                getDataById(id)
            }
        }
    }

    private fun setAdapter(leagues: ArrayList<Leagues>) {
        leagueList.addAll(leagues)
        rv_main.adapter?.notifyDataSetChanged()
        progress.invisible()
    }
}
