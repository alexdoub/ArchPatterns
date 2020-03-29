package com.doobie.archpatterns.savedstate

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.SavedStateViewModelFactory
import androidx.lifecycle.ViewModelProvider
import com.doobie.archpatterns.R
import com.doobie.archpatterns.databinding.SavedStateActivityBinding

/**
 * Created by Alex Doub on 3/28/2020.
 */

class SavedStateActivity : AppCompatActivity() {
    private lateinit var binding: SavedStateActivityBinding
    private lateinit var viewModel: SavedStateViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.saved_state_activity)
        setupViewModel()
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
    }

    private fun setupViewModel() {
        // Activity passes in zero args
        viewModel = ViewModelProvider(this, SavedStateViewModelFactory(application, this))
            .get(SavedStateViewModel::class.java)
    }
}