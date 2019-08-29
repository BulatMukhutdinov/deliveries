package tat.mukhutdinov.deliveries.delivery.domain.boundary

import tat.mukhutdinov.deliveries.delivery.domain.model.Delivery
import tat.mukhutdinov.deliveries.infrastructure.model.Listing

interface DeliveryGateway {

    fun getDeliveries(): Listing<Delivery>
}