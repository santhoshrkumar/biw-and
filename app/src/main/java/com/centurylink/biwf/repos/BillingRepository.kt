package com.centurylink.biwf.repos

import com.centurylink.biwf.model.billing.BillingDetails
import com.centurylink.biwf.service.network.BillingApiServices
import com.centurylink.biwf.utility.preferences.Preferences
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BillingRepository @Inject constructor(
    private val preferences: Preferences,
    private val billingApiDetails: BillingApiServices
) {

    private fun getPlanName(): String? {
        return preferences.getValueByID(Preferences.PLAN_NAME)
    }

    suspend fun getBillingDetails(): List<BillingDetails> {
        return billingApiDetails.getBillingDetails()
    }
}