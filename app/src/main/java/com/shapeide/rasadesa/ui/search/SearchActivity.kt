package com.shapeide.rasadesa.ui.search

import android.app.Activity
import android.app.SearchManager
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.shapeide.rasadesa.BuildConfig.TAG
import com.shapeide.rasadesa.R
import com.shapeide.rasadesa.adapters.SearchAdapter
import com.shapeide.rasadesa.databinding.ActivitySearchBinding
import com.shapeide.rasadesa.domain.Search
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchActivity : AppCompatActivity(), SearchView.OnQueryTextListener, SearchAdapter.Companion.Listener  {
    private lateinit var binding: ActivitySearchBinding
    private lateinit var searchView: SearchView
    private lateinit var rvSearchAdapter: SearchAdapter
    private val searchViewModel : SearchViewModel by viewModels()

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

        searchViewModel.searchHistoryData()
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        intent?.let { parseIntentAction(it) }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.search_menu, menu)
        searchView = menu.findItem(R.id.search).actionView as SearchView
        searchView.setOnQueryTextListener(this)
        searchView.isIconified = false

        /* Configuration of SearchManager for provide event in SearchActivity
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView.setSearchableInfo(searchManager.getSearchableInfo(
            ComponentName(this, SearchActivity::class.java)
        ))
         */

        return true
    }

    private fun parseIntentAction(intent: Intent){
        when(intent.action){
            /* while user still typing the query */
            Intent.ACTION_SEARCH -> {
                val query = intent.getStringExtra(SearchManager.QUERY)
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

    override fun onDelete(search: com.shapeide.rasadesa.domain.Search) {
        Log.d(TAG, "onDelete: Delete been selected")
        searchViewModel.deleteMealSearch(search)
        searchViewModel.searchHistoryData()
    }

    override fun onDetail(search: com.shapeide.rasadesa.domain.Search) {
        Log.d(TAG, "onDetail: List of search been clicked, ${search.text}")
        searchViewModel.addMealSearch(search)

        // Sent back the data into MainActivity
        val intent = Intent().apply {
            putExtra(KEY_ID, search.id)
            putExtra(KEY_SEARCH, search.text)
        }
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    private fun synchronizeSearchEvent(query: String?) : Boolean{
        if(query?.isEmpty() == true) {
            searchViewModel.queryMealSearch(query.toString())
        }else {
            searchViewModel.searchHistoryData()
        }

        return true
    }

    override fun onQueryTextSubmit(p0: String?): Boolean = synchronizeSearchEvent(p0)

    override fun onQueryTextChange(p0: String?): Boolean = synchronizeSearchEvent(p0)

    companion object {
        const val KEY_ID = "ID"
        const val KEY_SEARCH = "SEARCH"
    }
}