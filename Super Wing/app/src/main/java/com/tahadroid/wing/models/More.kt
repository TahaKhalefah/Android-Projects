package com.tahadroid.wing.models

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
class More{
    var descriptionShop: String?=null
    var foodName: String?=null
    var imagePath: String?=null
    constructor(){

    }
    constructor(descriptionShop:String,foodName: String,imagePath: String)
    {
        this.descriptionShop=descriptionShop
        this.foodName=foodName
        this.imagePath=imagePath
    }
}