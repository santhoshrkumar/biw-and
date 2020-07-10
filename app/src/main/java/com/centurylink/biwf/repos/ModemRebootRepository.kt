package com.centurylink.biwf.repos

import com.centurylink.biwf.model.assia.ModemRebootResponse
import com.centurylink.biwf.repos.assia.AssiaTokenManager
import com.centurylink.biwf.service.network.AssiaService
import com.centurylink.biwf.utility.preferences.Preferences
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ModemRebootRepository @Inject constructor(
    private val preferences: Preferences,
    private val assiaService: AssiaService,
    private val assiaTokenManager: AssiaTokenManager
) {

    suspend fun rebootModem(): ModemRebootResponse {
        return assiaService.rebootModem(
            preferences.getAssiaId(),
            getHeaderMap(token = assiaTokenManager.getAssiaToken())
        )
    }

    private fun getHeaderMap(token: String): Map<String, String> {
        val headerMap = mutableMapOf<String, String>()
        headerMap["Authorization"] = "bearer $token"
        return headerMap
    }

    companion object {
        const val REBOOT_STARTED_SUCCESSFULLY = 1000

        // TODO Move to Reboot service once created
        enum class RebootState {
            READY, ONGOING, SUCCESS, ERROR
        }
    }
}
