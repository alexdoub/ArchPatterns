package com.doobie.archpatterns.imagelistanddetails.api

import com.doobie.archpatterns.imagelistanddetails.api.model.CardsResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by Alex Doub on 3/28/2020.
 */

internal object MagicAPIClient {
    private val magicAPI: MagicAPI

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.magicthegathering.io/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        magicAPI = retrofit.create(MagicAPI::class.java)
    }

    suspend fun getCards(): CardsResponse {
        return magicAPI.getCards()
    }
}