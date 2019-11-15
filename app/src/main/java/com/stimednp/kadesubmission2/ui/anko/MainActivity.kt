package com.stimednp.kadesubmission2.ui.anko

import android.os.Bundle
import android.util.Log.e
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.stimednp.kadesubmission2.CustomesUI.showProgress
import com.stimednp.kadesubmission2.CustomesUI.showProgressDialog
import com.stimednp.kadesubmission2.R
import com.stimednp.kadesubmission2.api.ApiClient
import com.stimednp.kadesubmission2.model.Leagues
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
    var leagueAddto = ArrayList<Leagues>()
    lateinit var tvProgress: TextView
    lateinit var strProgress: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MainUI(leagueList).setContentView(this)
        showProgressDialog(this)
        tvProgress = showProgress.find(R.id.tv_progress_cust)
        strProgress = getString(R.string.str_progress)
        showDataProgress()

        getIdListLeague()
        swipeRefresh.onRefresh {
            getDataApi()
            if (!showProgress.isShowing){
                showDataProgress()
            }
        }
    }

    private fun showDataProgress() {
        tvProgress.setText(strProgress)
        showProgress.show()
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
            }
        }
    }

    private fun getDataById(id: Int, size: Int) {
        val tsdbService = ApiClient.iServiceTsdb
        GlobalScope.launch(Dispatchers.Main) {
            val listIdLeagues = tsdbService.getDetailById(id)
            try {
                val respose = listIdLeagues.await()
                val responseBody = respose.body()
//                setAdapter(responseBody?.leagues!!, position)
                addToList(responseBody?.leagues!!, size)
            } catch (er: Exception) {
                e("INIII", "Error getDataById ${er.message}")
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
            e("INIII", "LOAD DATA --> $i")
            getDataById(id, listIdLeagues.size)
        }
    }

    fun getDataApi() {
        if (leagueList.size > 0) {
            leagueList.clear()
            rv_main.adapter?.notifyDataSetChanged()
            getIdListLeague()
            toast("load refersh")
        }
    }

    fun addToList(leagues: ArrayList<Leagues>, size: Int) {
        val strProg = ("$strProgress ${leagueAddto.size} of $size")
        leagueAddto.addAll(leagues)
        tvProgress.setText(strProg)
        e("INIII", "SIEEE : ${leagueAddto.size}")
        if (leagueAddto.size == size) {
            toast("STOOOPP SAMA ${leagueAddto.size}")
            setAdapter(leagueAddto)
            leagueAddto.clear()
        }
    }

    private fun setAdapter(leagues: ArrayList<Leagues>) {
        leagueList.addAll(leagues)
        rv_main.adapter?.notifyDataSetChanged()
        if (swipeRefresh.isRefreshing) {
            swipeRefresh.isRefreshing = false
        }
        if (showProgress.isShowing){
            showProgress.dismiss()
        }
    }
}
