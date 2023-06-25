package com.shapeide.rasadesa.ui.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.shapeide.rasadesa.BuildConfig.TAG
import com.shapeide.rasadesa.R
import com.shapeide.rasadesa.databinding.ActivityMainBinding
import com.shapeide.rasadesa.ui.fragments.DiscoverFragment
import com.shapeide.rasadesa.ui.dialog.NetworkDialog
import com.shapeide.rasadesa.ui.fragments.DetailFragment
import com.shapeide.rasadesa.ui.listener.HomeSearchListener
import com.shapeide.rasadesa.ui.listener.MealDetailListener
import com.shapeide.rasadesa.utills.RasaApplication
import com.shapeide.rasadesa.viewmodels.NetworkStateViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), MealDetailListener, HomeSearchListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var networkStateViewModel: NetworkStateViewModel
    private lateinit var launcher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        networkStateViewModel = (application as RasaApplication).networkStateViewModel

        val navController = findNavController(R.id.fragmentContainer)
        binding.bottomNav.setupWithNavController(navController)

        launcher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                Log.i(TAG, "onSearchClicked: ${data?.extras}")
            } else {
                Log.i(TAG, "onCreate: What's wrong? this is else branch")
            }
        }
    }

    override fun onStart() {
        super.onStart()
        networkStateViewModel.networkStateLiveData.observe(this) { isConnected ->
            if (!isConnected) NetworkDialog(context = this@MainActivity).show()
        }
    }

    override fun onPause() {
        super.onPause()
        networkStateViewModel.networkStateLiveData.removeObservers(this)
    }

    override fun onNeedIntent(key: String, value: String, name: String) {
        Log.d(TAG, "onNeedIntent: {$key} and {$value} and {$name}")
        val intent = Intent(this, FilterActivity::class.java).apply {
            putExtra("key", key)
            putExtra("value", value)
            putExtra("name", name)
        }
        startActivity(intent)
    }

    override fun onDetailMeal(type: String, idMeal: Int) {
        // display the detail of the meal
        Log.d(TAG, "onDetailMeal: $idMeal")
        val intent = Intent(this, DetailActivity::class.java).apply {
            putExtra(DetailFragment.ARG_TYPE, type)
            putExtra(DetailFragment.ARG_IDMEAL, idMeal)
        }
        startActivity(intent)
    }

    override fun onSearchClicked() {
        launcher.launch(Intent(this, SearchActivity::class.java))
    }
}