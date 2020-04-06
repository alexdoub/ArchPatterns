package com.doobie.archpatterns.imagelistanddetails.withdb.repo

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.doobie.archpatterns.imagelistanddetails.withdb.entity.CardEntity

/**
 * Created by Alex Doub on 4/4/2020.
 */

@Database(entities = [CardEntity::class], version = 1)
abstract class CardsDB : RoomDatabase() {

    abstract fun cardsDao(): CardsDao

    companion object {

        // marking the instance as volatile to ensure atomic access to the variable
        @Volatile
        private var INSTANCE: CardsDB? = null

        fun getDatabase(context: Context): CardsDB {
            if (INSTANCE == null) {
                synchronized(CardsDB::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            CardsDB::class.java,
                            "cards.db"
                        )
                            .fallbackToDestructiveMigration()
                            .build()
                    }
                }
            }
            return INSTANCE!!
        }
    }
}
