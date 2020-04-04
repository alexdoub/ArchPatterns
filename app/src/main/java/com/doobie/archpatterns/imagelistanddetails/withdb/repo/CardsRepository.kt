package com.doobie.archpatterns.imagelistanddetails.withdb.repo

import android.content.Context
import android.content.SharedPreferences
import com.doobie.archpatterns.imagelistanddetails.api.MagicAPIClient
import com.doobie.archpatterns.imagelistanddetails.api.model.Card
import com.doobie.archpatterns.imagelistanddetails.withdb.entity.CardEntity
import kotlinx.coroutines.flow.Flow
import java.util.concurrent.TimeUnit

/**
 * Created by Alex Doub on 4/4/2020.
 */

class CardsRepository(context: Context, private val sharedPreferences: SharedPreferences) {

    companion object {
        private const val KEY_LAST_SAVED_TIME = "currencies_last_saved_time"
        private val DATA_STALE_DURATION = TimeUnit.MILLISECONDS.convert(30, TimeUnit.MINUTES)
    }

    private val apiClient = MagicAPIClient

    private val currencyDao: CardsDao = CardsDB.getDatabase(context).currencyDao()

    suspend fun fetchCards(): Boolean {

        if (!dataIsStale()) return false

        val response = apiClient.getCards()
        insertCards(response.cards)
        return true
    }

    fun getCards(): Flow<List<CardEntity>> {
        return currencyDao.getCards()
    }

    private suspend fun insertCards(cards: List<Card>) {
        //Transform to db entity
        val entities = cards
            .filter { it.imageUrl != null }
            .map { CardEntity(it.id, it.name, it.imageUrl!!, it.rarity) }

        // Insert data & save timestamp
        currencyDao.insertCards(entities)
        sharedPreferences.edit()
            .putLong(KEY_LAST_SAVED_TIME, System.currentTimeMillis())
            .apply()
    }

    suspend fun deleteCards() {
        currencyDao.deleteCards()
        sharedPreferences.edit()
            .putLong(KEY_LAST_SAVED_TIME, 0L)
            .apply()
    }

    private fun dataIsStale(): Boolean {

//        val lastSaved = sharedPreferences.getLong(KEY_LAST_SAVED_TIME, 0L)
//        val currentTime = System.currentTimeMillis()
//        val difference = currentTime - lastSaved
//
//        return difference > DATA_STALE_DURATION

        return sharedPreferences.getLong(KEY_LAST_SAVED_TIME, 0L) + DATA_STALE_DURATION < System.currentTimeMillis()
    }
}