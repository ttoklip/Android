package com.umc.ttoklip.data.db

import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.RoomDatabase

@Database(
    entities = [HistoryEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun historyDao(): HistoryDao
}

@Dao
interface HistoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addHistory(item: HistoryEntity)

    @Query("SELECT * FROM HistoryEntity")
    suspend fun getAll(): List<HistoryEntity>

    @Query("DELETE FROM HistoryEntity")
    suspend fun deleteAll()
}

@Entity
data class HistoryEntity(
    @PrimaryKey(autoGenerate = true) val id: Int ,
    val history: String
) {
    companion object {
        
    }
}