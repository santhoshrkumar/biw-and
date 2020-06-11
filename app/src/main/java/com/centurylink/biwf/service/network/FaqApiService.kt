package com.centurylink.biwf.service.network

import com.centurylink.biwf.model.FiberServiceResult
import com.centurylink.biwf.model.faq.Faq
import retrofit2.http.GET
import retrofit2.http.Query

interface FaqApiService {

    @GET("query")
    suspend fun getFaqDetails(@Query("q") id: String): FiberServiceResult<Faq>
}