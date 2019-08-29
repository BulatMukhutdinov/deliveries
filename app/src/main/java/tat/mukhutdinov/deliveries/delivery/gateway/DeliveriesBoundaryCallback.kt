package tat.mukhutdinov.deliveries.delivery.gateway

import androidx.annotation.MainThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import androidx.paging.PagingRequestHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import tat.mukhutdinov.deliveries.delivery.domain.model.Delivery
import tat.mukhutdinov.deliveries.delivery.gateway.DeliveryBoundGateway.Companion.PAGE_SIZE
import tat.mukhutdinov.deliveries.delivery.gateway.api.DeliveriesApi
import tat.mukhutdinov.deliveries.delivery.gateway.model.DeliveryResponse
import tat.mukhutdinov.deliveries.infrastructure.model.DataState
import timber.log.Timber
import java.util.concurrent.Executors

class DeliveriesBoundaryCallback(
    private val api: DeliveriesApi,
    private val handleResponse: suspend (List<DeliveryResponse>) -> Unit,
    private val coroutineScope: CoroutineScope
) : PagedList.BoundaryCallback<Delivery>() {

    val helper = PagingRequestHelper(Executors.newSingleThreadExecutor())

    val dataState = helper.createStatusLiveData()

    private var page = 0

    /**
     * Database returned 0 items. We should query the backend for more items.
     */
    @MainThread
    override fun onZeroItemsLoaded() {
        helper.runIfNotRunning(PagingRequestHelper.RequestType.INITIAL) { pagingHelperCallback ->
            coroutineScope.launch {
                try {
                    val deliveries = api.getDeliveries(page, PAGE_SIZE)
                    handleResponse(deliveries)
                    pagingHelperCallback.recordSuccess()
                } catch (throwable: Throwable) {
                    Timber.w(throwable)
                    pagingHelperCallback.recordFailure(throwable)
                }
            }
        }
    }

    /**
     * User reached to the end of the list.
     */
    @MainThread
    override fun onItemAtEndLoaded(itemAtEnd: Delivery) {
        helper.runIfNotRunning(PagingRequestHelper.RequestType.AFTER) { pagingHelperCallback ->
            coroutineScope.launch {
                try {
                    val deliveries = api.getDeliveries(++page * PAGE_SIZE, PAGE_SIZE)
                    handleResponse(deliveries)
                    pagingHelperCallback.recordSuccess()
                } catch (throwable: Throwable) {
                    Timber.w(throwable)
                    pagingHelperCallback.recordFailure(throwable)
                }
            }
        }
    }

    fun refresh() {
        page = 0
    }

    private fun PagingRequestHelper.createStatusLiveData(): LiveData<DataState> {
        val liveData = MutableLiveData<DataState>()

        addListener { report ->
            when {
                report.hasRunning() -> liveData.postValue(DataState.Loading)
                report.hasError() -> liveData.postValue(DataState.Error(getErrorMessage(report)))
                else -> liveData.postValue(DataState.Loaded)
            }
        }

        return liveData
    }

    private fun getErrorMessage(report: PagingRequestHelper.StatusReport): String? {
        return PagingRequestHelper.RequestType
            .values()
            .mapNotNull { report.getErrorFor(it)?.message }
            .firstOrNull()
    }
}