package tat.mukhutdinov.deliveries.delivery.domain.model

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.android.parcel.Parcelize

@Keep
@Parcelize
data class Delivery(
    val id: Int,
    val description: String,
    val image: String,
    val lat: Double,
    val lng: Double,
    val address: String
) : Parcelable