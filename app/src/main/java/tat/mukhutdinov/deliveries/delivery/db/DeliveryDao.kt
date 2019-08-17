package tat.mukhutdinov.deliveries.delivery.db

import androidx.paging.DataSource
import androidx.room.*

@Dao
interface DeliveryDao {

    @Query("SELECT * FROM ${DeliveryEntity.TABLE_NAME}")
    fun getAll(): DataSource.Factory<Int, DeliveryEntity>

    @Query("SELECT * FROM ${DeliveryEntity.TABLE_NAME} WHERE ${DeliveryEntity.COLUMN_ID} = :id")
    suspend fun findById(id: Long): DeliveryEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(delivery: DeliveryEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(deliveries: List<DeliveryEntity>)

    @Update
    suspend fun update(delivery: DeliveryEntity)

    @Delete
    suspend fun delete(delivery: DeliveryEntity)

    @Query("DELETE FROM ${DeliveryEntity.TABLE_NAME}")
    suspend fun clear()
}