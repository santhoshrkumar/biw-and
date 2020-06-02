package com.centurylink.biwf.model.cases

import com.google.gson.annotations.SerializedName

data class Cases(
    @SerializedName("recentItems")
    val caseRecentItems: List<CaseRecentItems> = emptyList()
)

data class CaseRecentItems(
    @SerializedName("Id")
    val Id: String? = null,

    @SerializedName("CaseNumber")
    val caseNumber: String? = null
)