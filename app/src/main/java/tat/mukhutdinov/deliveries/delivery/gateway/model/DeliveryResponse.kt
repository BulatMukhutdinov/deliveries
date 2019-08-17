package tat.mukhutdinov.deliveries.delivery.gateway.model

import androidx.annotation.Keep

@Keep
class DeliveryResponse(
    val id: Int?,
    val description: String?,
    val imageUrl: String?,
    val location: LocationResponse?
)

@Keep
data class LocationResponse(
    val lat: Double?,
    val lng: Double?,
    val address: String?
)