package com.centurylink.biwf.model.cases

import com.google.gson.annotations.SerializedName

/**
 * Model class for case subscription create details
 */
data class Cases(
    @SerializedName("recentItems")
    val caseRecentItems: List<CaseRecentItems> = emptyList()
)

data class CaseRecentItems(
    @SerializedName("Id")
    val id: String? = null,

    @SerializedName("CaseNumber")
    val caseNumber: String? = null
)
