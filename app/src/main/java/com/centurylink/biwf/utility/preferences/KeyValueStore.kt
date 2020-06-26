package com.centurylink.biwf.utility.preferences

interface KeyValueStore {

    fun put(key: String, value: String): Boolean

    fun get(key: String): String?

    fun remove(key: String): Boolean

    fun getBoolean(key: String): Boolean?

    fun putBoolean(key: String, value: Boolean): Boolean
}