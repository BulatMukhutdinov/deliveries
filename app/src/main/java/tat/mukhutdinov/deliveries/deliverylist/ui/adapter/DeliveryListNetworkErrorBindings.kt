package tat.mukhutdinov.deliveries.deliverylist.ui.adapter

import androidx.lifecycle.LiveData
import tat.mukhutdinov.deliveries.infrastructure.model.NetworkState
import tat.mukhutdinov.deliveries.infrastructure.ui.adapter.BaseViewHolderBindings

interface DeliveryListNetworkErrorBindings : BaseViewHolderBindings {

    val networkState: LiveData<NetworkState>

    fun onRetryClicked()
}