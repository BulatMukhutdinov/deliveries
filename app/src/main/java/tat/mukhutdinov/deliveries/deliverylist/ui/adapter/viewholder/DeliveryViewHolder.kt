package tat.mukhutdinov.deliveries.deliverylist.ui.adapter.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.databinding.DataBindingUtil
import com.squareup.picasso.Picasso
import tat.mukhutdinov.deliveries.R
import tat.mukhutdinov.deliveries.databinding.DeliveryListItemBinding
import tat.mukhutdinov.deliveries.delivery.domain.model.Delivery
import tat.mukhutdinov.deliveries.deliverylist.ui.adapter.DeliveryListItemBindings
import tat.mukhutdinov.deliveries.infrastructure.ui.adapter.BaseViewHolder

class DeliveryViewHolder(
    viewDataBinding: DeliveryListItemBinding,
    bindings: DeliveryListItemBindings
) : BaseViewHolder<DeliveryListItemBindings, DeliveryListItemBinding>(viewDataBinding, bindings) {

    override fun bindTo(delivery: Delivery?) {
        if (delivery == null) return

        ViewCompat.setTransitionName(viewDataBinding.image, delivery.id.toString())

        viewDataBinding.delivery = delivery
        viewDataBinding.image.clipToOutline = true

        super.bindTo(delivery)
    }

    override fun onViewRecycled() {
        super.onViewRecycled()
        Picasso.get().cancelRequest(viewDataBinding.image)
    }

    companion object {

        fun create(parent: ViewGroup, bindings: DeliveryListItemBindings): DeliveryViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val viewDataBinding: DeliveryListItemBinding = DataBindingUtil.inflate(layoutInflater, R.layout.delivery_list_item, parent, false)

            return DeliveryViewHolder(viewDataBinding, bindings)
        }
    }
}