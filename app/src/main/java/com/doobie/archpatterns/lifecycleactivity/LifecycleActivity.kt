package com.doobie.archpatterns.lifecycleactivity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.doobie.archpatterns.R
import com.doobie.archpatterns.databinding.LifecycleActivityBinding

/**
 * Created by Alex Doub on 3/28/2020.
 */

class LifecycleActivity : AppCompatActivity() {

    private lateinit var binding: LifecycleActivityBinding
    private lateinit var viewModel: LifecycleViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.lifecycle_activity)
        setupViewModel()
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
    }

    fun setupViewModel() {
        val factory = LifecycleViewModel.Factory(this, 150)
        viewModel = ViewModelProvider(this, factory)
            .get(LifecycleViewModel::class.java)
    }
}
