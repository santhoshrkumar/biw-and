package com.centurylink.biwf.screens.home.devices

import android.os.Bundle
import androidx.lifecycle.viewModelScope
import com.centurylink.biwf.base.BaseViewModel
import com.centurylink.biwf.coordinators.DevicesCoordinatorDestinations
import com.centurylink.biwf.model.devices.DevicesData
import com.centurylink.biwf.model.devices.DevicesInfo
import com.centurylink.biwf.repos.AssiaRepository
import com.centurylink.biwf.repos.DevicesRepository
import com.centurylink.biwf.screens.deviceusagedetails.UsageDetailsActivity
import com.centurylink.biwf.utility.BehaviorStateFlow
import com.centurylink.biwf.utility.EventFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

class DevicesViewModel @Inject constructor(
    private val devicesRepository: DevicesRepository,
    private val asiaRepository: AssiaRepository
) : BaseViewModel() {

    var errorMessageFlow = EventFlow<String>()
    var progressViewFlow = EventFlow<Boolean>()
    var devicesListFlow: Flow<UIDevicesTypeDetails> = BehaviorStateFlow()
    val myState = EventFlow<DevicesCoordinatorDestinations>()

    init {
        initApis()
    }

    fun initApis() {
        progressViewFlow.latestValue = true
        viewModelScope.launch {
            requestDevices()
        }
    }

    private suspend fun requestMockDevices() {
        val deviceDetails = devicesRepository.getDevicesDetails()
        deviceDetails.fold(ifLeft = {
            errorMessageFlow.latestValue = it
        }) {
            progressViewFlow.latestValue = false
            sortAndDisplayDeviceInfo(it)
        }
    }

    private suspend fun requestDevices() {
        val deviceDetails = asiaRepository.getDevicesDetails()
        sortAndDisplayDeviceInfo(deviceDetails)
    }

    private fun sortAndDisplayDeviceInfo(deviceInfo: DevicesInfo) {
        progressViewFlow.latestValue = false
        val removedList = deviceInfo.devicesDataList.filter { it.blocked }
        val connectedList = deviceInfo.devicesDataList.filter { !it.blocked }
        val deviceMap: HashMap<DeviceStatus, List<DevicesData>> = HashMap()
        deviceMap[DeviceStatus.CONNECTED] = connectedList
        if (!removedList.isNullOrEmpty()) {
            deviceMap[DeviceStatus.BLOCKED] = removedList
        }
        devicesListFlow.latestValue = UIDevicesTypeDetails(deviceMap)
    }

    fun navigateToUsageDetails(devicesInfo: DevicesData) {
        val bundle = Bundle()
        bundle.putString(UsageDetailsActivity.HOST_NAME, devicesInfo.hostName)
        bundle.putString(UsageDetailsActivity.STA_MAC, devicesInfo.stationMac)
        DevicesCoordinatorDestinations.bundle = bundle
        myState.latestValue = DevicesCoordinatorDestinations.DEVICE_DETAILS
    }

    data class UIDevicesTypeDetails(
        val deviceSortMap: HashMap<DeviceStatus, List<DevicesData>> = HashMap()
    )
}