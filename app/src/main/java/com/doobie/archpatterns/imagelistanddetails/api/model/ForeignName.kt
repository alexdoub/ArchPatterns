package com.doobie.archpatterns.imagelistanddetails.api.model

data class ForeignName(
    val language: String,
    val multiverseid: Int,
    val name: String
)