package com.centurylink.biwf.screens.notification

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.centurylink.biwf.ViewModelBaseTest
import com.centurylink.biwf.model.notification.Notification
import com.centurylink.biwf.model.notification.NotificationSource
import com.centurylink.biwf.repos.NotificationRepository
import io.mockk.impl.annotations.MockK
import org.junit.Before
import org.junit.Rule
import org.mockito.MockitoAnnotations

class NotificationViewModelTest : ViewModelBaseTest() {

    private lateinit var viewModel: NotificationViewModel
    private val notifiCationList = mutableListOf(
        Notification(
            NotificationActivity.KEY_UNREAD_HEADER, "",
            "", "", true, ""
        ),
        Notification(
            NotificationActivity.KEY_READ_HEADER, "",
            "", "", false, ""
        ),
        Notification("1", "", "", "", true, ""),
        Notification("2", "", "", "", false, "")
    )

    @MockK
    lateinit var notificationRepository: NotificationRepository

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        var notificationSource: NotificationSource = NotificationSource()
        notificationSource.notificationlist = notifiCationList
        viewModel = NotificationViewModel(notificationRepository)
    }
}


