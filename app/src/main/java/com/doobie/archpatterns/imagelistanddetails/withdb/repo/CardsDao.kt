package com.doobie.archpatterns.imagelistanddetails.withdb.repo

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.doobie.archpatterns.imagelistanddetails.withdb.entity.CardEntity
import kotlinx.coroutines.flow.Flow

/**
 * Created by Alex Doub on 4/4/2020.
 */

@Dao
interface CardsDao {

    @Query("SELECT * from cards_table")
    fun getCards(): Flow<List<CardEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCards(entity: List<CardEntity>)

    @Query("DELETE FROM cards_table")
    suspend fun deleteCards()
}
