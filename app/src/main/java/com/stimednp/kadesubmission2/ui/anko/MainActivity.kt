package com.stimednp.kadesubmission2.ui.anko

import android.os.Bundle
import android.os.Handler
import android.util.Log.e
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.stimednp.kadesubmission2.R
import com.stimednp.kadesubmission2.api.ApiClient
import com.stimednp.kadesubmission2.invisible
import com.stimednp.kadesubmission2.model.Leagues
import com.stimednp.kadesubmission2.ui.anko.MainUI.Companion.progress
import com.stimednp.kadesubmission2.ui.anko.MainUI.Companion.rv_main
import com.stimednp.kadesubmission2.ui.anko.MainUI.Companion.swipeRefresh
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jetbrains.anko.find
import org.jetbrains.anko.setContentView
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.toast

class MainActivity : AppCompatActivity() {
    var leagueList = ArrayList<Leagues>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MainUI(leagueList).setContentView(this)
        getIdListLeague()
        swipeRefresh.onRefresh {
            getDataApi()
        }
    }

    private fun getIdListLeague() {
        val tsdbService = ApiClient.iServiceTsdb
        GlobalScope.launch(Dispatchers.IO) {
            val listIdLeagues = tsdbService.getListLeagues()
            try {
                val respose = listIdLeagues.await()
                val responseBody = respose.body()
                setById(responseBody?.leagues!!)
            } catch (er: Exception) {
                e("INIII", "Error getDataById ${er.message}")
                progress.invisible()
            }
        }
    }

    private fun getDataById(id: Int) {
        val tsdbService = ApiClient.iServiceTsdb
        GlobalScope.launch(Dispatchers.Main) {
            val listIdLeagues = tsdbService.getDetailById(id)
            try {
                val respose = listIdLeagues.await()
                val responseBody = respose.body()
                setAdapter(responseBody?.leagues!!)
            } catch (er: Exception) {
                e("INIII", "Error getDataById ${er.message}")
                progress.invisible()
            }
        }
    }

    private fun setById(leagues: ArrayList<Leagues>) {
        val listIdLeagues: MutableList<Int> = ArrayList()
        for (i in leagues.indices) {
            val id: Int = leagues[i].idLeague.toInt()
//            getDataById(id)
            listIdLeagues.add(id)
        }
        for (i in listIdLeagues.indices) {
            val id = listIdLeagues.get(i)
            e("INIII","LOAD DATA --> $i")
            getDataById(id)
        }
    }

    fun getDataApi(){
        if (leagueList.size > 0){
            leagueList.clear()
            rv_main.adapter?.notifyDataSetChanged()
            getIdListLeague()
            toast("load refersh")
        }
    }
    private fun setAdapter(leagues: ArrayList<Leagues>) {
        leagueList.addAll(leagues)
        rv_main.adapter?.notifyDataSetChanged()
        if (swipeRefresh.isRefreshing){
            val handler =  Handler()
            handler.postDelayed({
                if (progress.isVisible){
                    progress.invisible()
                }
                swipeRefresh.isRefreshing = false
            },3000)
        }
    }
}
