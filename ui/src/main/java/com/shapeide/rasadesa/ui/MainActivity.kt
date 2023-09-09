package com.shapeide.rasadesa.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.shapeide.rasadesa.ui.navigation.RasaNavHost
import com.shapeide.rasadesa.ui.navigation.base.BaseDestination
import com.shapeide.rasadesa.ui.navigation.base.BaseNavigator
import com.shapeide.rasadesa.ui.theme.RasaDesaTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            RasaDesaTheme {
                RasaNavHost(navController = navController)
            }
        }
    }
}