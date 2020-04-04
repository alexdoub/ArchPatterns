package com.doobie.archpatterns.imagelistanddetails.withdb

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.SavedStateViewModelFactory
import androidx.lifecycle.ViewModelProvider
import com.doobie.archpatterns.imagelistanddetails.CardListActivity
import com.doobie.archpatterns.imagelistanddetails.CardListViewModel
import com.doobie.archpatterns.imagelistanddetails.withdb.repo.CardsRepository

/**
 * Created by Alex Doub on 3/28/2020.
 */

internal class CardListWithDBActivity : CardListActivity() {

    override fun setupViewModel() {
        val sharedPreferences = this.getSharedPreferences("cards_DB", Context.MODE_PRIVATE)
        val repository = CardsRepository(this, sharedPreferences)
        val factory = CardListWithDBViewModel.Factory(this, repository)
        viewModel = ViewModelProvider(this, factory)
            .get(CardListWithDBViewModel::class.java)
    }
}
