package edu.nd.pmcburne.hello.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface LocationDao {
    @Query("SELECT * FROM locations")
    suspend fun getAll(): List<LocationEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(locations: List<LocationEntity>)
}