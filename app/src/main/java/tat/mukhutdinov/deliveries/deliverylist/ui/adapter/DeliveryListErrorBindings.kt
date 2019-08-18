package tat.mukhutdinov.deliveries.deliverylist.ui.adapter

import androidx.lifecycle.LiveData
import tat.mukhutdinov.deliveries.infrastructure.model.DataState
import tat.mukhutdinov.deliveries.infrastructure.ui.adapter.BaseViewHolderBindings

interface DeliveryListErrorBindings : BaseViewHolderBindings {

    val dataState: LiveData<DataState>

    fun onRetryClicked()
}