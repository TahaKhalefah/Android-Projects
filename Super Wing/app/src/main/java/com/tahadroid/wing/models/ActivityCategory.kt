package com.tahadroid.wing.models

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
class ActivityCategory {
    var activityCategoryName: String? = null


    constructor() {

    }

    constructor(activityCategoryName: String) {

        this.activityCategoryName = activityCategoryName
    }
}