package tat.mukhutdinov.deliveries.delivery.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import tat.mukhutdinov.deliveries.delivery.db.DeliveryEntity.Companion.COLUMN_DESCRIPTION
import tat.mukhutdinov.deliveries.delivery.db.DeliveryEntity.Companion.COLUMN_ID
import tat.mukhutdinov.deliveries.delivery.db.DeliveryEntity.Companion.TABLE_NAME

@Entity(
    tableName = TABLE_NAME,
    indices = [Index(value = [COLUMN_ID]), Index(value = [COLUMN_DESCRIPTION])]
)
class DeliveryEntity(
    @PrimaryKey
    @ColumnInfo(name = COLUMN_ID)
    var id: Int,
    @ColumnInfo(name = COLUMN_DESCRIPTION)
    var description: String,
    @ColumnInfo(name = COLUMN_IMAGE)
    var image: String,
    @ColumnInfo(name = COLUMN_LAT)
    var lat: Double,
    @ColumnInfo(name = COLUMN_LNG)
    var lng: Double,
    @ColumnInfo(name = COLUMN_ADDRESS)
    var address: String
) {

    companion object {
        const val TABLE_NAME = "delivery"
        const val COLUMN_ID = "id"
        const val COLUMN_DESCRIPTION = "description"
        const val COLUMN_IMAGE = "image"
        const val COLUMN_LAT = "lat"
        const val COLUMN_LNG = "lng"
        const val COLUMN_ADDRESS = "address"
    }
}