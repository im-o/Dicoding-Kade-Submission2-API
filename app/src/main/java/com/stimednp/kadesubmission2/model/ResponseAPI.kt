package com.stimednp.kadesubmission2.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by rivaldy on 11/10/2019.
 */

class ResponseLeagues {
    var leagues = ArrayList<Leagues>()
}

class ResponseEvents {
    var leagues = ArrayList<Leagues>()
}

class ResponseDEvent {
    var leagues = ArrayList<Leagues>()
}

@Parcelize
data class Leagues(
    var idLeague: String?,
    var strSport: String?,
    var strLeague: String?,
    var intFormedYear: Int?,
    var dateFirstEvent: String?,
    var strWebsite: String?,
    var strFacebook: String?,
    var strTwitter: String?,
    var strYoutube: String?,
    var strDescriptionEN: String?,
    var strBadge: String?,
    var strLogo: String?,
    var strComplete: String?
) : Parcelable

class Events {
    var idEvent: String = ""
    var idSoccerXML: String = ""
    var strEvent: String = ""
    var strSport: String = ""
    var idLeague: String = ""
    var strLeague: String = ""
}
