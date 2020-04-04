package com.doobie.archpatterns.imagelistanddetails.api.model

internal data class Card(
    val id: String,
    val name: String,
    val imageUrl: String?,
    val rarity: String
//    ,
//    val artist: String,
//    val cmc: Int,
//    val colorIdentity: List<String>,
//    val colors: List<String>,
//    val foreignNames: List<ForeignName>,
//    val layout: String,
//    val manaCost: String,
//    val multiverseid: Int,
//    val names: List<String>,
//    val number: String,
//    val originalText: String,
//    val originalType: String,
//    val power: String,
//    val printings: List<String>,
//    val `set`: String,
//    val subtypes: List<String>,
//    val supertypes: List<String>,
//    val text: String,
//    val toughness: String,
//    val type: String,
//    val types: List<String>
)