package com.tahadroid.wing.models

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
class Section {
    var sectionName: String? = null
    var sectionIcon: String? = null

    constructor(){

    }
    constructor(sectionName:String,sectionIcon: String)
    {
        this.sectionName=sectionName
        this.sectionIcon=sectionIcon
    }
}