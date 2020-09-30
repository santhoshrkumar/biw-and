package com.centurylink.biwf.screens.networkstatus

import com.centurylink.biwf.R
import com.centurylink.biwf.model.assia.ApInfo
import com.centurylink.biwf.model.devices.DeviceConnectionStatus
import com.centurylink.biwf.model.devices.DevicesData
import com.centurylink.biwf.model.wifi.NetWorkBand
import java.util.regex.Matcher
import java.util.regex.Pattern

class ModemUtils {
    companion object {
        val PAT_DEFAULT1: Pattern = Pattern.compile(
            "(.*?)"
                    + "(?:\\-(\\d+)\\-)?(\\.[^.]*)?")

        fun getGuestNetworkName(apiInfo: ApInfo): String {
            var guestNetworkName = ""
            if (apiInfo.ssidMap.containsKey(NetWorkBand.Band5G_Guest4.name)) {
                guestNetworkName =
                    apiInfo.ssidMap.getValue(NetWorkBand.Band5G_Guest4.name)
            } else if (apiInfo.ssidMap.containsKey(NetWorkBand.Band2G_Guest4.name)) {
                guestNetworkName = apiInfo.ssidMap.getValue(NetWorkBand.Band2G_Guest4.name)
            }
            return guestNetworkName
        }

        fun getGuestNetworkState(apiInfo: ApInfo): Boolean {
            return apiInfo.bssidMap.containsValue(NetWorkBand.Band5G_Guest4.name) || apiInfo.bssidMap.containsValue(
                NetWorkBand.Band2G_Guest4.name
            )
        }

        fun getRegularNetworkState(apiInfo: ApInfo) =
            apiInfo.bssidMap.containsValue(NetWorkBand.Band5G.name) || apiInfo.bssidMap.containsValue(
                NetWorkBand.Band2G.name
            )

        fun getRegularNetworkName(modemInfo: ApInfo): String {
            var wifiNetworkName = ""
            if (modemInfo.ssidMap.containsKey(NetWorkBand.Band5G.name)) {
                wifiNetworkName = modemInfo.ssidMap.getValue(NetWorkBand.Band5G.name)
            } else if (modemInfo.ssidMap.containsKey(NetWorkBand.Band2G.name)) {
                wifiNetworkName = modemInfo.ssidMap.getValue(NetWorkBand.Band2G.name)
            }
            return wifiNetworkName
        }

        //Icons for buttons are different from list view, added method to get button status icons.
        fun getConnectionStatusIcon(devicesData: DevicesData): Int {
            val signalStrength = devicesData.rssi
            val connectionMode = devicesData.connectedInterface
            when (devicesData.deviceConnectionStatus) {
                DeviceConnectionStatus.MODEM_OFF -> {
                    return R.drawable.ic_network_no_internet
                }
                DeviceConnectionStatus.FAILURE -> {
                    return R.drawable.ic_icon_reload
                }
                DeviceConnectionStatus.PAUSED -> {
                    return R.drawable.ic_wi_fi_off_btn
                }
                DeviceConnectionStatus.DEVICE_CONNECTED -> {
                    if (!connectionMode.isNullOrEmpty() && connectionMode.equals(
                            "Ethernet",
                            true
                        )
                    ) {
                        return R.drawable.ic_network_3_bars
                    } else {
                        return when (signalStrength) {
                            in -50..-1 -> {
                                R.drawable.ic_network_3_bars
                            }
                            in -51 downTo -75 -> {
                                R.drawable.ic_network_2_bars
                            }
                            in -76 downTo -90 -> {
                                R.drawable.ic_network_1_bar
                            }
                            else -> {
                                R.drawable.ic_network_1_bar
                            }
                        }
                    }
                }
                else -> return R.drawable.ic_network_no_internet
            }
        }

        fun getConnectionStatusIconForDeviceList(devicesData: DevicesData): Int {
            val signalStrength = devicesData.rssi
            val connectionMode = devicesData.connectedInterface
            when (devicesData.deviceConnectionStatus) {
                DeviceConnectionStatus.MODEM_OFF -> {
                    return R.drawable.ic_cta_wi_fi_disconnected
                }
                DeviceConnectionStatus.FAILURE -> {
                    return R.drawable.ic_icon_reload
                }
                DeviceConnectionStatus.PAUSED -> {
                    return R.drawable.ic_off
                }
                DeviceConnectionStatus.DEVICE_CONNECTED -> {
                    if (!connectionMode.isNullOrEmpty() && connectionMode.equals(
                            "Ethernet",
                            true
                        )
                    ) {
                        return R.drawable.ic_ethernet
                    } else {
                        return when (signalStrength) {
                            in -50..-1 -> {
                                R.drawable.ic_strong_signal
                            }
                            in -51 downTo -75 -> {
                                R.drawable.ic_medium_signal
                            }
                            in -76 downTo -90 -> {
                                R.drawable.ic_weak_signal
                            }
                            else -> {
                                R.drawable.ic_weak_signal
                            }
                        }
                    }
                }
                else -> return R.drawable.ic_cta_wi_fi_disconnected
            }
        }

        fun getNewName(
            filename: String?,
            fileList: ArrayList<String?>
        ): String? {
            var filename = filename
            if (fileExists(filename, fileList)) {
                val m: Matcher = PAT_DEFAULT1.matcher(filename)
                if (m.matches()) {
                    val prefix: String = m.group(1)
                    val last: String = m.group(2)
                    var suffix: String = m.group(3)
                    if (suffix == null) suffix = ""
                    var count = last?.toInt() ?: 0
                    do {
                        count++
                        filename = "$prefix-$count$suffix"
                    } while (fileExists(filename, fileList))
                }
            }
            return filename
        }

        private fun fileExists(filename: String?, fileList: ArrayList<String?>): Boolean {
            return fileList.contains(filename)
        }
    }
}