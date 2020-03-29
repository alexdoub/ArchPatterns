package com.doobie.archpatterns.imagelistanddetails.api

import com.doobie.archpatterns.imagelistanddetails.api.model.CardsResponse
import retrofit2.http.GET

/**
 * Created by Alex Doub on 3/28/2020.
 */

interface MagicAPI {
    @GET("cards")
    suspend fun getCards(): CardsResponse
}