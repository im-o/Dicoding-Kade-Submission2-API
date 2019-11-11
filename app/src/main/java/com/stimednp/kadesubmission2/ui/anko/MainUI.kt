package com.stimednp.kadesubmission2.ui.anko

import android.widget.LinearLayout
import androidx.core.content.ContextCompat.getColor
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.appbar.AppBarLayout
import com.stimednp.kadesubmission2.R
import com.stimednp.kadesubmission2.R.color.*
import com.stimednp.kadesubmission2.model.Leagues
import com.stimednp.kadesubmission2.ui.adapter.HomeAdapter
import org.jetbrains.anko.*
import org.jetbrains.anko.design.coordinatorLayout
import org.jetbrains.anko.design.themedAppBarLayout
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.swipeRefreshLayout

/**
 * Created by rivaldy on 11/10/2019.
 */

class MainUI(val items: ArrayList<Leagues>) : AnkoComponent<MainActivity> {
    override fun createView(ui: AnkoContext<MainActivity>) = with(ui) {
        coordinatorLayout {
            //            backgroundColor = getColor(context, colorPrimaryDark)
            lparams(matchParent, matchParent)
            fitsSystemWindows = true
            themedAppBarLayout(R.style.AppTheme_AppBarOverlay) {
                toolbar {
                    title = resources.getString(R.string.app_title)
                    backgroundColor = getColor(context, colorPrimaryToolbar)
                }.lparams(matchParent, wrapContent) {
                    scrollFlags =
                        AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL or AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS
                }
            }.lparams(matchParent, wrapContent)
            verticalLayout {
                orientation = LinearLayout.VERTICAL
                swipeRefreshLayout {
                    id = R.id.swipe_main
                    setColorSchemeResources(
                        colorAccent,
                        colorTextGrey,
                        colorPrimary,
                        colorTransparantBlack
                    )
                    relativeLayout {
                        recyclerView {
                            id = R.id.rv_main
                            lparams(matchParent, matchParent)
                            layoutManager = LinearLayoutManager(context)
                            adapter = HomeAdapter(items)
//                        LeagueAdapter(items) {
//                            startActivity<DetailActivity>(DetailActivity.EXTRA_DATA to it)
//                        }
                        }
                        progressBar {
                            id = R.id.progress_main
                        }.lparams{
                            centerInParent()
                        }
                    }
                }.lparams(matchParent, matchParent)
            }.lparams(matchParent, matchParent) {
                behavior = AppBarLayout.ScrollingViewBehavior()
            }
        }
    }
}