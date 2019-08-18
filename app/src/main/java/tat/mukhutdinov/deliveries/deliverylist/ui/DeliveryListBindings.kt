package tat.mukhutdinov.deliveries.deliverylist.ui

import androidx.lifecycle.LiveData
import tat.mukhutdinov.deliveries.infrastructure.model.DataState
import tat.mukhutdinov.deliveries.infrastructure.ui.BaseBindings

interface DeliveryListBindings : BaseBindings {

    val dataState: LiveData<DataState>
}