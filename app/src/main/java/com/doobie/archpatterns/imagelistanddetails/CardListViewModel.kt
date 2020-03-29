package com.doobie.archpatterns.imagelistanddetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.doobie.archpatterns.imagelistanddetails.api.MagicAPIClient
import com.doobie.archpatterns.imagelistanddetails.api.model.CardsResponse
import com.doobie.archpatterns.imagelistanddetails.model.CardListItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Created by Alex Doub on 3/28/2020.
 */

class CardListViewModel : ViewModel() {

    private val _listItems = MutableLiveData<List<CardListItem>>()
    val listItems: LiveData<List<CardListItem>>
        get() = _listItems

    private val _isLoading = MutableLiveData<Boolean>(false)
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    private val _toastText = MutableLiveData<String>()
    val toastText: LiveData<String>
        get() = _toastText

    var rarities: String = "None"
        private set

    private val apiClient = MagicAPIClient

    init {
        loadData()
    }

    fun loadData() {
        if (_isLoading.value!!) return

        _isLoading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = apiClient.getCards()
                withContext(Dispatchers.Main) {
                    setData(response)
                }
            } catch (t: Throwable) {
                _toastText.postValue("Error loading data: ${t.message}")
            }
        }.invokeOnCompletion {
            _isLoading.postValue(false)
        }
    }

    private fun setData(response: CardsResponse) {
        _listItems.value = response.toListItems()
        val grouped = response.cards.groupBy { it.rarity }.mapValues { it.value.size }
        rarities = grouped.entries.joinToString(transform = { "${it.key}: ${it.value}" }, separator = ",\n")
    }

    private fun CardsResponse.toListItems(): List<CardListItem> {
        return cards.mapNotNull {
            // Filter bad data
            if (it.imageUrl == null) {
                null
            } else {
                CardListItem(it.id, it.imageUrl, it.name)
            }
        }
    }
}