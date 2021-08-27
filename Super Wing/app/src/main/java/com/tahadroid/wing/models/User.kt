package com.tahadroid.wing.models

class User {
    var phone: String? = null
    var username:String? = null
    var password: String? = null
    var profileImage: String? = null
    constructor() {

    }

    constructor(phone: String,username:String,password: String,profileImage: String) {
        this.phone = phone
        this.username=username
        this.password=password
        this.profileImage=profileImage
    }
}