package tat.mukhutdinov.deliveries.delivery.gateway

import androidx.annotation.MainThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.paging.Config
import androidx.paging.toLiveData
import androidx.room.withTransaction
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import tat.mukhutdinov.deliveries.delivery.db.DeliveryDao
import tat.mukhutdinov.deliveries.delivery.domain.boundary.DeliveryGateway
import tat.mukhutdinov.deliveries.delivery.domain.model.Delivery
import tat.mukhutdinov.deliveries.delivery.gateway.api.DeliveriesApi
import tat.mukhutdinov.deliveries.delivery.gateway.converter.DeliveryConverter
import tat.mukhutdinov.deliveries.delivery.gateway.model.DeliveryResponse
import tat.mukhutdinov.deliveries.infrastructure.db.DataBase
import tat.mukhutdinov.deliveries.infrastructure.model.Listing
import tat.mukhutdinov.deliveries.infrastructure.model.NetworkState
import tat.mukhutdinov.deliveries.infrastructure.util.exceptionHandler
import timber.log.Timber

class DeliveryBoundGateway(
    private val db: DataBase,
    private val api: DeliveriesApi,
    private val deliveryDao: DeliveryDao,
    private val deliveryConverter: DeliveryConverter,
    private val coroutineScope: CoroutineScope
) : DeliveryGateway {

    private val boundaryCallback = DeliveriesBoundaryCallback(
        api = api,
        coroutineScope = coroutineScope,
        handleResponse = { insertIntoDatabase(it) }
    )

    @MainThread
    override fun getDeliveries(): Listing<Delivery> {
        val refreshTrigger = MutableLiveData<Unit>()
        val refreshState = Transformations.switchMap(refreshTrigger) { refresh() }

        val pagingConfig = Config(
            initialLoadSizeHint = PAGE_SIZE,
            pageSize = PAGE_SIZE,
            prefetchDistance = PAGE_SIZE
        )

        val deliveries = deliveryDao.getAll()
            .map { deliveryConverter.fromDatabase(it) }
            .toLiveData(
                config = pagingConfig,
                boundaryCallback = boundaryCallback
            )

        return Listing(
            pagedList = deliveries,
            networkState = boundaryCallback.networkState,
            retry = { boundaryCallback.helper.retryAllFailed() },
            refresh = { refreshTrigger.value = null },
            refreshState = refreshState
        )
    }

    /**
     * When refresh is called, we simply run a fresh network request and when it arrives, clear
     * the database table and insert all new items in a transaction.
     * <p>
     * Since the PagedList already uses a database bound data source, it will automatically be
     * updated after the database transaction is finished.
     */
    @MainThread
    private fun refresh(): LiveData<NetworkState> {
        boundaryCallback.refresh()

        val networkState = MutableLiveData<NetworkState>()
        networkState.value = NetworkState.Loading

        coroutineScope.launch(exceptionHandler) {
            try {
                val deliveries = api.getDeliveries(0, PAGE_SIZE)

                db.withTransaction {
                    deliveryDao.clear()
                    insertIntoDatabase(deliveries)
                }

                networkState.postValue(NetworkState.Loaded)
            } catch (throwable: Throwable) {
                Timber.w(throwable)
                networkState.postValue(NetworkState.Error(throwable.message))
            }
        }

        return networkState
    }

    private suspend fun insertIntoDatabase(deliveries: List<DeliveryResponse>) {
        deliveries.forEach { delivery ->
            val entity = deliveryConverter.fromNetwork(delivery)
            deliveryDao.insert(entity)
        }
    }

    companion object {
        const val PAGE_SIZE = 20
    }
}