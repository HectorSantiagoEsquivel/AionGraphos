package org.aioncyclus.aiongraphos.ui.screens.main

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults.Indicator
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.core.content.ContextCompat
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import org.aioncyclus.aiongraphos.domain.model.planet.Planet
import org.aioncyclus.aiongraphos.ui.components.aspectdashboard.AspectDashboard
import org.aioncyclus.aiongraphos.ui.components.chart.Chart
import org.aioncyclus.aiongraphos.ui.components.navigation.NavigationBar
import org.aioncyclus.aiongraphos.ui.components.planetdashboard.PlanetDashboard
import java.time.Duration
import java.time.Instant
import java.time.temporal.ChronoUnit

@ExperimentalMaterial3Api
@Composable
fun MainScreen(
    state: MainScreenUIState,
    onLocationPermissionGranted: () -> Unit,
    onPlanetClick: (Planet) -> Unit,
    onSettingsClick: () -> Unit,
    onRefresh: () -> Unit
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
            state=state,
            onPlanetClick=onPlanetClick,
            onSettingsClick=onSettingsClick,
            onRefresh=onRefresh
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

@ExperimentalMaterial3Api
@Composable
fun ChartScreen(
    state: MainScreenUIState,
    onPlanetClick: (Planet) -> Unit,
    onSettingsClick: () -> Unit,
    onRefresh: () -> Unit) {

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
        val dashboardColumns = 2
        var chartAge by remember { mutableStateOf(Duration.ZERO) }

        LaunchedEffect(state.chartInstant) {
            while (true) {
                val remaining = Duration.between(
                    Instant.now(),
                    state.chartInstant.plus(30, ChronoUnit.MINUTES)
                )
                chartAge = Duration.between(state.chartInstant, Instant.now())
                if (remaining.isZero || remaining.isNegative) {
                    onRefresh()
                    break
                }
                delay(minOf(remaining.toMillis(), 60000))
            }
        }

        Scaffold(
            topBar = {
                NavigationBar(
                    title = state.title,
                    onMenuClick = onSettingsClick
                )
            }
        ) { innerPadding ->
            val pullToRefreshState = rememberPullToRefreshState()

            PullToRefreshBox(
                isRefreshing = state.isRefreshing,
                onRefresh = {onRefresh()},
                state = pullToRefreshState,
                indicator = @Composable {
                    Indicator(
                        modifier = Modifier.align(Alignment.TopCenter),
                        isRefreshing = state.isRefreshing,
                        state = pullToRefreshState,
                        color = MaterialTheme.colorScheme.tertiary,
                        containerColor = MaterialTheme.colorScheme.onTertiary
                    )
                },
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {

                val lazyListState = rememberLazyListState()

                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    state = lazyListState,
                    flingBehavior = rememberSnapFlingBehavior(
                        lazyListState = lazyListState
                    )
                ) {
                    item {
                        Column(
                            modifier = Modifier
                                .fillParentMaxHeight()
                                .fillMaxWidth()
                        ) {
                            Row(modifier= Modifier
                                .weight(0.1F))
                            {
                                val ageMinutes = chartAge.toMinutes()
                                if(ageMinutes >= 1)
                                {
                                    Spacer(Modifier.width(20.dp))
                                    Icon(
                                        imageVector = Icons.Default.Refresh,
                                        tint = MaterialTheme.colorScheme.tertiary,
                                        contentDescription = null
                                    )
                                    Spacer(Modifier.width(4.dp))
                                    Text(text =when {
                                        ageMinutes < 60 -> "$ageMinutes MIN"
                                        else -> "${chartAge.toHours()} HR"
                                    })
                                }
                            }

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


