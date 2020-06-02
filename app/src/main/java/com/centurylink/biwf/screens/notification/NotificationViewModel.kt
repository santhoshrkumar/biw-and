package com.centurylink.biwf.screens.notification

import android.os.Bundle
import androidx.lifecycle.viewModelScope
import com.centurylink.biwf.base.BaseViewModel
import com.centurylink.biwf.coordinators.NotificationCoordinatorDestinations
import com.centurylink.biwf.model.notification.Notification
import com.centurylink.biwf.model.notification.NotificationSource
import com.centurylink.biwf.repos.NotificationRepository
import com.centurylink.biwf.utility.BehaviorStateFlow
import com.centurylink.biwf.utility.EventFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject


class NotificationViewModel @Inject constructor(
    private val notificationRepository: NotificationRepository
) : BaseViewModel() {
    var errorMessageFlow = EventFlow<String>()
    val errorEvents: EventFlow<String> = EventFlow()
    val displayClearAllEvent: EventFlow<Unit> = EventFlow()
    val myState = EventFlow<NotificationCoordinatorDestinations>()
    val notifications: Flow<MutableList<Notification>> = BehaviorStateFlow()
    val notificationListDetails :Flow<NotificationSource> = BehaviorStateFlow<NotificationSource>()
    private val unreadItem: Notification =
        Notification(
            NotificationActivity.KEY_UNREAD_HEADER, "",
            "", "", true, ""
        )
    private val readItem: Notification =
        Notification(
            NotificationActivity.KEY_READ_HEADER, "",
            "", "", false, ""
        )

    private var mergedNotificationList: MutableList<Notification> = mutableListOf()

    init {
        initApi()
    }

    private fun initApi() {
        viewModelScope.launch {
            requestNotificationDetails()
        }
    }

    private suspend fun requestNotificationDetails() {
        val notificationDetails = notificationRepository.getNotificationDetails()
        notificationDetails.fold(ifLeft = {
            errorMessageFlow.latestValue = it
        }) {
            notificationListDetails.latestValue = it
        }
    }

    fun notificationItemClicked(notificationItem: Notification) {
        if (notificationItem.isUnRead) {
            mergedNotificationList.remove(notificationItem)
            notificationItem.isUnRead = false
            mergedNotificationList.add(mergedNotificationList.size, notificationItem)
            val readNotificationList =
                mergedNotificationList.asSequence().filter { !it.isUnRead }.toMutableList()
            if (readNotificationList.size == 1) {
                mergedNotificationList.add(mergedNotificationList.size - 1, readItem)
            }
            val unreadNotificationList = mergedNotificationList.asSequence()
                .filter { it.isUnRead }
                .toMutableList()
            if (unreadNotificationList.size == 1) {
                mergedNotificationList.remove(unreadItem)
            }

            notifications.latestValue = mergedNotificationList
        }
        navigatetoNotifcationDetails(notificationItem)
    }

    private fun navigatetoNotifcationDetails(notificationItem: Notification) {
        val bundle = Bundle()
        bundle.putString(NotificationDetailsActivity.URL_TO_LAUNCH, notificationItem.detialUrl)
        bundle.putBoolean(NotificationDetailsActivity.LAUNCH_FROM_HOME, true)
        NotificationCoordinatorDestinations.bundle = bundle
        myState.latestValue = NotificationCoordinatorDestinations.NOTIFICATION_DETAILS
    }

    fun markNotificationasRead() {
        mergedNotificationList.forEach { it.isUnRead = false }
        mergedNotificationList.remove(unreadItem)
        mergedNotificationList.remove(readItem)
        mergedNotificationList.add(0, readItem)
        notifications.latestValue = mergedNotificationList
    }

    fun displaySortedNotifications(notificationList: List<Notification>) {
        val unreadNotificationList = notificationList.asSequence()
            .filter { it.isUnRead }
            .toMutableList()
        if (unreadNotificationList.size > 0) {
            unreadNotificationList.add(0, unreadItem)
        }
        val readNotificationList = notificationList.asSequence()
            .filter { !it.isUnRead }
            .toMutableList()
        if (readNotificationList.size > 0) {
            readNotificationList.add(0, readItem)
        }
        mergedNotificationList.addAll(unreadNotificationList)
        mergedNotificationList.addAll(unreadNotificationList.size, readNotificationList)
        notifications.latestValue = mergedNotificationList
    }

    fun clearAllReadNotifications() {
        mergedNotificationList = mergedNotificationList.filter { it.isUnRead }.toMutableList()
        mergedNotificationList.remove(readItem)
        notifications.latestValue = mergedNotificationList
    }

    fun displayClearAllDialogs() {
        viewModelScope.launch {
            displayClearAllEvent.postValue(Unit)
        }
    }

    fun displayErrorDialog() {
        viewModelScope.launch {
            errorEvents.postValue("Server error!Try again later")
        }
    }
}