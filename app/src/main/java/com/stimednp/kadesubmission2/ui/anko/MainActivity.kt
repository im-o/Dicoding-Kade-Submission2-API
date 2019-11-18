package com.stimednp.kadesubmission2.ui.anko

import android.os.Bundle
import android.os.Handler
import android.util.Log.e
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.stimednp.kadesubmission2.CustomesUI.showProgress
import com.stimednp.kadesubmission2.CustomesUI.showProgressDialog
import com.stimednp.kadesubmission2.R
import com.stimednp.kadesubmission2.api.ApiClient
import com.stimednp.kadesubmission2.invisible
import com.stimednp.kadesubmission2.model.Leagues
import com.stimednp.kadesubmission2.ui.anko.MainUI.Companion.rv_main
import com.stimednp.kadesubmission2.ui.anko.MainUI.Companion.swipeRefresh
import com.stimednp.kadesubmission2.ui.anko.MainUI.Companion.tv_nodata
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jetbrains.anko.find
import org.jetbrains.anko.longToast
import org.jetbrains.anko.setContentView
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.toast

class MainActivity : AppCompatActivity() {
    var leagueList = ArrayList<Leagues>()
    var leagueAddto = ArrayList<Leagues>()
    var sizeListId: Int = 0
    val items = ArrayList<Leagues>()
    var sizeLen = 0
    var isBackPress = false
    lateinit var tvProgress: TextView
    lateinit var strProgress: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MainUI(leagueList).setContentView(this)
        init()
        showDataProgress()
        getIdListLeague()
        swipeRefresh.onRefresh {
            getDataApi()
        }
    }

    private fun init() {
        showProgressDialog(this)
        tvProgress = showProgress.find(R.id.tv_progress_cust)
        strProgress = getString(R.string.str_progress)
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
                runOnUiThread { disableProgress() }
                e("INIII", "Error getDataById ${er.message}")
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
                addToList(responseBody?.leagues!!)
//                setAdapter(responseBody?.leagues!!)
                filterList(responseBody.leagues)
            } catch (er: Exception) {
                runOnUiThread { disableProgress() }
                e("INIII", "Error getDataById ${er.message}")
            }
        }
    }

    private fun filterList(leagues: java.util.ArrayList<Leagues>) { //loop stop after size == leagues.size
        items.addAll(leagues)
        if (sizeListId > 20){
            e("INIII","UKURAN : ${items.size}")
//            toast("size ${leagues.size}")
            sizeLen = sizeLen+1
            if (items.size == 20){
                setAdapter(items)
//                toast("20 items : ${items.size}")
                items.clear()
            } else if (sizeListId.equals(sizeLen) && items.size != 0){
                toast("SAMA size items ${sizeLen} dan items size : ${items.size}")
                setAdapter(items)
                items.clear()
            }
        } else{
            toast("kurang")
            setAdapter(items)
        }
    }

    private fun setById(leagues: ArrayList<Leagues>) {
        val listIdLeagues: MutableList<Int> = ArrayList()
        sizeListId = leagues.size
        for (i in leagues.indices) {
            val id = leagues[i].idLeague!!.toInt()
            listIdLeagues.add(id)
        }
        for (i in listIdLeagues.indices) {
            val id = listIdLeagues.get(i)
            getDataById(id)
        }
    }

    fun getDataApi() {
        if (leagueAddto.size >= sizeListId && sizeListId != 0) {
            e("INIII", "${leagueAddto.size}")
            setAdapter(leagueAddto)
        } else {
//            showDataProgress()
            getIdListLeague()
//            toast("no")
        }
    }

    fun addToList(leagues: ArrayList<Leagues>) {
        val strProg = ("$strProgress ${leagueAddto.size} of $sizeListId")
        leagueAddto.addAll(leagues)
        tvProgress.setText(strProg)

        if (leagueAddto.size == sizeListId) {
            tv_nodata.invisible()
//            setAdapter(leagueAddto)
            longToast("${getString(R.string.str_loadsucces)} $sizeListId")
//            leagueAddto.clear()
        }
    }

    private fun setAdapter(leagues: ArrayList<Leagues>) {
//        toast("sizee : $sizeListId")
        disableProgress()
//        leagueList.clear()
        leagueList.addAll(leagues)
        rv_main.adapter?.notifyDataSetChanged()
    }

    override fun onBackPressed() {
        val strClose = resources.getString(R.string.tap_to_close)
        if (isBackPress) {
            toast("exit")
//            super.onBackPressed()
            return
        }
        isBackPress = true
        toast(strClose)
        Handler().postDelayed({
            isBackPress = false
            disableProgress()
        }, 2000)
    }

    fun disableProgress() {
        if (swipeRefresh.isRefreshing) {
            swipeRefresh.isRefreshing = false
        }
        if (showProgress.isShowing) {
            showProgress.dismiss()
        }
    }
}
