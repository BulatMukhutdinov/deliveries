package tat.mukhutdinov.deliveries.deliverylist.ui.adapter.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import tat.mukhutdinov.deliveries.R
import tat.mukhutdinov.deliveries.databinding.DeliveryListLoadingItemBinding
import tat.mukhutdinov.deliveries.infrastructure.ui.adapter.BaseViewHolder
import tat.mukhutdinov.deliveries.infrastructure.ui.adapter.BaseViewHolderBindings

class DeliveryLoadingViewHolder(
    viewDataBinding: DeliveryListLoadingItemBinding,
    bindings: BaseViewHolderBindings
) : BaseViewHolder<BaseViewHolderBindings, DeliveryListLoadingItemBinding>(viewDataBinding, bindings) {

    companion object {

        fun create(parent: ViewGroup): DeliveryLoadingViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val viewDataBinding: DeliveryListLoadingItemBinding = DataBindingUtil.inflate(layoutInflater, R.layout.delivery_list_loading_item, parent, false)

            return DeliveryLoadingViewHolder(viewDataBinding, object : BaseViewHolderBindings {})
        }
    }
}