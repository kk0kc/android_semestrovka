package itis.lisa.semestrovka.basicfeature.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import itis.lisa.semestrovka.basicfeature.data.local.model.NewsCached
import kotlinx.coroutines.flow.Flow


@Dao
interface NewsDao {

    @Query("SELECT * FROM NewsCached")
    fun getNews(): Flow<List<NewsCached>>

    @Upsert
    suspend fun saveNews(news: List<NewsCached>)
}
