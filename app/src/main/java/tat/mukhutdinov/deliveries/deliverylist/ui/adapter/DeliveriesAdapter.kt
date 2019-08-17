package tat.mukhutdinov.deliveries.deliverylist.ui.adapter

import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import tat.mukhutdinov.deliveries.R
import tat.mukhutdinov.deliveries.delivery.domain.model.Delivery
import tat.mukhutdinov.deliveries.deliverylist.ui.adapter.viewholder.DeliveryLoadingViewHolder
import tat.mukhutdinov.deliveries.deliverylist.ui.adapter.viewholder.DeliveryNetworkErrorViewHolder
import tat.mukhutdinov.deliveries.deliverylist.ui.adapter.viewholder.DeliveryViewHolder
import tat.mukhutdinov.deliveries.infrastructure.model.NetworkState
import tat.mukhutdinov.deliveries.infrastructure.model.Status
import tat.mukhutdinov.deliveries.infrastructure.ui.adapter.BaseViewHolder

class DeliveriesAdapter(
    private val deliveryListItemBindings: DeliveryListItemBindings,
    private val deliveryListNetworkErrorBindings: DeliveryListNetworkErrorBindings
) : PagedListAdapter<Delivery, BaseViewHolder<*, *>>(DeliveryDiffUtilItemCallback()) {

    private var networkState: NetworkState = NetworkState.Loaded

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*, *> =
        when (viewType) {
            R.layout.delivery_list_item -> DeliveryViewHolder.create(parent, deliveryListItemBindings)
            R.layout.delivery_list_error_item -> DeliveryNetworkErrorViewHolder.create(parent, deliveryListNetworkErrorBindings)
            R.layout.delivery_list_loading_item -> DeliveryLoadingViewHolder.create(parent)
            else -> throw IllegalArgumentException("unknown view type $viewType")
        }

    override fun onBindViewHolder(holder: BaseViewHolder<*, *>, position: Int) {
        when (getItemViewType(position)) {
            R.layout.delivery_list_item -> (holder as DeliveryViewHolder).bindTo(getItem(position))
            R.layout.delivery_list_error_item -> (holder as DeliveryNetworkErrorViewHolder).bindTo()
            R.layout.delivery_list_loading_item -> (holder as DeliveryLoadingViewHolder).bindTo()
        }
    }

    override fun getItemViewType(position: Int): Int =
        if (position == itemCount - 1) {
            when (networkState.status) {
                Status.FAILED -> R.layout.delivery_list_error_item
                Status.RUNNING -> R.layout.delivery_list_loading_item
                Status.SUCCESS -> R.layout.delivery_list_item
            }
        } else {
            R.layout.delivery_list_item
        }

    fun updateNetworkState(newNetworkState: NetworkState) {
        val previousState = this.networkState
        val hadExtraRow = hasExtraRow()
        this.networkState = newNetworkState
        val hasExtraRow = hasExtraRow()

        if (hadExtraRow != hasExtraRow) {
            if (hadExtraRow) {
                notifyItemRemoved(super.getItemCount())
            } else {
                notifyItemInserted(super.getItemCount())
            }
        } else if (hasExtraRow && previousState != newNetworkState) {
            notifyItemChanged(itemCount - 1)
        }
    }

    private fun hasExtraRow() =
        networkState.status != Status.SUCCESS

    override fun getItemCount(): Int {
        return super.getItemCount() + if (hasExtraRow()) 1 else 0
    }

    override fun onViewRecycled(holder: BaseViewHolder<*, *>) {
        super.onViewRecycled(holder)
        holder.onViewRecycled()
    }

    override fun getItemId(position: Int): Long {
        return getItem(position)?.id?.toLong() ?: -1
    }
}