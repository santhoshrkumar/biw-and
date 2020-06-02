package com.centurylink.biwf.screens.cancelsubscription

import androidx.lifecycle.viewModelScope
import com.centurylink.biwf.base.BaseViewModel
import com.centurylink.biwf.coordinators.CancelSubscriptionCoordinatorDestinations
import com.centurylink.biwf.repos.ZouraSubscriptionRepository
import com.centurylink.biwf.utility.BehaviorStateFlow
import com.centurylink.biwf.utility.DateUtils
import com.centurylink.biwf.utility.EventFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

class CancelSubscriptionViewModel @Inject constructor(
    private val zuoraSubscriptionRepository: ZouraSubscriptionRepository
) : BaseViewModel() {

    val cancelSubscriptionDate: Flow<UiCancelSubscriptionDetails> = BehaviorStateFlow()
    var errorMessageFlow = EventFlow<String>()
    val myState = EventFlow<CancelSubscriptionCoordinatorDestinations>()

    init {
        initApis()
    }

    private fun initApis() {
        viewModelScope.launch {
            requestSubscriptionDate()
        }
    }

    private suspend fun requestSubscriptionDate() {
        val subscriptionDate = zuoraSubscriptionRepository.getSubscriptionDate()
        subscriptionDate.fold(ifLeft = {
            errorMessageFlow.latestValue = it
        }) {
            cancelSubscriptionDate.latestValue = UiCancelSubscriptionDetails(
                subscriptionEndDate = DateUtils.toSimpleString(it, DateUtils.STANDARD_FORMAT)
            )
        }
    }

    fun onNavigateToCancelSubscriptionDetails() {
        myState.latestValue =
            CancelSubscriptionCoordinatorDestinations.CANCEL_SELECT_DATE_SUBSCRIPTION
    }

    data class UiCancelSubscriptionDetails(
        val subscriptionEndDate: String? = null
    )
}