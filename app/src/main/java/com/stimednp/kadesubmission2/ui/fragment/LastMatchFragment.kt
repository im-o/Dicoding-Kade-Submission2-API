package com.stimednp.kadesubmission2.ui.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.stimednp.kadesubmission2.R
import com.stimednp.kadesubmission2.model.Leagues
import com.stimednp.kadesubmission2.ui.xml.DetailsActivity
import org.jetbrains.anko.support.v4.toast
import org.jetbrains.anko.toast

/**
 * A simple [Fragment] subclass.
 */
class LastMatchFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_last_match, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val dataItems = DetailsActivity.items
        val idEvent = dataItems?.idLeague?.toInt()
        setIdEvent(idEvent!!)
    }

    private fun setIdEvent(idEvent: Int) {
        toast("ID FRAGMENT ---> ${idEvent}")
    }

}
