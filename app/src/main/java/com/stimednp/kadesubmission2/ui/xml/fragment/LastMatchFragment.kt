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
import com.stimednp.kadesubmission2.model.TeamsBadge
import com.stimednp.kadesubmission2.ui.adapter.EventMatchAdapter
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
    var idLeague: String? = null
    var itemEvents = ArrayList<EventsLeagues>()
    var itemTeamsH = ArrayList<TeamsBadge>()
    var itemTeamsA = ArrayList<TeamsBadge>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_last_match, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dataItems = DetailsActivity.items
        idLeague = dataItems?.idLeague!!
        setIdEvent(idLeague!!)

        val layoutManager = LinearLayoutManager(context)
        rv_lastmatch.layoutManager = layoutManager
        rv_lastmatch.adapter = EventMatchAdapter(context!!, itemEvents, itemTeamsH, itemTeamsA)

    }

    private fun setIdEvent(idLeague: String) {
        val tsdbService = ApiClient.iServiceTsdb
        GlobalScope.launch(Dispatchers.Main) {
            val listIdEvents = tsdbService.getPrevMatch(idLeague)
            try {
                val responseE = listIdEvents.await()
                val resBodyE = responseE.body()
                savetoArrays(resBodyE?.events!!)
            } catch (e: Exception) {
                e("INIII", "ERRROR LAST 1 $e")
                runOnUiThread {
                    tv_loadempty.visible()
                    disabelProgress()
                }
            }
        }
    }

    private fun setIdTeam(events: ArrayList<EventsLeagues>, teamH: ArrayList<Int>, teamA: ArrayList<Int>) {
        val tsdbService = ApiClient.iServiceTsdb
        GlobalScope.launch(Dispatchers.Main) {
            val itemsH = ArrayList<TeamsBadge>()
            val itemsA = ArrayList<TeamsBadge>()
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
                    e("INIII", "ERRROR LAST 2 $e")
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
            val teamH = events[i].idHomeTeam
            val teamA = events[i].idAwayTeam

            badgeH.add(teamH!!)
            badgeA.add(teamA!!)
        }
        setIdTeam(events, badgeH, badgeA)
    }

    private fun disabelProgress() {
        progress_lastmatch.gone()
    }

    private fun setAdapter(itemsE: ArrayList<EventsLeagues>, itemsH: ArrayList<TeamsBadge>, itemsA: ArrayList<TeamsBadge>) {
        itemEvents.clear()
        itemTeamsH.clear()
        itemEvents.addAll(itemsE)
        itemTeamsH.addAll(itemsH)
        itemTeamsA.addAll(itemsA)
        if (rv_lastmatch != null) {
            rv_lastmatch.adapter?.notifyDataSetChanged()
        } else if (idLeague != null) {
            setIdEvent(idLeague!!)
        }
        disabelProgress()
    }
}
