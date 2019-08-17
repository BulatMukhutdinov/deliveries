package tat.mukhutdinov.deliveries.infrastructure.db

import androidx.room.Database
import androidx.room.RoomDatabase
import tat.mukhutdinov.deliveries.delivery.db.DeliveryDao
import tat.mukhutdinov.deliveries.delivery.db.DeliveryEntity

@Database(
    entities = [
        DeliveryEntity::class
    ],
    version = 3,
    exportSchema = false)
abstract class DataBase : RoomDatabase() {

    abstract fun deliveryDao(): DeliveryDao
}