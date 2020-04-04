package com.doobie.archpatterns.imagelistanddetails

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.SavedStateViewModelFactory
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.doobie.archpatterns.R
import com.doobie.archpatterns.databinding.ListViewActivityBinding

/**
 * Created by Alex Doub on 3/28/2020.
 */

internal open class CardListActivity : AppCompatActivity(), CardListAdapter.IOnCardClickedListener {

    protected lateinit var binding: ListViewActivityBinding
    protected lateinit var viewModel: CardListViewModel

    protected lateinit var cardListAdapter: CardListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        cardListAdapter = CardListAdapter(this) //@@TODO: Move back to inline constructor?

        setupViewModel()
        observeVM()
        viewModel.loadData()

        binding = DataBindingUtil.setContentView(this, R.layout.list_view_activity)
//        binding.viewModel = viewModel
//        binding.lifecycleOwner = this

        binding.recyclerView.layoutManager = GridLayoutManager(this, 3, RecyclerView.VERTICAL, false)
        binding.recyclerView.adapter = cardListAdapter
        binding.swipeRefresh.setOnRefreshListener { viewModel.loadData() }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.list_view_activity_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == R.id.rarities) {
            AlertDialog.Builder(this)
                .setTitle("Rarities")
                .setMessage(viewModel.rarities)
                .setPositiveButton("OK") { _, _ -> }
                .create().show()
            return true
        }
        if (item.itemId == R.id.clear) {
            viewModel.clearData()
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onCardClicked(id: String) {
        //TODO: launch details activity. add repo. details uses repo so it doesnt have to refetch
        //rarities menu item text should go by repo. Or else it would break when data is paged
    }

    protected open fun setupViewModel() {
        viewModel = ViewModelProvider(this, SavedStateViewModelFactory(application, this)).get(CardListViewModel::class.java)
    }

    private fun observeVM() {
        viewModel.listItems.observe(this, Observer {
            cardListAdapter.items = it
        })
        viewModel.toastText.observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        })
        viewModel.isLoading.observe(this, Observer {
            binding.swipeRefresh.isRefreshing = it
        })
    }
}

//A: Fragment direclty handles - best if not doing GSON approach
//B: viewmodel handles -> just to find & pass back to activity == best if doing GSON approach