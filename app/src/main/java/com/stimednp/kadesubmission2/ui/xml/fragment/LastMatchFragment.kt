package com.stimednp.kadesubmission2.ui.xml.fragment


import android.os.Bundle
import android.util.Log.e
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.stimednp.kadesubmission2.R
import com.stimednp.kadesubmission2.api.ApiClient
import com.stimednp.kadesubmission2.gone
import com.stimednp.kadesubmission2.model.EventsLeagues
import com.stimednp.kadesubmission2.model.TeamsBadgeA
import com.stimednp.kadesubmission2.model.TeamsBadgeH
import com.stimednp.kadesubmission2.ui.adapter.LastMatchAdapter
import com.stimednp.kadesubmission2.ui.xml.activity.DetailsActivity
import com.stimednp.kadesubmission2.visible
import kotlinx.android.synthetic.main.fragment_last_match.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jetbrains.anko.support.v4.runOnUiThread

/**
 * A simple [Fragment] subclass.
 */
class LastMatchFragment : Fragment() {
    var itemEvents = ArrayList<EventsLeagues>()
    var itemTeamsH = ArrayList<TeamsBadgeH>()
    var itemTeamsA = ArrayList<TeamsBadgeA>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_last_match, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dataItems = DetailsActivity.items
        val idLeague = dataItems?.idLeague?.toInt()
        setIdEvent(idLeague!!)

        val layoutManager = LinearLayoutManager(context)
        rv_lastmatch.layoutManager = layoutManager
        rv_lastmatch.adapter = LastMatchAdapter(context!!, itemEvents, itemTeamsH, itemTeamsA)

    }

    private fun setIdEvent(idLeague: Int) {
        val tsdbService = ApiClient.iServiceTsdb
        GlobalScope.launch(Dispatchers.Main) {
            val listIdEvents = tsdbService.getPrevMatch(idLeague)
            try {
                val responseE = listIdEvents.await()
                val resBodyE = responseE.body()
                savetoArrays(resBodyE?.events!!)
            } catch (e: Exception) {
                e("INIII", "ERRRROR $e")
                runOnUiThread {
                    disabelProgress()
                    if (e.message == KotlinNullPointerException().message) {
                        tv_empty_lastmath.visible()
                    }
                }
            }
        }
    }

    private fun setIdTeam(events: ArrayList<EventsLeagues>, teamH: ArrayList<Int>, teamA: ArrayList<Int>) {
        val tsdbService = ApiClient.iServiceTsdb
        GlobalScope.launch(Dispatchers.Main) {
            val itemsH = ArrayList<TeamsBadgeH>()
            val itemsA = ArrayList<TeamsBadgeA>()
            for (i in events.indices) {
                val listIdHome = tsdbService.getDetailTeamH(teamH[i])
                val listIdAway = tsdbService.getDetailTeamA(teamA[i])
                try {
                    val responseH = listIdHome.await()
                    val bodyH = responseH.body()
                    val responseA = listIdAway.await()
                    val bodyA = responseA.body()
                    itemsH.addAll(bodyH?.teams!!)
                    itemsA.addAll(bodyA?.teams!!)
                } catch (e: Exception) {
                    e("INIII", "ERRRRORR 2 $e")
                    runOnUiThread { disabelProgress() }
                }
            }
            setAdapter(events, itemsH, itemsA)
        }
    }

    private fun savetoArrays(events: ArrayList<EventsLeagues>) {
        val badgeH = ArrayList<Int>()
        val badgeA = ArrayList<Int>()

        for (i in events.indices) {
            val teamH = events.get(i).idHomeTeam
            val teamA = events.get(i).idAwayTeam

            badgeH.add(teamH!!)
            badgeA.add(teamA!!)
        }
        setIdTeam(events, badgeH, badgeA)
    }

    private fun disabelProgress() {
        progress_lastmatch.gone()
    }

    private fun setAdapter(itemsE: ArrayList<EventsLeagues>, itemsH: ArrayList<TeamsBadgeH>, itemsA: ArrayList<TeamsBadgeA>) {
        itemEvents.clear()
        itemTeamsH.clear()
        itemEvents.addAll(itemsE)
        itemTeamsH.addAll(itemsH)
        itemTeamsA.addAll(itemsA)
        rv_lastmatch.adapter?.notifyDataSetChanged()
        disabelProgress()
    }
}
