package com.stimednp.kadesubmission2.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import com.stimednp.kadesubmission2.CustomesUI
import com.stimednp.kadesubmission2.R
import com.stimednp.kadesubmission2.invisible
import com.stimednp.kadesubmission2.model.EventsLeagues
import com.stimednp.kadesubmission2.model.TeamsBadge
import com.stimednp.kadesubmission2.ui.xml.activity.DetailsEventActivity
import kotlinx.android.synthetic.main.item_event_match.view.*
import org.jetbrains.anko.startActivity

/**
 * Created by rivaldy on 11/16/2019.
 */

class EventMatchAdapter(
    private val context: Context, private val items: ArrayList<EventsLeagues>,
    private val badgesH: ArrayList<TeamsBadge>, private val badgesA: ArrayList<TeamsBadge>
) :
    RecyclerView.Adapter<EventMatchAdapter.LastMatchViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LastMatchViewHolder {
        return LastMatchViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_event_match, parent, false))
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: LastMatchViewHolder, position: Int) {
        holder.bindItem(items[position], badgesH[position], badgesA[position])
        holder.view.setOnClickListener() {
            context.startActivity<DetailsEventActivity>(
                DetailsEventActivity.EXTRA_DATA_EVENT to items[position],
                DetailsEventActivity.EXTRA_BADGEH to badgesH[position],
                DetailsEventActivity.EXTRA_BADGEA to badgesA[position]
            )
        }
    }

    class LastMatchViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        fun bindItem(itemsE: EventsLeagues?, badgesH: TeamsBadge?, badgesA: TeamsBadge?) {
            val urlimgH = "${badgesH?.strTeamBadge}/preview"
            val urlimgA = "${badgesA?.strTeamBadge}/preview"
            val dateChange = CustomesUI.changeDateFormat(itemsE?.dateEvent!!, itemsE.strTime!!)

            view.tv_league_sport.text = itemsE.strSport
            view.tv_strevent.text = itemsE.strEvent
            view.tv_date_time.text = dateChange
            view.tv_home_score.text = itemsE.intHomeScore?.toString() ?: "-"
            view.tv_away_score.text = itemsE.intAwayScore?.toString() ?: "-"
            view.tv_hometeam.text = itemsE.strHomeTeam
            view.tv_awayteam.text = itemsE.strAwayTeam
            itemsE.intHomeScore ?: itemsE.intAwayScore ?: view.tv_ft.invisible()
//            if (itemsE.intHomeScore == null && itemsE.intAwayScore == null) {
//                view.tv_ft.invisible()
//            } else {
//                view.tv_ft.visible()
//            }

            Picasso.get().load(urlimgH).into(view.imgv_hometeam, object : Callback {
                override fun onSuccess() {
                    view.prog_tim_home.invisible()
                }

                override fun onError(e: Exception?) {
                    view.prog_tim_home.invisible()
                }
            })
            Picasso.get().load(urlimgA).into(view.imgv_awayteam, object : Callback {
                override fun onSuccess() {
                    view.prog_tim_away.invisible()
                }

                override fun onError(e: Exception?) {
                    view.prog_tim_away.invisible()
                }
            })


        }

    }
}