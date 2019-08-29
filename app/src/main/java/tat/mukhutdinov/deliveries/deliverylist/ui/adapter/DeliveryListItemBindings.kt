package tat.mukhutdinov.deliveries.deliverylist.ui.adapter

import android.widget.ImageView
import tat.mukhutdinov.deliveries.delivery.domain.model.Delivery
import tat.mukhutdinov.deliveries.infrastructure.ui.adapter.BaseViewHolderBindings

interface DeliveryListItemBindings : BaseViewHolderBindings {

    fun onDeliveryClicked(delivery: Delivery, image: ImageView)
}