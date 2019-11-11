package com.stimednp.kadesubmission2.ui.anko

import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.appbar.AppBarLayout
import com.stimednp.kadesubmission2.R
import com.stimednp.kadesubmission2.model.Leagues
import org.jetbrains.anko.*
import org.jetbrains.anko.design.coordinatorLayout
import org.jetbrains.anko.design.themedAppBarLayout
import org.jetbrains.anko.recyclerview.v7.recyclerView

/**
 * Created by rivaldy on 11/10/2019.
 */

class MainUI(val items: ArrayList<Leagues>) : AnkoComponent<MainActivity> {
    override fun createView(ui: AnkoContext<MainActivity>) = with(ui) {
        coordinatorLayout {
            fitsSystemWindows = true
            themedAppBarLayout(R.style.AppTheme_AppBarOverlay) {
                toolbar {
                    title = resources.getString(R.string.app_name)
                }.lparams(matchParent, wrapContent) {
                    scrollFlags = AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL or AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS
                }
            }.lparams(matchParent, wrapContent)

            frameLayout {
                recyclerView {
                    lparams(matchParent, matchParent)
                    layoutManager = LinearLayoutManager(context)
//                    adapter =
//                        LeagueAdapter(items) {
//                            startActivity<DetailActivity>(DetailActivity.EXTRA_DATA to it)
//                        }
                }
            }.lparams(matchParent, matchParent) {
                behavior = AppBarLayout.ScrollingViewBehavior()
            }
        }
    }
}