package com.centurylink.biwf.screens.home

import androidx.core.os.bundleOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.centurylink.biwf.R
import com.centurylink.biwf.base.BaseViewModel
import com.centurylink.biwf.coordinators.HomeCoordinatorDestinations
import com.centurylink.biwf.model.TabsBaseItem
import com.centurylink.biwf.model.user.UserDetails
import com.centurylink.biwf.model.user.UserInfo
import com.centurylink.biwf.repos.AccountRepository
import com.centurylink.biwf.repos.ContactRepository
import com.centurylink.biwf.repos.UserRepository
import com.centurylink.biwf.service.network.TestRestServices
import com.centurylink.biwf.service.network.UserService
import com.centurylink.biwf.utility.MutableStateFlow
import com.centurylink.biwf.utility.ObservableData
import com.centurylink.biwf.widgets.OnlineStatusData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val testRestServices: TestRestServices,
    private val userService: UserService,
    private val userRepository: UserRepository,
    private val contactRepository: ContactRepository,
    private val accountRepository: AccountRepository

) : BaseViewModel() {

    val userRestFlow: Flow<UserInfo> = MutableStateFlow()
    val userRestErrorFlow: Flow<Throwable> = MutableStateFlow()

    val userDetailsFlow: Flow<UserDetails> = MutableStateFlow()
    val userDetailsErrorFlow: Flow<Throwable> = MutableStateFlow()

    val activeUserTabBarVisibility: LiveData<Boolean> = MutableLiveData(false)
    val networkStatus: LiveData<OnlineStatusData> = MutableLiveData(OnlineStatusData())
    val myState = ObservableData(HomeCoordinatorDestinations.HOME)
    var upperTabHeaderList = mutableListOf<TabsBaseItem>()
    var lowerTabHeaderList = mutableListOf<TabsBaseItem>()

    // Example: Expose data through Flow properties.
    // TODO Remove later when example is no longer needed.
    val testRestFlow: Flow<String> = MutableStateFlow()
    val testRestErrorFlow: Flow<Throwable> = MutableStateFlow()

    // dummy variable that helps toggle between online states. Will remove when implementing real online status
    var dummyOnline = false

    init {
        upperTabHeaderList = initList(true)
        lowerTabHeaderList = initList(false)

        // TODO Remove later when example is no longer needed.
        requestTestRestFlow()
        requestUserInfo()
        getUserDetails()

    }

    fun handleTabBarVisibility(isExistingUser: Boolean) {
        //just a dummy function to test showing different toolbars
        activeUserTabBarVisibility.latestValue = isExistingUser
    }

    fun onSupportClicked() {
        myState.value = HomeCoordinatorDestinations.SUPPORT
    }

    fun onNotificationBellClicked() {
        myState.value = HomeCoordinatorDestinations.NOTIFICATION_LIST
    }

	fun onNotificationClicked(){
        myState.value = HomeCoordinatorDestinations.NOTIFICATION_DETAILS
    }
    fun loadData() {
        loadAccountsData()
        loadDevicesData()
        loadDashboardData()
    }

    fun onOnlineToolbarClick() {
        // dummy function to show the different states of the user Online / Offline
        if (dummyOnline) {
            networkStatus.latestValue = OnlineStatusData()
        } else {
            val onlineStatusData = OnlineStatusData(isOnline = true, networkName = "Fake Network")
            networkStatus.latestValue = onlineStatusData
        }
        dummyOnline = !dummyOnline
    }

    fun onProfileClickEvent() {
        myState.value = HomeCoordinatorDestinations.PROFILE
    }

    // Example: Use Coroutines to get data asynchronously and emit the results through Flows
    // TODO Remove later when example is no longer needed.
    private fun requestTestRestFlow() {
        viewModelScope.launch {
            try {
                testRestFlow.latestValue =
                    testRestServices.query("SELECT Name FROM Contact LIMIT 10").toString()

            } catch (e: Throwable) {
                testRestFlow.latestValue = ""
                testRestErrorFlow.latestValue = e
            }
        }
    }

    private fun initList(isUpperTab: Boolean): MutableList<TabsBaseItem> {
        val list = mutableListOf<TabsBaseItem>()

        list.add(TabsBaseItem(indextype = TabsBaseItem.ACCOUNT, titleRes = R.string.tittle_text_account))
        list.add(TabsBaseItem(indextype = TabsBaseItem.DASHBOARD, titleRes = R.string.tittle_text_dashboard, bundle = bundleOf("NEW_USER" to isUpperTab)))
        if (!isUpperTab)
        list.add(TabsBaseItem(indextype = TabsBaseItem.DEVICES, titleRes = R.string.tittle_text_devices))

        return list
    }

    private fun loadDashboardData() {
        //Load data here
    }

    private fun loadDevicesData() {
        //Load data here
    }

    private fun loadAccountsData() {
        //Load data here
    }

    private fun requestUserInfo() {
        viewModelScope.launch {
            try {
                userRestFlow.latestValue =
                    userService.qetUserInfo()
                val userId: String = userRestFlow.latestValue.recentItems[0].Id!!
                userRepository.storeUserId(userId)


            } catch (e: Throwable) {
                userRestErrorFlow.latestValue = e
            }
        }
    }

    private fun getUserDetails() {
        val userId = userRepository.getUserId()
        viewModelScope.launch {
            try {
                userDetailsFlow.latestValue =
                    userService.getCompleteUserDetails(userId!!)
                accountRepository.storeAccountId(userDetailsFlow.latestValue.accountId!!)
                contactRepository.storeContactId(userDetailsFlow.latestValue.contactId!!)
            } catch (e: Throwable) {
                userDetailsErrorFlow.latestValue = e
            }
        }
    }
}

