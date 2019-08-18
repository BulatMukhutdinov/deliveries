package tat.mukhutdinov.deliveries.deliverylist.ui

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.core.view.doOnPreDraw
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf
import tat.mukhutdinov.deliveries.R
import tat.mukhutdinov.deliveries.databinding.DeliveryListBinding
import tat.mukhutdinov.deliveries.delivery.domain.model.Delivery
import tat.mukhutdinov.deliveries.delivery.ui.boundary.DeliveryDomain
import tat.mukhutdinov.deliveries.deliverylist.ui.adapter.DeliveriesAdapter
import tat.mukhutdinov.deliveries.deliverylist.ui.adapter.DeliveryListItemBindings
import tat.mukhutdinov.deliveries.deliverylist.ui.adapter.DeliveryListErrorBindings
import tat.mukhutdinov.deliveries.infrastructure.model.DataState
import tat.mukhutdinov.deliveries.infrastructure.ui.BaseViewModel

class DeliveryListViewModel : BaseViewModel<DeliveryListBindings, DeliveryListBinding>(), DeliveryListBindings,
    DeliveryListItemBindings, DeliveryListErrorBindings {

    override val layoutId: Int = R.layout.delivery_list

    override val bindings: DeliveryListBindings = this

    private val deliveryDomain: DeliveryDomain by inject { parametersOf(this) }

    override val dataState = MutableLiveData<DataState>(DataState.Loading)

    private val listing = deliveryDomain.getDeliveries()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        postponeEnterTransition()
        view.doOnPreDraw { startPostponedEnterTransition() }

        setupList()

        setupRefresh()
    }

    private fun setupList() {
        val adapter = DeliveriesAdapter(this, this)

        viewBinding.deliveries.adapter = adapter
        viewBinding.deliveries.setHasFixedSize(true)

        listing.pagedList.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })

        listing.dataState.observe(viewLifecycleOwner, Observer {
            dataState.value = it

            adapter.updateDataState(it)

            if (it != DataState.Loaded && isLastItemVisible()) {
                viewBinding.deliveries.scrollToPosition(adapter.itemCount - 1)
            }
        })
    }

    private fun isLastItemVisible(): Boolean {
        val layoutManager = viewBinding.deliveries.layoutManager as LinearLayoutManager
        val visibleItemCount = layoutManager.childCount
        val totalItemCount = layoutManager.itemCount
        val firstVisibleItemPosition = layoutManager.findFirstCompletelyVisibleItemPosition()

        return visibleItemCount + firstVisibleItemPosition >= totalItemCount && firstVisibleItemPosition >= 0
    }

    private fun setupRefresh() {
        viewBinding.refresh.setOnRefreshListener {
            listing.refresh()
        }

        listing.refreshState.observe(viewLifecycleOwner, Observer {
            viewBinding.refresh.isRefreshing = it == DataState.Loading
        })
    }

    override fun onDeliveryClicked(delivery: Delivery, image: ImageView) {
        val extras = FragmentNavigatorExtras(image to delivery.id.toString())

        view?.findNavController()?.navigate(DeliveryListViewModelDirections.toDetails(delivery), extras)
    }

    override fun onRetryClicked() {
        listing.retry()
    }
}