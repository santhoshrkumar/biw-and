package com.centurylink.biwf.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.centurylink.biwf.analytics.AnalyticsKeys
import com.centurylink.biwf.analytics.AnalyticsManager
import com.centurylink.biwf.service.impl.workmanager.ModemRebootMonitorService
import com.centurylink.biwf.utility.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import org.threeten.bp.Instant
import org.threeten.bp.ZoneId
import org.threeten.bp.format.DateTimeFormatterBuilder
import org.threeten.bp.format.SignStyle
import org.threeten.bp.temporal.ChronoField

abstract class BaseViewModel(
    private val modemRebootMonitorService: ModemRebootMonitorService,
    val analyticsManagerInterface: AnalyticsManager
) : ViewModel() {

    /**
     * A emission of true represents "success" and a false represents "error"
     */
    val rebootDialogFlow = EventFlow<Boolean>()

    /**
     * A emission of true represents "success" and a false represents "error"
     */
    val rebootDialogStatusFlow = EventFlow<Boolean>()

    /**
     * A emission of reboot ongoing flow
     */
    val rebootOngoingFlow = EventFlow<Boolean>()

    /**
     * A emission of reboot ongoing flow
     */
    val rebootCanceledFlow = EventFlow<Boolean>()

    /**
     * Emits more detailed modem reboot status updates. Can be used for informing UI elements
     * which change depending on the state (e.g. "Restart modem" button -> "Restarting" button)
     */
    val detailedRebootStatusFlow = EventFlow<ModemRebootMonitorService.RebootState>()

    init {
        listenToRebootStatus()
    }

    private fun listenToRebootStatus() {
        viewModelScope.launch {
            modemRebootMonitorService.modemRebootStateFlow.collect {
                handleRebootStatus(it)
            }
        }
    }

    internal open suspend fun handleRebootStatus(status: ModemRebootMonitorService.RebootState) {
        detailedRebootStatusFlow.latestValue = status
        if (status == ModemRebootMonitorService.RebootState.SUCCESS) {
            rebootDialogFlow.latestValue = true
            sendModemRebootStatus(true)
        } else if (status == ModemRebootMonitorService.RebootState.ERROR) {
            rebootDialogFlow.latestValue = false
            sendModemRebootStatus(false)
        }
    }

    private fun sendModemRebootStatus(status: Boolean) {
        val modemRebootMessage: Events.ModemRebootMessage =
            Events.ModemRebootMessage(status)
        GlobalBus.bus!!.post(modemRebootMessage)
    }

    fun rebootModem() {
        analyticsManagerInterface.logButtonClickEvent(AnalyticsKeys.BUTTON_RESTART_MODEM_SUPPORT)
        rebootOngoingFlow.latestValue = true
        GlobalScope.launch {
            modemRebootMonitorService.sendRebootModemRequest()
        }
    }

    fun rebootCanceled() {
        modemRebootMonitorService.cancelWork()
        onRebootDialogShown()
        rebootCanceledFlow.latestValue = true
    }

    fun onRebootDialogShown() {
        modemRebootMonitorService.pruneFinishedWork()
    }

    // Works exactly the same way as MutableLiveData.value
    // This allows all the subclasses' live data to be declared as LiveData<T> type instead of MutableLiveData<T> so
    // that their values can't be changed externally but still can internally
    // see https://github.com/IntrepidPursuits/skotlinton-android/pull/33#discussion_r275908063
    @Deprecated("Use `Flow`s and `fun Flow<T>.latestValue` instead.")
    protected var <T : Any?> LiveData<T>.latestValue: T?
        get() = this.value
        set(value) {
            (this as MutableLiveData<T>).value = value
        }

    /**
     * Either reads the latest value from this [Flow] or
     * changes the latest value of this [Flow].
     *
     * Note that this [Flow] has a [BehaviorStateFlow] backing it.
     */
    protected var <T : Any> Flow<T>.latestValue: T
        get() = (this as BehaviorStateFlow<T>).value
        set(value) {
            (this as BehaviorStateFlow<T>).value = value
        }

    protected var <T : Any> EventFlow<T>.latestValue: T
        get() {
            throw IllegalStateException("Cannot read EventFlow value")
        }
        set(value) {
            with(this) { viewModelScope.value = value }
        }

    protected fun <T : Any> EventLiveData<T>.emit(event: T) {
        this.latestValue = LiveEvent(event)
    }

    fun CoroutineScope.interval(initialDelay: Long, delay: Long, action: suspend () -> Unit) {
        launch {
            kotlinx.coroutines.delay(initialDelay)
            while (isActive) {
                action()
                kotlinx.coroutines.delay(delay)
            }
        }
    }

    fun formatUtcString(utcString: String): String {
        val zoneId: ZoneId? = ZoneId.systemDefault()
        val myDate = Instant.parse(utcString.substringBefore('+') + "Z")
            .atZone(ZoneId.of(zoneId.toString()))
        val amPm = if (myDate.hour < 12) "am" else "pm"
        val dateTimeFormatter = DateTimeFormatterBuilder()
            .appendValue(ChronoField.MONTH_OF_YEAR, 2, 2, SignStyle.NEVER)
            .appendLiteral('/')
            .appendValue(ChronoField.DAY_OF_MONTH, 2, 2, SignStyle.NEVER)
            .appendLiteral('/')
            .appendValue(ChronoField.YEAR, 4, 4, SignStyle.NEVER)
            .appendLiteral(" at ")
            .appendValue(ChronoField.CLOCK_HOUR_OF_AMPM)
            .appendLiteral(':')
            .appendValue(ChronoField.MINUTE_OF_HOUR, 2, 2, SignStyle.NEVER)
            .appendLiteral(amPm)
            .toFormatter()
        return dateTimeFormatter.format(myDate)
    }

    fun logModemRebootSuccessDialog() {
        analyticsManagerInterface.logButtonClickEvent(AnalyticsKeys.ALERT_RESTART_MODEM_SUCCESS)
    }

    fun logModemRebootErrorDialog() {
        analyticsManagerInterface.logButtonClickEvent(AnalyticsKeys.ALERT_RESTART_MODEM_FAILURE)
    }

    /**
     * functions shows the reboot progress happen opr not
     *
     */
    fun clearRebootProgress() {
        rebootDialogStatusFlow.latestValue = true
    }

    companion object {
        const val MODEM_STATUS_REFRESH_INTERVAL = 30000L
        const val SPEED_TEST_REFRESH_INTERVAL = 5000L
        const val ENABLE_DISABLE_STATUS_REFRESH_INTERVAL = 90000L
        const val MODEM_STATUS_REFRESH_LINEINFO_INTERVAL = 60000L
    }
}
