package com.khansafzh.authapp.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Recipes (
    var recipeId : String = "0",
    var recipeName : String? = null,
    var recipeJenis : String? = null
) : Parcelable