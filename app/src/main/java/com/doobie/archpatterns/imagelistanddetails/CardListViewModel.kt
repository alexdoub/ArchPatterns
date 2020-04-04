package com.doobie.archpatterns.imagelistanddetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.doobie.archpatterns.imagelistanddetails.api.MagicAPIClient
import com.doobie.archpatterns.imagelistanddetails.api.model.Card
import com.doobie.archpatterns.imagelistanddetails.api.model.CardsResponse
import com.doobie.archpatterns.imagelistanddetails.model.CardListItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Created by Alex Doub on 3/28/2020.
 */

internal open class CardListViewModel : ViewModel() {

    protected  val _listItems = MutableLiveData<List<CardListItem>>()
    val listItems: LiveData<List<CardListItem>>
        get() = _listItems

    protected  val _isLoading = MutableLiveData<Boolean>(false)
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    protected val _toastText = MutableLiveData<String>()
    val toastText: LiveData<String>
        get() = _toastText

    var rarities: String = "None"
        private set

    private val apiClient = MagicAPIClient

    open fun loadData() {
        if (_isLoading.value!!) return

        _isLoading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = apiClient.getCards()
                withContext(Dispatchers.Main) {
                    setData(response.cards)
                }
            } catch (t: Throwable) {
                _toastText.postValue("Error loading data: ${t.message}")
            }
        }.invokeOnCompletion {
            _isLoading.postValue(false)
        }
    }

    protected fun setData(cards: List<Card>) {
        _listItems.value = cards.toListItems()
        val grouped = cards.groupBy { it.rarity }.mapValues { it.value.size }

        rarities = if (cards.isEmpty()) {
            "No data"
        } else {
            grouped.entries.joinToString(transform = { "${it.key}: ${it.value}" }, separator = ",\n")
        }
    }

    open fun clearData() {
        setData(emptyList())
        _toastText.postValue("Data Cleared (In memory only)")
    }

    private fun List<Card>.toListItems(): List<CardListItem> {
        return mapNotNull {
            // Filter bad data
            if (it.imageUrl == null) {
                null
            } else {
                CardListItem(it.id, it.imageUrl, it.name)
            }
        }
    }
}