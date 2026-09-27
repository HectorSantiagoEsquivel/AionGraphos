package org.aioncyclus.aiongraphos

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import org.aioncyclus.aiongraphos.ui.navigation.AppNavHost
@ExperimentalMaterial3Api
@Composable
fun AionGraphosApp(

) {
    val navController = rememberNavController()
    AppNavHost(
        navController = navController
    )
}