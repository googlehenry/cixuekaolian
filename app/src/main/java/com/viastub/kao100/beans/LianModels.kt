package com.viastub.kao100.beans

import android.os.Parcelable
import com.viastub.kao100.db.PracticeBook
import com.viastub.kao100.db.PracticeSection
import com.viastub.kao100.module.lian.LianBookUnitSummaryActivity
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LianContext(
    var book: PracticeBook,
    var sections: MutableList<PracticeSection> = mutableListOf(),
    var startIdx: Int?,
    var sortedBy: LianBookUnitSummaryActivity.SortedBy?
) : Parcelable


data class TemplateIDStatus(
    var seq: Int,
    var templateId: Int,
    var finished: Boolean = false,
    var shortCategory: String? = null
)