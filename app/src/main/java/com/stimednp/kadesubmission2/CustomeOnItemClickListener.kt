package com.stimednp.kadesubmission2

import android.view.View

/**
 * Created by rivaldy on 11/18/2019.
 */

class CustomeOnItemClickListener(private var position: Int, private var onItemClickCallback: OnItemClickCallback) : View.OnClickListener {
    override fun onClick(view: View?) {
        onItemClickCallback.onItemClicked(view!!, position)
    }

    interface OnItemClickCallback {
        fun onItemClicked(view: View, position: Int)
    }
}
