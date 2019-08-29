package tat.mukhutdinov.deliveries.deliverylist.ui

import androidx.lifecycle.LiveData
import tat.mukhutdinov.deliveries.infrastructure.ui.BaseBindings

interface DeliveryListBindings : BaseBindings {

    val isRefreshing: LiveData<Boolean>

    fun refresh()
}