package com.stimednp.kadesubmission2.ui.adapter

import android.util.Log.e
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.stimednp.kadesubmission2.R
import com.stimednp.kadesubmission2.model.EventsLeagues
import com.stimednp.kadesubmission2.model.TeamsBadgeH
import com.stimednp.kadesubmission2.model.TeamsBadgeA
import kotlinx.android.synthetic.main.item_event_match.view.*

/**
 * Created by rivaldy on 11/16/2019.
 */

class LastMatchAdapter(private val items: ArrayList<EventsLeagues>, private val badgesH: ArrayList<TeamsBadgeH>, private val badgesA: ArrayList<TeamsBadgeA>) :
    RecyclerView.Adapter<LastMatchAdapter.LastMatchViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LastMatchViewHolder {
        return LastMatchViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_event_match, parent, false))
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: LastMatchViewHolder, position: Int) {
        holder.bindItem(items[position], badgesH[position], badgesA[position])
    }

    class LastMatchViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        fun bindItem(itemsE: EventsLeagues,  badgesH: TeamsBadgeH?, badgesA: TeamsBadgeA?) {
            val urlimgH = "${badgesH?.strTeamBadge}/preview"
            val urlimgA = "${badgesA?.strTeamBadge}/preview"

            itemView.tv_league_sport.text = itemsE.strSport
            itemView.tv_strevent.text = itemsE.strEvent
            itemView.tv_date_time.text = itemsE.strDate
            itemView.tv_home_score.text = itemsE.intHomeScore.toString()
            itemView.tv_away_score.text = itemsE.intAwayScore.toString()
            itemView.tv_hometeam.text = itemsE.strHomeTeam
            itemView.tv_awayteam.text = itemsE.strAwayTeam

            Picasso.get().load(urlimgH).into(itemView.imgv_hometeam)
            Picasso.get().load(urlimgA).into(itemView.imgv_awayteam)
        }

    }
}