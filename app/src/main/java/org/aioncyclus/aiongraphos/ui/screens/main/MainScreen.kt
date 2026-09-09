package org.aioncyclus.aiongraphos.ui.screens.main


import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.MaterialTheme

import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text

import androidx.compose.material3.rememberDrawerState

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

import androidx.compose.ui.text.style.TextAlign
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalDensity
import kotlinx.coroutines.launch
import org.aioncyclus.aiongraphos.domain.model.planet.Planet
import org.aioncyclus.aiongraphos.ui.components.aspectdashboard.AspectDashboard
import org.aioncyclus.aiongraphos.ui.components.chart.Chart
import org.aioncyclus.aiongraphos.ui.components.navigation.NavigationBar
import org.aioncyclus.aiongraphos.ui.components.planetdashboard.PlanetDashboard


@Composable
fun MainScreen(
    state: MainScreenUIState,
    onLocationPermissionGranted: () -> Unit,
    onPlanetClick: (Planet) -> Unit
) {
    val context = LocalContext.current

    var locationGranted by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED ||
                    ContextCompat.checkSelfPermission(
                        context,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) == PackageManager.PERMISSION_GRANTED
        )
    }

    val permissionLauncher =
        rememberLauncherForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->

            if (permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true ||
                permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true
            ) {
                locationGranted = true
                onLocationPermissionGranted()
            }
        }

    if (!locationGranted) {
        PermissionRequiredScreen(
            error = "Couldn't load current location\n" +
                    "Please make sure to give Aiongraphos location permissions and try again",
            onRequestPermission = {
                permissionLauncher.launch(
                    arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    )
                )
            }
        )
    } else {
        ChartScreen(
            state = state,
            onPlanetClick = onPlanetClick
        )
    }
}
@Composable
fun PermissionRequiredScreen(
    error: String,
    onRequestPermission: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = error,
                textAlign = TextAlign.Center
            )

            Button(
                onClick = onRequestPermission
            ) {
                Text("Allow location")
            }
        }
    }
}
@Composable
fun ChartScreen(
    state: MainScreenUIState,
    onPlanetClick: (Planet) -> Unit) {
    /*val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE*/

    if (state.isLoading) {
        Box(
            modifier = Modifier.aspectRatio(1F),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }
    else if(state.error!=null)
    {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = state.error,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
    else if (state.planetDashboardUIState != null && state.chartUIState!=null) {
        val dashboardColumns = /*if (isLandscape) 5 else*/ 2

        val drawerState = rememberDrawerState(
            initialValue = DrawerValue.Closed
        )

        val scope = rememberCoroutineScope()

        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
                ModalDrawerSheet(
                    modifier = Modifier.fillMaxWidth(0.66F),
                    drawerContainerColor = MaterialTheme.colorScheme.background.copy(alpha = 0.75f)
                ) {
                    NavigationDrawerItem(
                        label = { Text(text = "Location") },
                        selected = false,
                        onClick = { /*TODO*/ }
                    )
                }
            }
        )
        {
            Scaffold(
                topBar = {
                    NavigationBar(
                        title = state.title,
                        onMenuClick = {
                            scope.launch {
                                drawerState.apply {
                                    if (isClosed) open() else close()
                                }
                            }
                        }

                    )
                }
            ) { innerPadding ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                ) {

                    Box(
                        modifier = Modifier.fillMaxSize()
                    ) {

                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            flingBehavior = rememberSnapFlingBehavior(
                                lazyListState = rememberLazyListState()
                            )
                        ) {
                            item {
                                Column(
                                    modifier = Modifier
                                        .fillParentMaxHeight()
                                        .fillMaxWidth()
                                ) {
                                    Chart(
                                        chartUIState = state.chartUIState,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .weight(1.5f)
                                    )

                                    AspectDashboard(
                                        aspects = state.chartUIState.aspects,
                                        columns = 5,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .weight(1f)
                                    )
                                }
                            }

                            item {
                                PlanetDashboard(
                                    state = state.planetDashboardUIState,
                                    columns = dashboardColumns,
                                    onPlanetClick = onPlanetClick,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .fillParentMaxHeight()
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
