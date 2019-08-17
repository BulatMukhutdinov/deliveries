package tat.mukhutdinov.deliveries.deliverylist.ui.adapter

import androidx.recyclerview.widget.DiffUtil
import tat.mukhutdinov.deliveries.delivery.domain.model.Delivery

class DeliveryDiffUtilItemCallback : DiffUtil.ItemCallback<Delivery>() {

    override fun areItemsTheSame(oldItem: Delivery, newItem: Delivery): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Delivery, newItem: Delivery): Boolean =
        oldItem == newItem
}