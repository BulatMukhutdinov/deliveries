package tat.mukhutdinov.deliveries.delivery.domain

import tat.mukhutdinov.deliveries.delivery.domain.boundary.DeliveryGateway
import tat.mukhutdinov.deliveries.delivery.domain.model.Delivery
import tat.mukhutdinov.deliveries.delivery.ui.boundary.DeliveryDomain
import tat.mukhutdinov.deliveries.infrastructure.model.Listing

class DeliveryInteractor(private val deliveryGateway: DeliveryGateway) : DeliveryDomain {

    override suspend fun test(): Int {
        kotlinx.coroutines.delay(5000)
        return 10
    }

    override fun getDeliveries(): Listing<Delivery> =
        deliveryGateway.getDeliveries()
}