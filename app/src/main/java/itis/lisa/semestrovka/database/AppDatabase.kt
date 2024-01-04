package itis.lisa.semestrovka.database

import androidx.room.Database
import androidx.room.RoomDatabase
import itis.lisa.semestrovka.basicfeature.data.local.dao.NewsDao
import itis.lisa.semestrovka.basicfeature.data.local.model.NewsCached


private const val DATABASE_VERSION = 1

@Database(
    entities = [NewsCached::class],
    version = DATABASE_VERSION,
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun newsDao(): NewsDao
}
