package com.centurylink.biwf.service.network

import com.centurylink.biwf.model.FiberServiceResult
import com.centurylink.biwf.model.appointment.AppointmentResponse
import com.centurylink.biwf.model.appointment.AppointmentSlots
import com.centurylink.biwf.model.appointment.Appointments
import com.centurylink.biwf.model.appointment.RescheduleInfo
import com.centurylink.biwf.model.devices.DevicesInfo
import com.centurylink.biwf.model.faq.Faq
import com.centurylink.biwf.model.notification.NotificationSource
import com.centurylink.biwf.model.sumup.SumUpInput
import com.centurylink.biwf.model.sumup.SumUpResult
import com.centurylink.biwf.model.usagedetails.TrafficUsageResponse
import com.centurylink.biwf.model.wifi.WifiDetails
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * This interface bundles are services that are not yet ready on the backend, to
 * (temporarily) unblock the app's integration with the backend.
 */
interface IntegrationRestServices {
    /**
     * A dummy/test integration service that just sums up two values.
     */
    @Deprecated("This service is just a proof-of-concept.")
    @POST("sumUp/{value1}")
    suspend fun calculateSum(
        @Path("value1") value1: Int,
        @Query("value2") value2: Int,
        @Body input: SumUpInput
    ): SumUpResult

    @GET("/sobject/notification/{value1}")
    suspend fun getNotificationDetails(
        @Path("value1") value1: String
    ): FiberServiceResult<NotificationSource>

    @GET("/sobject/appointment/{value1}")
    suspend fun getAppointmentDetails(
        @Path("value1") value1: String
    ): FiberServiceResult<Appointments>

    @GET("/sobject/faq/{value1}")
    suspend fun getFaqDetails(
        @Path("value1") value1: String
    ): FiberServiceResult<Faq>

    @GET("/sobject/devices/{value1}")
    suspend fun getDevicesDetails(
        @Path("value1") value1: String
    ): FiberServiceResult<DevicesInfo>

    @GET("api/v2/wifi/diags/station/traffic")
    suspend fun getUsageDetails(): FiberServiceResult<TrafficUsageResponse>

    @GET("/sobject/getslots/{value1}")
    suspend fun getAppointmentSlots(
        @Path("value1") value1: String
    ): FiberServiceResult<AppointmentSlots>

    @POST("/sobject/reschedule/{value1}")
    suspend fun submitAppointments(
        @Body input: RescheduleInfo
    ): FiberServiceResult<AppointmentResponse>

    @GET("/sobject/wifi/{value1}")
    suspend fun getWifiListandCredentials(
        @Path("value1") value1: String
    ): FiberServiceResult<WifiDetails>
}
