package com.centurylink.biwf.model.user

import com.google.gson.annotations.SerializedName

data class UserInfo(
    @SerializedName("recentItems")
    val recentItems: List<RecentItems> = emptyList()
)

data class RecentItems(
    @SerializedName("Id")
    val Id: String? = null,

    @SerializedName("Name")
    val name: String? = null
)