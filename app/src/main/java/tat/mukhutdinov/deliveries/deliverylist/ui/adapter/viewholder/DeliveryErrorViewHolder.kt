package tat.mukhutdinov.deliveries.deliverylist.ui.adapter.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import tat.mukhutdinov.deliveries.R
import tat.mukhutdinov.deliveries.databinding.DeliveryListErrorItemBinding
import tat.mukhutdinov.deliveries.deliverylist.ui.adapter.DeliveryListErrorBindings
import tat.mukhutdinov.deliveries.infrastructure.ui.adapter.BaseViewHolder

class DeliveryErrorViewHolder(
    viewDataBinding: DeliveryListErrorItemBinding,
    bindings: DeliveryListErrorBindings
) : BaseViewHolder<DeliveryListErrorBindings, DeliveryListErrorItemBinding>(viewDataBinding, bindings) {

    companion object {

        fun create(parent: ViewGroup, bindings: DeliveryListErrorBindings): DeliveryErrorViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val viewDataBinding: DeliveryListErrorItemBinding = DataBindingUtil.inflate(layoutInflater, R.layout.delivery_list_error_item, parent, false)

            return DeliveryErrorViewHolder(viewDataBinding, bindings)
        }
    }
}