package com.doobie.archpatterns.savedstate

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.doobie.archpatterns.paramsandsavedstate.ParamsAndSavedStateViewModel

/**
 * Created by Alex Doub on 3/28/2020.
 */

class SavedStateViewModel(private val stateHandle: SavedStateHandle) : ViewModel() {

    private val _text = MutableLiveData(clickCount.toString())
    val text: LiveData<String>
        get() = _text

    // Params that need to be stored in state go in the stateHandle, which is put in onSavedInstanceState
    private var clickCount: Int
        get() = stateHandle.get(ParamsAndSavedStateViewModel.KEY_CLICK_COUNT) ?: 100
        set(value) = stateHandle.set(ParamsAndSavedStateViewModel.KEY_CLICK_COUNT, value)

    fun onButtonClicked() {
        clickCount++
        _text.value = clickCount.toString()
    }
}