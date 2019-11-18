package com.stimednp.kadesubmission2

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.net.ConnectivityManagerCompat

//import android.content.Context
//import android.net.ConnectivityManager
//import android.net.NetworkInfo
//import android.util.Log

/**
 * Created by rivaldy on 11/15/2019.
 */
//
object CheckNetwork {
//    private val TAG = CheckNetwork::class.java.simpleName
//    var statusInternet: Int = 0
//
//    fun isInternetAvailable(context: Context): Boolean {
//        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
//        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)!!.state == NetworkInfo.State.CONNECTED || connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)!!.state == NetworkInfo.State.CONNECTED) {
//            Log.w(TAG, "Internet Connection Available")
//            statusInternet = 1//CONNECTED
//            return true
//        } else {
//            if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)!!.state == NetworkInfo.State.DISCONNECTED && connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)!!.state == NetworkInfo.State.DISCONNECTED) {
//                Log.w(TAG, "No internet Connection")
//                statusInternet = 0//DISCONNECTED
//                return true
//            } else {
//                Log.w(TAG, "Internet Connection")
//                statusInternet = 2//RECONNECTION
//                return true
//            }
//        }
//    }

    fun isOnline(): Boolean {
        val connectivtym: ConnectivityManagerCompat
        return true
    }
}


class ConnectivityReceiver : BroadcastReceiver(){
    override fun onReceive(p0: Context?, p1: Intent?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}