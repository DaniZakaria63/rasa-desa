package com.shapeide.rasadesa.ui.activities

import android.app.SearchManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.shapeide.rasadesa.BuildConfig.TAG
import com.shapeide.rasadesa.R
import com.shapeide.rasadesa.adapters.SearchAdapter
import com.shapeide.rasadesa.databinding.ActivitySearchBinding
import com.shapeide.rasadesa.domains.Search
import com.shapeide.rasadesa.ui.fragments.DetailFragment
import com.shapeide.rasadesa.viewmodels.SearchVM

class SearchActivity : AppCompatActivity(), SearchView.OnQueryTextListener, SearchAdapter.Companion.Listener  {
    private lateinit var binding: ActivitySearchBinding
    private lateinit var searchView: SearchView
    private lateinit var rvSearchAdapter: SearchAdapter
    private val searchViewModel: SearchVM by lazy {
        application.let {
            ViewModelProvider.AndroidViewModelFactory.getInstance(it)
                .create(SearchVM::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        parseIntentAction(intent)
        rvSearchAdapter = SearchAdapter(this)
        binding.searchList.adapter = rvSearchAdapter
        binding.searchList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.searchList.isNestedScrollingEnabled = false

        searchViewModel.mealSearchData.observe(this){ searchList ->
            rvSearchAdapter.submitList(searchList)
        }

        searchViewModel.errorMessage.observe(this){ err ->
            Toast.makeText(application, err, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        intent?.let { parseIntentAction(it) }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.search_menu, menu)
        searchView = menu.findItem(R.id.search).actionView as SearchView
        searchView.setOnQueryTextListener(this)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView.setSearchableInfo(searchManager.getSearchableInfo(
            ComponentName(this, SearchActivity::class.java)
        ))
        searchView.isIconified = false

        return true
    }

    private fun parseIntentAction(intent: Intent){
        when(intent.action){
            /* while user still typing the query */
            Intent.ACTION_SEARCH -> {
                val query = intent.getStringExtra(SearchManager.QUERY)
                searchViewModel.queryMealSearch(query.toString())
                Log.i(TAG, "Searching Query: $query")
            }

            /* while user selected the result */
            Intent.ACTION_VIEW -> {
                val uri = intent.dataString
                Log.i(TAG, "Selected Query: $uri")
            }
            else -> {
                Log.i(TAG, "Intent Branch: else")
            }
        }
    }

    override fun onDelete(search: Search) {
        searchViewModel.deleteMealSearch(search)
    }

    override fun onDetail(search: Search) {
        searchViewModel.addMealSearch(search)
        // Sent back the data into MainActivity
    }

    override fun onQueryTextSubmit(p0: String?): Boolean = false

    override fun onQueryTextChange(p0: String?): Boolean = false
}