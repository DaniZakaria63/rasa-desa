package com.shapeide.rasadesa.ui.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.shapeide.rasadesa.BuildConfig.TAG
import com.shapeide.rasadesa.R
import com.shapeide.rasadesa.databinding.ActivityMainBinding
import com.shapeide.rasadesa.ui.fragments.DiscoverFragment
import com.shapeide.rasadesa.ui.dialog.NetworkDialog
import com.shapeide.rasadesa.utills.RasaApplication
import com.shapeide.rasadesa.viewmodels.NetworkStateViewModel

class MainActivity : AppCompatActivity(), DiscoverFragment.CallbackListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var networkStateViewModel: NetworkStateViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        networkStateViewModel = (application as RasaApplication).networkStateViewModel

        val navController = findNavController(R.id.fragmentContainer)
        binding.bottomNav.setupWithNavController(navController)

    }

    override fun onStart() {
        super.onStart()
        networkStateViewModel.networkStateLiveData.observe(this){ isConnected->
            if(!isConnected) NetworkDialog(context = this@MainActivity).show()
        }
    }

    override fun onPause() {
        super.onPause()
        networkStateViewModel.networkStateLiveData.removeObservers(this)
    }

    override fun onNeedIntent(key: String, value: String, name: String) {
        Log.d(TAG, "onNeedIntent: {$key} and {$value} and {$name}")
        val intent = Intent(this, FilterActivity::class.java)
        with(intent) {
            putExtra("key", key)
            putExtra("value", value)
            putExtra("name", name)
        }
        startActivity(intent)
    }

    override fun onDetailMeal(idMeal: String) {
        // display the detail of the meal
        Log.d(TAG, "onDetailMeal: $idMeal")
        startActivity(Intent(this, DetailActivity::class.java))
    }
}