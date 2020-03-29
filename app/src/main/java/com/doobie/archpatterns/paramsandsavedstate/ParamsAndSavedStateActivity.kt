package com.doobie.archpatterns.paramsandsavedstate

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.doobie.archpatterns.R
import com.doobie.archpatterns.databinding.ParamsAndSavedStateActivityBinding

/**
 * Created by Alex Doub on 3/28/2020.
 *
 * This is an example of how a VM should keep state in regards to an activity lifecycle
 */

class ParamsAndSavedStateActivity : AppCompatActivity() {

    private lateinit var binding: ParamsAndSavedStateActivityBinding
    private lateinit var viewModel: ParamsAndSavedStateViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.params_and_saved_state_activity)
        setupViewModel()
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
    }

    private fun setupViewModel() {
        // Activity passes in args
        val factory = ParamsAndSavedStateViewModel.Factory(this, 100)
        viewModel = ViewModelProvider(this, factory)
            .get(ParamsAndSavedStateViewModel::class.java)
    }
}
