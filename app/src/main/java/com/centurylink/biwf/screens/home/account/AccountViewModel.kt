package com.centurylink.biwf.screens.home.account

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.centurylink.biwf.base.BaseViewModel
import com.centurylink.biwf.coordinators.AccountCoordinatorDestinations
import com.centurylink.biwf.model.account.AccountDetails
import com.centurylink.biwf.model.contact.ContactDetails
import com.centurylink.biwf.model.user.UserDetails
import com.centurylink.biwf.repos.AccountRepository
import com.centurylink.biwf.repos.AuthRepository
import com.centurylink.biwf.repos.ContactRepository
import com.centurylink.biwf.repos.UserRepository
import com.centurylink.biwf.service.auth.AuthService
import com.centurylink.biwf.service.auth.AuthServiceFactory
import com.centurylink.biwf.service.auth.AuthServiceHost
import com.centurylink.biwf.utility.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

class AccountViewModel internal constructor(
    private val accountRepository: AccountRepository,
    private val contactRepository: ContactRepository,
    private val userRepository: UserRepository,
    private val authService: AuthService<*>
) : BaseViewModel() {

    class Factory @Inject constructor(
        private val accountRepository: AccountRepository,
        private val contactRepository: ContactRepository,
        private val userRepository: UserRepository,
        private val authServiceFactory: AuthServiceFactory<*>
    ) : ViewModelFactoryWithInput<AuthServiceHost> {

        override fun withInput(input: AuthServiceHost): ViewModelProvider.Factory {
            return viewModelFactory {
                AccountViewModel(
                    accountRepository,
                    contactRepository,
                    userRepository,
                    authServiceFactory.create(input)
                )
            }
        }
    }

    val accountDetailsInfo: Flow<UiAccountDetails> = BehaviorStateFlow()
    var uiAccountDetails: UiAccountDetails = UiAccountDetails()
    var errorMessageFlow = EventFlow<String>()

    init {
        initApiCalls()
    }

    val myState = EventFlow<AccountCoordinatorDestinations>()

    val navigateToSubscriptionActivityEvent: EventLiveData<Unit> = MutableLiveData()

    fun onBiometricChange(boolean: Boolean) {
        uiAccountDetails = uiAccountDetails.copy(biometricStatus = boolean)
    }

    private fun initApiCalls() {
        viewModelScope.launch {
            requestUserInfo()
            requestUserDetails()
            requestAccountDetails()
            requestContactInfo()
        }
    }

    fun onServiceCallsAndTextsChange(servicecall: Boolean) {
        viewModelScope.launch {
            uiAccountDetails = uiAccountDetails.copy(serviceCallsAndText = servicecall)
            val result = accountRepository.setServiceCallsAndTexts(servicecall)
            errorMessageFlow.latestValue = result
        }
    }

    fun onMarketingEmailsChange(boolean: Boolean) {
        viewModelScope.launch {
            uiAccountDetails = uiAccountDetails.copy(marketingEmails = boolean)
            val result = contactRepository.setMarketingEmails(boolean)
            errorMessageFlow.latestValue = result
        }
    }

    fun onMarketingCallsAndTextsChange(boolean: Boolean) {
        viewModelScope.launch {
            uiAccountDetails = uiAccountDetails.copy(marketingCallsAndText = boolean)
            val result = contactRepository.setMarketingCallsAndText(boolean)
            errorMessageFlow.latestValue = result
        }
    }

    fun onSubscriptionCardClick() {
        navigateToSubscriptionActivityEvent.emit(Unit)
    }

    fun onPersonalInfoCardClick() {
        myState.latestValue = AccountCoordinatorDestinations.PROFILE_INFO
    }

    fun onLogOutClick() {
        viewModelScope.launch {
            val result = authService.revokeToken()
            if (result) {
                authService.launchSignInFlow()
            }
        }
    }

    private suspend fun requestUserInfo() {
        val userInfo = userRepository.getUserInfo()
        userInfo.fold(ifLeft = {
            errorMessageFlow.latestValue = it
        }) {}
    }

    private suspend fun requestUserDetails() {
        val userDetails = userRepository.getUserDetails()
        userDetails.fold(ifLeft = {
            errorMessageFlow.latestValue = it
        }) {
            updateUIAccountDetailsFromUserDetails(it)
        }
    }

    private suspend fun requestAccountDetails() {
        val accountDetails = accountRepository.getAccountDetails()
        accountDetails.fold(ifLeft = {
            errorMessageFlow.latestValue = it
        }) {
            updateUIAccountDetailsFromAccounts(it)
        }
    }

    private suspend fun requestContactInfo() {
        val contactDetails = contactRepository.getContactDetails()
        contactDetails.fold(ifLeft = {
            errorMessageFlow.latestValue = it
        }) {
            updateUIAccountDetailsFromContacts(it)
        }
    }

    private fun updateUIAccountDetailsFromAccounts(accontDetails: AccountDetails) {
        uiAccountDetails = uiAccountDetails.copy(
            name = accontDetails.name,
            serviceAddress1 = accontDetails.billingAddress?.street ?: "",
            serviceAddress2 = formatServiceAddress2(accontDetails) ?: "",
            planName = accontDetails.productNameC ?: "Best in World Fiber",
            planSpeed = accontDetails.productPlanNameC ?: "",
            paymentDate = DateUtils.formatInvoiceDate(accontDetails.lastViewedDate!!),
            password = "******", cellPhone = accontDetails.phone, homePhone = accontDetails.phone,
            workPhone = accontDetails.phone, serviceCallsAndText = accontDetails.cellPhoneOptInC
        )
    }


    private fun formatServiceAddress2(accountDetails: AccountDetails): String? {
        return accountDetails.billingAddress?.run {
            val billingAddressList = listOf(city, state, postalCode, country)
            billingAddressList.filterNotNull().joinToString(separator = ", ")
        }
    }

    private fun updateUIAccountDetailsFromContacts(contactDetails: ContactDetails) {
        uiAccountDetails = uiAccountDetails.copy(
            marketingEmails = contactDetails.emailOptInC,
            marketingCallsAndText = contactDetails.marketingOptInC
        )
        updateAccountFlow()
    }

    private fun updateAccountFlow() {
        accountDetailsInfo.latestValue = uiAccountDetails
    }

    private fun updateUIAccountDetailsFromUserDetails(userDetails: UserDetails) {
        uiAccountDetails = uiAccountDetails.copy(email = userDetails.email)
    }

    data class UiAccountDetails(
        val name: String? = null,
        val serviceAddress1: String? = null,
        val serviceAddress2: String? = null,
        val planName: String? = null,
        val planSpeed: String? = null,
        val paymentDate: String? = null,
        val paymentMethod: String? = null,
        val email: String? = null,
        val password: String? = null,
        val cellPhone: String? = null,
        val homePhone: String? = null,
        val workPhone: String? = null,
        val biometricStatus: Boolean = false,
        val serviceCallsAndText: Boolean = false,
        val marketingEmails: Boolean = true,
        val marketingCallsAndText: Boolean = false
    )
}
