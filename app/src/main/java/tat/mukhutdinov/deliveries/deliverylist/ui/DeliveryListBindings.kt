package tat.mukhutdinov.deliveries.deliverylist.ui

import androidx.lifecycle.LiveData
import tat.mukhutdinov.deliveries.infrastructure.model.NetworkState
import tat.mukhutdinov.deliveries.infrastructure.ui.BaseBindings

interface DeliveryListBindings : BaseBindings {

    val networkState: LiveData<NetworkState>
}