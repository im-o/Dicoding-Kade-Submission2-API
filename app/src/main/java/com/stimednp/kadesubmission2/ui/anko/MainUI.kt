package com.stimednp.kadesubmission2.ui.anko

import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.Toolbar
import androidx.core.content.ContextCompat.getColor
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.appbar.AppBarLayout
import com.stimednp.kadesubmission2.R
import com.stimednp.kadesubmission2.R.color.*
import com.stimednp.kadesubmission2.model.Leagues
import com.stimednp.kadesubmission2.ui.adapter.HomeAdapter
import com.stimednp.kadesubmission2.ui.xml.DetailsActivity
import org.jetbrains.anko.*
import org.jetbrains.anko.design.coordinatorLayout
import org.jetbrains.anko.design.themedAppBarLayout
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.swipeRefreshLayout

/**
 * Created by rivaldy on 11/10/2019.
 */

class MainUI(val items: ArrayList<Leagues>) : AnkoComponent<MainActivity> {
    companion object {
        lateinit var rv_main: RecyclerView
        lateinit var progress: ProgressBar
        lateinit var swipeRefresh: SwipeRefreshLayout
        lateinit var toolbar_main: Toolbar
    }

    override fun createView(ui: AnkoContext<MainActivity>) = with(ui) {
        coordinatorLayout {
            lparams(matchParent, matchParent)
            fitsSystemWindows = true
            themedAppBarLayout(R.style.AppTheme_AppBarOverlay) {
                toolbar_main = toolbar {
                    title = resources.getString(R.string.app_title)
                    backgroundColor = getColor(context, colorPrimaryToolbar)
                }.lparams(matchParent, wrapContent) {
                    scrollFlags =
                        AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL or AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS
                }
            }.lparams(matchParent, wrapContent)
            verticalLayout {
                orientation = LinearLayout.VERTICAL
                swipeRefresh = swipeRefreshLayout {
                    id = R.id.swipe_main
                    isRefreshing = true
                    setColorSchemeResources(
                        colorAccent,
                        colorTextGrey,
                        colorPrimary,
                        colorTransparantBlack
                    )
                    relativeLayout {
                        rv_main = recyclerView {
                            id = R.id.rv_main
                            lparams(matchParent, matchParent)
                            layoutManager = LinearLayoutManager(context)
                            adapter = HomeAdapter(items) {
                                toast("Hasil : ${it.strLeague}")
                                startActivity<DetailsActivity>()
                            }
                        }
                        progress = progressBar {
                            id = R.id.progress_main
                        }.lparams {
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