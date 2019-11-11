package com.stimednp.kadesubmission2.model

/**
 * Created by rivaldy on 11/10/2019.
 */

class ResponseTsdb {
    var leagues = ArrayList<Leagues>()
}

class Leagues {
    lateinit var idLeague: String
    lateinit var strSport: String
    lateinit var strLeague: String
    var intFormedYear: Int? = null
    lateinit var dateFirstEvent: String
    lateinit var strWebsite: String
    lateinit var strFacebook: String
    lateinit var strTwitter: String
    lateinit var strYoutube: String
    lateinit var strDescriptionEN: String
    var strBadge: String? = null
    lateinit var strLogo: String
    lateinit var strComplete: String
}
