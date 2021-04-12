package com.viastub.kao100.beans

import android.os.Parcelable
import com.viastub.kao100.db.TeachingBookUnitSection
import kotlinx.android.parcel.Parcelize

@Parcelize
data class XueContext(
    var unit: TeachingBookUnitSection,
    var currentPageIndex: Int = -1
) : Parcelable