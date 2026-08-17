package org.aioncyclus.aiongraphos

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import AppNavHost

@Composable
fun AionGraphosApp(

) {
    val navController = rememberNavController()
    AppNavHost(
        navController = navController
    )
}