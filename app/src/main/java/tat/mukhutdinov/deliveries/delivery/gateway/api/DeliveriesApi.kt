package tat.mukhutdinov.deliveries.delivery.gateway.api

import retrofit2.http.GET
import retrofit2.http.Query
import tat.mukhutdinov.deliveries.delivery.gateway.model.DeliveryResponse

interface DeliveriesApi {

    @GET("deliveries")
    suspend fun getDeliveries(@Query("offset") offset: Int, @Query("limit") limit: Int): List<DeliveryResponse>
}