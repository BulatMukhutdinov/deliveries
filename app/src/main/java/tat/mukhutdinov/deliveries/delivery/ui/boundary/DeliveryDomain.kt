package tat.mukhutdinov.deliveries.delivery.ui.boundary

import kotlinx.coroutines.CoroutineScope
import tat.mukhutdinov.deliveries.delivery.domain.model.Delivery
import tat.mukhutdinov.deliveries.infrastructure.model.Listing

interface DeliveryDomain {

    fun getDeliveries(): Listing<Delivery>

    suspend fun test() : Int
}