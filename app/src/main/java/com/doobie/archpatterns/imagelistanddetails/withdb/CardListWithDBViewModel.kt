package com.doobie.archpatterns.imagelistanddetails.withdb

import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.doobie.archpatterns.imagelistanddetails.CardListViewModel
import com.doobie.archpatterns.imagelistanddetails.api.model.Card
import com.doobie.archpatterns.imagelistanddetails.withdb.entity.CardEntity
import com.doobie.archpatterns.imagelistanddetails.withdb.repo.CardsRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 * Created by Alex Doub on 3/28/2020.
 */

internal open class CardListWithDBViewModel(
    private val stateHandle: SavedStateHandle,
    private val repository: CardsRepository
) : CardListViewModel() {

    init {
        observeRepo()
    }

    override fun loadData() {
        if (_isLoading.value!!) return
        _isLoading.value = true

        viewModelScope.launch {
            val success = repository.fetchCards()

            if (success) {
                _toastText.value = "Did fetch cards from API"
            } else {
                _toastText.value = "Did NOT fetch cards from API"
            }

            _isLoading.value = false
        }
    }

    override fun clearData() {
        viewModelScope.launch {
            repository.deleteCards()
            _toastText.postValue("Data Cleared (In DB)")
        }
    }

    private fun observeRepo() {
        viewModelScope.launch {
            repository.getCards().collect {
                setData(it.toCardList())
            }
        }
    }

    private fun List<CardEntity>.toCardList(): List<Card> {
        return map { Card(it.id, it.name, it.imageUrl, it.rarity) }
    }

    class Factory(
        activity: AppCompatActivity,
        private val repository: CardsRepository
    ) : AbstractSavedStateViewModelFactory(activity, bundleOf()) {
        override fun <T : ViewModel?> create(
            key: String,
            modelClass: Class<T>,
            handle: SavedStateHandle
        ): T {
            return CardListWithDBViewModel(handle, repository) as T
        }
    }
}