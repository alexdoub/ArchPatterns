package com.doobie.archpatterns.lifecycleactivity

import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.lifecycle.*

/**
 * Created by Alex Doub on 3/28/2020.
 */


class LifecycleViewModel(
    private val stateHandle: SavedStateHandle
) : ViewModel() {

    companion object {
        const val KEY_PARAM = "param"
    }

    var param: Int
        get() = stateHandle.get(KEY_PARAM) ?: 0
        set(value) = stateHandle.set(KEY_PARAM, value)

    private val _text = MutableLiveData(param.toString())
    val text: LiveData<String>
        get() = _text

    fun onButtonClicked() {
        param++
        _text.value = param.toString()
    }

    class Factory(
        activity: AppCompatActivity,
        argument: Int
    ) : AbstractSavedStateViewModelFactory(activity, bundleOf(KEY_PARAM to argument)) {

        override fun <T : ViewModel?> create(
            key: String,
            modelClass: Class<T>,
            handle: SavedStateHandle
        ): T {
            return LifecycleViewModel(handle) as T
        }
    }
}
