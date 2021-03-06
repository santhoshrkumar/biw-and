package com.centurylink.biwf.service.impl.workmanager

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.ListenableWorker.Result.failure
import androidx.work.ListenableWorker.Result.success
import androidx.work.WorkerParameters

import com.centurylink.biwf.repos.OAuthAssiaRepository
import com.centurylink.biwf.utility.AppUtil

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import timber.log.Timber

class ModemRebootMonitorWorker constructor(
    context: Context,
    workerParams: WorkerParameters,
    private val oAuthAssiaRepository: OAuthAssiaRepository
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        var timeBeforeFailure = MAX_TIMEOUT_MILLIS
        AppUtil.rebootOnGoingStatus = true
        while (timeBeforeFailure > 0) {
            delay(RETRY_MILLIS)

            if (isRebootComplete()) {
                Timber.d("ModemRebootWorker - COMPLETE")
                return@withContext success()
            } else {
                return@withContext failure()
            }
            timeBeforeFailure -= RETRY_MILLIS
            Timber.d("ModemRebootWorker - not complete - time remaining - $timeBeforeFailure")
        }
        return@withContext failure()
    }

    private suspend fun isRebootComplete(): Boolean {
        val result = oAuthAssiaRepository.getModemInfoForcePing()
        result.fold(ifRight = {
            return it.apInfoList[0].isAlive
        }, ifLeft = {
            return false
        })
    }

    companion object {
        const val RETRY_MILLIS = 30000L
        const val MAX_TIMEOUT_MILLIS = 250000L
        const val UNIQUE_NAME = "modem-reboot"
    }
}
