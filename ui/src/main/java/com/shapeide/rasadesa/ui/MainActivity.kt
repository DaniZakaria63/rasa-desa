package com.shapeide.rasadesa.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.shapeide.rasadesa.domain.domain.MealType
import com.shapeide.rasadesa.presenter.base.Status
import com.shapeide.rasadesa.presenter.main.viewmodel.MainViewModel
import com.shapeide.rasadesa.ui.home.HomeScreen
import com.shapeide.rasadesa.ui.theme.RasaDesaTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch(Dispatchers.Main) {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.getRecipesByMealType(MealType.Breakfast)
                viewModel.recipeState.collect {
                    Timber.d("Data: ${it.recipeList}")
                    when (it.status) {
                        Status.ERROR -> Timber.e("Data: Something Error")
                        Status.DATA -> Timber.d("Data: ${it.recipeList}")
                        Status.LOADING -> Timber.d("Still Loading")
                    }
                }
            }
        }

        setContent {
            RasaDesaTheme {
                HomeScreen()
            }
        }
    }
}