package com.centurylink.biwf.screens.home

import com.centurylink.biwf.Either
import com.centurylink.biwf.ViewModelBaseTest
import com.centurylink.biwf.coordinators.HomeCoordinatorDestinations
import com.centurylink.biwf.model.appointment.AppointmentRecordsInfo
import com.centurylink.biwf.model.appointment.ServiceStatus
import com.centurylink.biwf.model.user.UserDetails
import com.centurylink.biwf.model.user.UserInfo
import com.centurylink.biwf.repos.AppointmentRepository
import com.centurylink.biwf.repos.AssiaRepository
import com.centurylink.biwf.repos.UserRepository
import com.centurylink.biwf.utility.preferences.Preferences
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runBlockingTest
import org.amshove.kluent.shouldEqual
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.threeten.bp.LocalDateTime

@Suppress("EXPERIMENTAL_API_USAGE")
class HomeViewModelTest : ViewModelBaseTest() {

    private lateinit var viewModel: HomeViewModel

    @MockK
    private lateinit var appointmentRepository: AppointmentRepository
    @MockK
    private lateinit var userRepository: UserRepository
    @MockK
    private lateinit var assiaRepository: AssiaRepository
    @MockK
    private lateinit var mockPreferences: Preferences

    @Before
    fun setup() {
        every { mockPreferences.getHasSeenDialog() } returns true
        every { mockPreferences.getUserType() } returns true
        coEvery { userRepository.getUserInfo() } returns Either.Right(
            UserInfo()
        )
        coEvery { userRepository.getUserDetails() } returns Either.Right(
            UserDetails()
        )
        coEvery { appointmentRepository.getAppointmentInfo() } returns Either.Right(
            AppointmentRecordsInfo(
                serviceAppointmentStartDate = LocalDateTime.now(),
                serviceAppointmentEndTime = LocalDateTime.now(),
                serviceEngineerName = "",
                serviceEngineerProfilePic = "",
                serviceStatus = ServiceStatus.COMPLETED,
                serviceLatitude = "",
                serviceLongitude = "",
                jobType = "",
                appointmentId = "",
                timeZone = ""
            )
        )
        viewModel =
            HomeViewModel(
                mockk(),
                appointmentRepository,
                mockPreferences,
                mockk(),
                userRepository,
                assiaRepository,
                mockModemRebootMonitorService
            )
        //Need to Revisit Tests
    }
}
