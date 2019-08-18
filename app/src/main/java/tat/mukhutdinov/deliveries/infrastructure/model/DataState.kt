package tat.mukhutdinov.deliveries.infrastructure.model

sealed class DataState(val status: Status) {

    object Loaded : DataState(Status.SUCCESS)

    object Loading : DataState(Status.RUNNING)

    class Error(val message: String?) : DataState(Status.FAILED)
}

enum class Status {
    RUNNING,
    SUCCESS,
    FAILED
}