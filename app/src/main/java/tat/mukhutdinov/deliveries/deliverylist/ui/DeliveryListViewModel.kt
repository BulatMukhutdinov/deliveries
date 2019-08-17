package tat.mukhutdinov.deliveries.deliverylist.ui

import android.os.Bundle
import android.view.View
import android.widget.ImageView
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
import tat.mukhutdinov.deliveries.deliverylist.ui.adapter.DeliveryListNetworkErrorBindings
import tat.mukhutdinov.deliveries.infrastructure.model.NetworkState
import tat.mukhutdinov.deliveries.infrastructure.ui.BaseViewModel

class DeliveryListViewModel : BaseViewModel<DeliveryListBindings, DeliveryListBinding>(), DeliveryListBindings,
    DeliveryListItemBindings, DeliveryListNetworkErrorBindings {

    override val layoutId: Int = R.layout.delivery_list

    override val bindings: DeliveryListBindings = this

    private val deliveryDomain: DeliveryDomain by inject { parametersOf(this) }

    override val networkState = MutableLiveData<NetworkState>(NetworkState.Loading)

    private val listing = deliveryDomain.getDeliveries()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupList()

        setupRefresh()
    }

    private fun setupList() {
        val adapter = DeliveriesAdapter(this, this)

        viewBinding.deliveries.apply {
            this.adapter = adapter
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
        }

        listing.pagedList.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })

        listing.networkState.observe(viewLifecycleOwner, Observer {
            networkState.value = it

            adapter.updateNetworkState(it)

            if (it != NetworkState.Loaded && isLastItemVisible()) {
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
            viewBinding.refresh.isRefreshing = it == NetworkState.Loading
        })
    }

    override fun onDeliveryClicked(delivery: Delivery, image: ImageView) {
        val extras = FragmentNavigatorExtras(image to image.transitionName)
        val direction = DeliveryListViewModelDirections.toDetails(delivery)

        view?.findNavController()?.navigate(
            direction.actionId,
            direction.arguments,
            null,
            extras //https://github.com/googlesamples/android-architecture-components/issues/495
        )
    }

    override fun onRetryClicked() {
        listing.retry()
    }
}