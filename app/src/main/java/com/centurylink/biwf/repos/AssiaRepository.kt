package com.centurylink.biwf.repos

import com.centurylink.biwf.Either
import com.centurylink.biwf.flatMap
import com.centurylink.biwf.model.assia.ModemInfo
import com.centurylink.biwf.model.devices.BlockResponse
import com.centurylink.biwf.model.devices.DevicesData
import com.centurylink.biwf.model.speedtest.SpeedTestRequestResult
import com.centurylink.biwf.model.speedtest.SpeedTestResponse
import com.centurylink.biwf.model.speedtest.SpeedTestStatus
import com.centurylink.biwf.repos.assia.AssiaTokenManager
import com.centurylink.biwf.service.network.AssiaService
import com.centurylink.biwf.utility.preferences.Preferences
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AssiaRepository @Inject constructor(
    private val preferences: Preferences,
    private val assiaService: AssiaService,
    private val assiaTokenManager: AssiaTokenManager
) {

    suspend fun getModemInfo(): Either<String, ModemInfo>  {
        val result =
            assiaService.getModemInfo(getV3HeaderMap(token = assiaTokenManager.getAssiaToken()))
        return result.mapLeft { it.message?.message.toString() }.flatMap { it->
            it.let {
                if (it.code != "1000") {
                    return Either.Left(it.message)
                }
                val deviceId = it.modemInfo.apInfoList[0].deviceId
                if (!deviceId.isNullOrEmpty()) {
                    preferences.saveAssiaId(deviceId)
                }
                return Either.Right(it.modemInfo)
            }
        }
    }

    // Secondary method for Modem Info retrieval, which forces a ping to the hardware. This 
    // prevents Assia from sending us cached data in the response, but is more expensive so it
    // should only be used for certain use cases which require it. Rebooting uses this method for
    // obtaining the instantaneous "isAlive" value
    suspend fun getModemInfoForcePing(): Either<String,ModemInfo> {
        val result = assiaService.getModemInfo(
            getV3HeaderMap(token = assiaTokenManager.getAssiaToken()).plus("forcePing" to "true")
        )
        return result.mapLeft { it.message?.message.toString() }.flatMap { it ->
            it.let {
                if (it.code != "1000") {
                    return Either.Left(it.message)
                }
                return Either.Right(it.modemInfo)
            }
        }
    }

    suspend fun getDevicesDetails():  Either<String,List<DevicesData>> {
        val result =  assiaService.getDevicesList(getHeaderMap(token = assiaTokenManager.getAssiaToken()))
        return result.mapLeft { it.message?.message.toString() }.flatMap { it ->
            it.let {
                if (it.code != "1000") {
                   return Either.Left(it.message!!)
                }
                return Either.Right(it.devicesDataList)
            }
        }

    }

    suspend fun startSpeedTest():  Either<String,SpeedTestRequestResult> {
        val result =  assiaService.startSpeedTest(getHeaderToStartSpeedTest(token = assiaTokenManager.getAssiaToken()))
        return result.mapLeft { it.message?.message.toString() }.flatMap { it ->
            it.let {
                if (it.code != 1000) {
                    return Either.Left(it.message)
                }
                return Either.Right(it)
            }
        }
    }

    suspend fun checkSpeedTestStatus(speedTestId: Int): Either<String, SpeedTestStatus> {
        val result = assiaService.checkSpeedTestResults(
            getHeaderStatus(
                token = assiaTokenManager.getAssiaToken(),
                requestId = speedTestId
            )
        )
        return result.mapLeft { it.message?.message.toString() }.flatMap { it ->
            it.let {
                if (it.code != 1000) {
                   return Either.Left(it.message)
                }
               return Either.Right(it)
            }
        }
    }

    suspend fun getUpstreamResults(): Either<String,SpeedTestResponse> {
        val result = assiaService.checkSpeedTestUpStreamResults(getHeaderMapWithXhours(token = assiaTokenManager.getAssiaToken()))
        return result.mapLeft { it.message?.message.toString()}.flatMap { it ->
            it.let {
                if (it.code != 1000) {
                    return Either.Left(it.message)
                }
                return Either.Right(it)
            }
        }
    }

    suspend fun getDownstreamResults(): Either<String,SpeedTestResponse> {
        val result =  assiaService.checkSpeedTestDownStreamResults(getHeaderMapWithXhours(token = assiaTokenManager.getAssiaToken()))
        return result.mapLeft { it.message?.message.toString()}.flatMap { it ->
            it.let {
                if (it.code != 1000) {
                    return Either.Left(it.message)
                }
                return Either.Right(it)
            }
        }
    }


    suspend fun blockDevices(stationmac: String): Either<String,BlockResponse> {
        val result = assiaService.blockDevice(
            preferences.getAssiaId(),
            stationmac,
            getHeaderMap(token = assiaTokenManager.getAssiaToken())
        )
        return result.mapLeft { it.message?.message.toString()}.flatMap { it ->
            it.let {
                if (it.code != "1000") {
                    return Either.Left(it.message)
                }
                return Either.Right(it)
            }
        }
    }

    suspend fun unblockDevices(stationmac: String): Either<String, BlockResponse> {
        val result =   assiaService.unBlockDevice(
            preferences.getAssiaId(),
            stationmac,
            getHeaderMap(token = assiaTokenManager.getAssiaToken()))
        return result.mapLeft { it.message?.message.toString()}.flatMap { it ->
            it.let {
                if (it.code != "1000") {
                    return Either.Left(it.message)
                }
                return Either.Right(it)
            }
        }
    }

    private fun getHeaderMap(token: String): Map<String, String> {
        val headerMap = mutableMapOf<String, String>()
        // TODO remove "Authorization" from map when Cloudcheck URLs updated
        headerMap["Authorization"] = "bearer $token"
        headerMap["assiaId"] = preferences.getAssiaId()
        return headerMap
    }

    private fun getV3HeaderMap(token: String): Map<String, String> {
        val headerMap = mutableMapOf<String, String>()
        headerMap["Authorization"] = "bearer $token"
        headerMap["genericId"] = preferences.getLineId()
        return headerMap
    }

    private fun getHeaderMapWithXhours(token: String): Map<String, Any> {
        val headerMap = mutableMapOf<String, Any>()
        // TODO remove "Authorization" from map when Cloudcheck URLs updated
        headerMap["Authorization"] = "bearer $token"
        headerMap["assiaId"] = preferences.getAssiaId()
        headerMap["pastXHours"] = 0
        return headerMap
    }

    private fun getHeaderToStartSpeedTest(token: String): Map<String, Any> {
        val headerMap = mutableMapOf<String, Any>()
        // TODO remove "Authorization" from map when Cloudcheck URLs updated
        headerMap["Authorization"] = "bearer $token"
        headerMap["assiaId"] = preferences.getAssiaId()
        headerMap["pastXHours"] = 0
        headerMap["rtFlagBroadBandSpeed"] = true
        return headerMap
    }

    private fun getHeaderStatus(token: String, requestId: Int): Map<String, Any> {
        val headerMap = mutableMapOf<String, Any>()
        // TODO remove "Authorization" from map when Cloudcheck URLs updated
        headerMap["Authorization"] = "bearer $token"
        headerMap["assiaId"] = preferences.getAssiaId()
        headerMap["requestId"] = requestId
        return headerMap
    }
}