package tat.mukhutdinov.deliveries.deliverylist.ui.adapter

import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import tat.mukhutdinov.deliveries.R
import tat.mukhutdinov.deliveries.delivery.domain.model.Delivery
import tat.mukhutdinov.deliveries.deliverylist.ui.adapter.viewholder.DeliveryLoadingViewHolder
import tat.mukhutdinov.deliveries.deliverylist.ui.adapter.viewholder.DeliveryErrorViewHolder
import tat.mukhutdinov.deliveries.deliverylist.ui.adapter.viewholder.DeliveryViewHolder
import tat.mukhutdinov.deliveries.infrastructure.model.DataState
import tat.mukhutdinov.deliveries.infrastructure.model.Status
import tat.mukhutdinov.deliveries.infrastructure.ui.adapter.BaseViewHolder

class DeliveriesAdapter(
    private val deliveryListItemBindings: DeliveryListItemBindings,
    private val deliveryListNetworkErrorBindings: DeliveryListErrorBindings
) : PagedListAdapter<Delivery, BaseViewHolder<*, *>>(DeliveryDiffUtilItemCallback()) {

    private var dataState: DataState = DataState.Loaded

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*, *> =
        when (viewType) {
            R.layout.delivery_list_item -> DeliveryViewHolder.create(parent, deliveryListItemBindings)
            R.layout.delivery_list_error_item -> DeliveryErrorViewHolder.create(parent, deliveryListNetworkErrorBindings)
            R.layout.delivery_list_loading_item -> DeliveryLoadingViewHolder.create(parent)
            else -> throw IllegalArgumentException("unknown view type $viewType")
        }

    override fun onBindViewHolder(holder: BaseViewHolder<*, *>, position: Int) {
        when (getItemViewType(position)) {
            R.layout.delivery_list_item -> (holder as DeliveryViewHolder).bindTo(getItem(position))
            R.layout.delivery_list_error_item -> (holder as DeliveryErrorViewHolder).bindTo()
            R.layout.delivery_list_loading_item -> (holder as DeliveryLoadingViewHolder).bindTo()
        }
    }

    override fun getItemViewType(position: Int): Int =
        if (position == itemCount - 1) {
            when (dataState.status) {
                Status.FAILED -> R.layout.delivery_list_error_item
                Status.RUNNING -> R.layout.delivery_list_loading_item
                Status.SUCCESS -> R.layout.delivery_list_item
            }
        } else {
            R.layout.delivery_list_item
        }

    fun updateDataState(newDataState: DataState) {
        val previousState = this.dataState
        val hadExtraRow = hasExtraRow()
        this.dataState = newDataState
        val hasExtraRow = hasExtraRow()

        if (hadExtraRow != hasExtraRow) {
            if (hadExtraRow) {
                notifyItemRemoved(super.getItemCount())
            } else {
                notifyItemInserted(super.getItemCount())
            }
        } else if (hasExtraRow && previousState != newDataState) {
            notifyItemChanged(itemCount - 1)
        }
    }

    private fun hasExtraRow() =
        dataState.status != Status.SUCCESS

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