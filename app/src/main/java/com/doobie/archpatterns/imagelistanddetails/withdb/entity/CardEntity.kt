package com.doobie.archpatterns.imagelistanddetails.withdb.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * Created by Alex Doub on 4/4/2020.
 */

@Entity(
    tableName = "cards_table",
    indices = [Index(value = ["id"])]
)
class CardEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val imageUrl: String,
    val rarity: String
)
