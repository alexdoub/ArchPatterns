package com.doobie.archpatterns.imagelistanddetails.api.model

internal data class ForeignName(
    val language: String,
    val multiverseid: Int,
    val name: String
)