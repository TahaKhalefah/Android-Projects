package com.tahadroid.wing.models

import android.os.Parcelable
import com.google.firebase.database.IgnoreExtraProperties
import kotlinx.android.parcel.Parcelize

@IgnoreExtraProperties
@Parcelize
class Menu(
    var shopName: String? = null,
    var shopId: String? = null,
    var pieceId: String? = null,
    var discount: Int? = null,
    var description: String? = null,
    var image: String? = null,
    var inStock: Boolean? = null,
    var name: String? = null,
    var price: Float = 0.0F,
    var rate: Float? = null,
    var placeKey: String? = null,
    var section: String? = null
): Parcelable