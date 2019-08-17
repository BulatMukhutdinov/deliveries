package tat.mukhutdinov.deliveries.deliverylist.ui.adapter.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import tat.mukhutdinov.deliveries.R
import tat.mukhutdinov.deliveries.databinding.DeliveryListErrorItemBinding
import tat.mukhutdinov.deliveries.delivery.domain.model.Delivery
import tat.mukhutdinov.deliveries.deliverylist.ui.adapter.DeliveryListNetworkErrorBindings
import tat.mukhutdinov.deliveries.infrastructure.ui.adapter.BaseViewHolder

class DeliveryNetworkErrorViewHolder(
    viewDataBinding: DeliveryListErrorItemBinding,
    bindings: DeliveryListNetworkErrorBindings
) : BaseViewHolder<DeliveryListNetworkErrorBindings, DeliveryListErrorItemBinding>(viewDataBinding, bindings) {

    companion object {

        fun create(parent: ViewGroup, bindings: DeliveryListNetworkErrorBindings): DeliveryNetworkErrorViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val viewDataBinding: DeliveryListErrorItemBinding = DataBindingUtil.inflate(layoutInflater, R.layout.delivery_list_error_item, parent, false)

            return DeliveryNetworkErrorViewHolder(viewDataBinding, bindings)
        }
    }
}