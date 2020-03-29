package com.doobie.archpatterns.paramsandsavedstate

import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.lifecycle.*

/**
 * Created by Alex Doub on 3/28/2020.
 */


class ParamsAndSavedStateViewModel(
    private val stateHandle: SavedStateHandle
) : ViewModel() {

    companion object {
        const val KEY_CLICK_COUNT = "click_count"
    }

    class Factory(
        activity: AppCompatActivity,
        argument: Int
    ) : AbstractSavedStateViewModelFactory(activity, bundleOf(KEY_CLICK_COUNT to argument)) {
        override fun <T : ViewModel?> create(
            key: String,
            modelClass: Class<T>,
            handle: SavedStateHandle
        ): T {
            return ParamsAndSavedStateViewModel(handle) as T
        }
    }

    // Params that need to be stored in state go in the stateHandle, which is put in onSavedInstanceState
    private var clickCount: Int
        get() = stateHandle.get(KEY_CLICK_COUNT)!!
        set(value) = stateHandle.set(KEY_CLICK_COUNT, value)

    private val _text = MutableLiveData(clickCount.toString())
    val text: LiveData<String>
        get() = _text

    fun onButtonClicked() {
        clickCount++
        _text.value = clickCount.toString()
    }
}
