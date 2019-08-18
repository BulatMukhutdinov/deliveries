package tat.mukhutdinov.deliveries.delivery.ui.boundary

import tat.mukhutdinov.deliveries.delivery.domain.model.Delivery
import tat.mukhutdinov.deliveries.infrastructure.model.Listing

interface DeliveryDomain {

    fun getDeliveries(): Listing<Delivery>
}