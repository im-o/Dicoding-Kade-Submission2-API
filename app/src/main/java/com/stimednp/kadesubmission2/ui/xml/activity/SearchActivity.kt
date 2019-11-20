package com.stimednp.kadesubmission2.ui.xml.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log.e
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.stimednp.kadesubmission2.R
import com.stimednp.kadesubmission2.R.color.*
import com.stimednp.kadesubmission2.api.ApiClient
import com.stimednp.kadesubmission2.model.EventsLeagues
import com.stimednp.kadesubmission2.model.TeamsBadgeA
import com.stimednp.kadesubmission2.model.TeamsBadgeH
import com.stimednp.kadesubmission2.ui.adapter.LastMatchAdapter
import com.stimednp.kadesubmission2.visible
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.toast

class SearchActivity : AppCompatActivity(), SearchView.OnQueryTextListener {
    var itemEvents = ArrayList<EventsLeagues>()
    var itemTeamsH = ArrayList<TeamsBadgeH>()
    var itemTeamsA = ArrayList<TeamsBadgeA>()
    var itemSave = ArrayList<EventsLeagues>()
    var textSearch: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        setToolbar()
        initial()

        swipe_search.setColorSchemeResources(colorAccent, colorTwitter, colorYoutube, colorFacebook)
        swipe_search.onRefresh {
            if (!textSearch.equals(null)) {
                if (itemSave.equals(itemEvents)) {
                    toast("No more match, all data is loaded!")
                    disabelProgress()
                } else {
                    searchEvent(textSearch!!)
                }
            } else {
                toast("No data to search...")
                disabelProgress()
            }
        }
    }

    private fun initial() {
        val layoutManager = LinearLayoutManager(this)
        rv_search.layoutManager = layoutManager
        rv_search.adapter = LastMatchAdapter(this, itemEvents, itemTeamsH, itemTeamsA)
    }

    private fun setToolbar() {
        setSupportActionBar(tb_search)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        tb_search.setTitle(R.string.app_title)
        tb_search.setNavigationIcon(R.drawable.ic_keyboard_backspace_black_24dp)
        tb_search.setNavigationOnClickListener { finish() }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.option_search_menu, menu)
        val searchItem = menu?.findItem(R.id.item_search_action)
        val searchView = searchItem?.actionView as SearchView
        searchView.queryHint = getString(R.string.app_search_event)
        searchView.isIconified = false
        searchView.setOnQueryTextListener(this)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query != null) {
            toast(query)
            searchEvent(query)
            textSearch = query
        }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
//        if (newText != null) {
//            toast(newText)
//            searchEvent(newText)
//            textSearch = newText
//        }
        return false
    }

    private fun searchEvent(strEvent: String) {
        enableProgress()
        toast("CARRRIIII DATA")
        val tsdbService = ApiClient.iServiceTsdb
        GlobalScope.launch(Dispatchers.Main) {
            val listEvents = tsdbService.getSearchEvent(strEvent)
            try {
                val responseE = listEvents.await()
                val resBodyE = responseE.body()
                e("INIII", "SEARRCHHH $strEvent : ${resBodyE?.events?.size}")
                savetoArrays(resBodyE?.events!!)
            } catch (er: Exception) {
                e("INIII", "ERRRROR $er")
                runOnUiThread {
                    disabelProgress()
                    if (er.message == KotlinNullPointerException().message) {
                        enableNodata()
                    }
                }
            }
        }
    }

    @SuppressLint("DefaultLocale")
    private fun savetoArrays(events: ArrayList<EventsLeagues>) {
        val badgeH = ArrayList<Int>()
        val badgeA = ArrayList<Int>()

        for (i in events.indices) {
            val sportSoccer = events.get(i).strSport?.toLowerCase()
            val idH = events[i].idHomeTeam!!
            val idA = events[i].idAwayTeam!!
            if (sportSoccer == "soccer") {
                badgeH.add(idH)
                badgeA.add(idA)
            }
        }
        setIdTeam(events, badgeH, badgeA)
    }

    private fun setIdTeam(events: ArrayList<EventsLeagues>, teamH: ArrayList<Int>, teamA: ArrayList<Int>) {
        val tsdbService = ApiClient.iServiceTsdb
        GlobalScope.launch(Dispatchers.Main) {
            val itemsH = ArrayList<TeamsBadgeH>()
            val itemsA = ArrayList<TeamsBadgeA>()
            if (events.size > 0) {
                for (i in events.indices) {
                    try {
                        val listIdHome = tsdbService.getDetailTeamH(teamH[i])
                        val listIdAway = tsdbService.getDetailTeamA(teamA[i])
                        val responseH = listIdHome.await()
                        val bodyH = responseH.body()
                        val responseA = listIdAway.await()
                        val bodyA = responseA.body()
                        itemsH.addAll(bodyH?.teams!!)
                        itemsA.addAll(bodyA?.teams!!)
                    } catch (er: Exception) {
                        e("INIII", "ERRRRORR 2 $er")
                        runOnUiThread { disabelProgress() }
                    }
                }
                setAdapter(events, itemsH, itemsA)
            } else {
                enableNodata()
                disabelProgress()
            }
        }
    }


    private fun setAdapter(itemsE: ArrayList<EventsLeagues>, itemsH: ArrayList<TeamsBadgeH>, itemsA: ArrayList<TeamsBadgeA>) {
        itemEvents.clear()
        itemTeamsH.clear()
        itemTeamsA.clear()
        itemSave.clear()

        itemEvents.addAll(itemsE)
        itemTeamsH.addAll(itemsH)
        itemTeamsA.addAll(itemsA)
        itemSave.addAll(itemsE)
        toast("CARIII : ${itemsE.size}")
        if (rv_search.adapter != null) {
            rv_search.adapter?.notifyDataSetChanged()
        }
        disabelProgress()
    }

    private fun disabelProgress() {
        if (swipe_search.isRefreshing) {
            swipe_search.isRefreshing = false
        }
    }

    private fun enableProgress() {
        if (!swipe_search.isRefreshing) {
            swipe_search.isRefreshing = true
        }
    }

    private fun enableNodata() {
        if (tv_empty_datas.isVisible) {
            tv_empty_datas.visible()
        }
    }
}
