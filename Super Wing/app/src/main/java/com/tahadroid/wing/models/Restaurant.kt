package com.tahadroid.wing.models

import android.os.Parcelable
import com.google.firebase.database.IgnoreExtraProperties
import kotlinx.android.parcel.Parcelize

@IgnoreExtraProperties
@Parcelize
class Restaurant(
    var shopId: String? = null,
    var icon: String? = null,
    var type: String? = null,
    var description: String? = null,
    var image: String? = null,
    var lat: String? = null,
    var lon: String? = null,
    var rate: Float? = null,
    var shopName: String? = null,
    var shopNameBranch: String? = null,
    var placeNumber: String? = null
): Parcelable