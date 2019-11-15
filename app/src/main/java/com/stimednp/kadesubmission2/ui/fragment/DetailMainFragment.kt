package com.stimednp.kadesubmission2.ui.fragment

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.stimednp.kadesubmission2.R
import com.stimednp.kadesubmission2.ui.adapter.ViewPagerAdapter
import kotlinx.android.synthetic.main.fragment_detail_main.*
import org.jetbrains.anko.support.v4.find

/**
 * A simple [Fragment] subclass.
 */
class DetailMainFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_detail_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        setupViewPager()
        setToolbar()
    }

    private fun setToolbar() {
        
    }

    private fun setupViewPager() {
        htab_viewpager.adapter = context?.let { ViewPagerAdapter(childFragmentManager, it) }
        htab_tablayout.setupWithViewPager(htab_viewpager)

        htab_tablayout.getTabAt(0)?.setIcon(R.drawable.ic_event_note_black_24dp)
        htab_tablayout.getTabAt(1)?.setIcon(R.drawable.ic_event_note_black_24dp)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.option_menu, menu)
        val searchItem = menu.findItem(R.id.item_search)
        val searchView = searchItem.actionView as SearchView
        searchView.queryHint = "Search"
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })
        super.onCreateOptionsMenu(menu, inflater)
    }
}
