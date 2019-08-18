package tat.mukhutdinov.deliveries.infrastructure.model

import androidx.lifecycle.LiveData
import androidx.paging.PagedList

data class Listing<T>(
    val pagedList: LiveData<PagedList<T>>,
    val dataState: LiveData<DataState>,
    val refreshState: LiveData<DataState>,
    val refresh: () -> Unit,
    val retry: () -> Unit
)