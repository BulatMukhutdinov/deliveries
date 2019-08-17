package tat.mukhutdinov.deliveries.delivery.gateway.converter

import tat.mukhutdinov.deliveries.delivery.db.DeliveryEntity
import tat.mukhutdinov.deliveries.delivery.domain.model.Delivery
import tat.mukhutdinov.deliveries.delivery.gateway.model.DeliveryResponse

class DeliveryConverter {

    fun fromNetwork(response: DeliveryResponse): DeliveryEntity =
        DeliveryEntity(
            id = response.id ?: 0,
            address = response.location?.address.orEmpty(),
            description = response.description.orEmpty(),
            image = response.imageUrl.orEmpty(),
            lat = response.location?.lat ?: 0.0,
            lng = response.location?.lng ?: 0.0
        )

    fun fromDatabase(entity: DeliveryEntity): Delivery =
        Delivery(
            id = entity.id,
            lng = entity.lng,
            lat = entity.lat,
            image = entity.image,
            description = entity.description,
            address = entity.address
        )
}