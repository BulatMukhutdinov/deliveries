package tat.mukhutdinov.deliveries.delivery.domain

import tat.mukhutdinov.deliveries.delivery.domain.boundary.DeliveryGateway
import tat.mukhutdinov.deliveries.delivery.domain.model.Delivery
import tat.mukhutdinov.deliveries.delivery.ui.boundary.DeliveryDomain
import tat.mukhutdinov.deliveries.infrastructure.model.Listing

class DeliveryInteractor(private val deliveryGateway: DeliveryGateway) : DeliveryDomain {

    override fun getDeliveries(): Listing<Delivery> =
        deliveryGateway.getDeliveries()
}