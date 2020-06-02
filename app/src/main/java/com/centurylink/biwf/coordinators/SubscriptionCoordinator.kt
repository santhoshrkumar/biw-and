package com.centurylink.biwf.coordinators

import android.os.Bundle
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SubscriptionCoordinator @Inject constructor(
    val navigator: Navigator
) : Coordinator<SubscriptionCoordinatorDestinations> {

    override fun navigateTo(destination: SubscriptionCoordinatorDestinations) {
        when (destination) {
            SubscriptionCoordinatorDestinations.STATEMENT -> navigateToInvoiceDetails()
            SubscriptionCoordinatorDestinations.MANAGE_MY_SUBSCRIPTION -> navigateToManageSubscription()
        }
    }

    private fun navigateToInvoiceDetails() {
        navigator.navigateToBillStatement()
    }

    private fun navigateToManageSubscription() {
        navigator.navigateToMangeSubscription()
    }
}

enum class SubscriptionCoordinatorDestinations {
    STATEMENT, MANAGE_MY_SUBSCRIPTION;

    companion object {
        lateinit var bundle: Bundle
    }
}