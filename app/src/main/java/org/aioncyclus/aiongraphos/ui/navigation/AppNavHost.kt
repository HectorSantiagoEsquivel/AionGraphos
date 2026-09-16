import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import org.aioncyclus.aiongraphos.domain.model.planet.Planet
import org.aioncyclus.aiongraphos.ui.navigation.AppScreen
import org.aioncyclus.aiongraphos.ui.screens.main.MainScreen
import org.aioncyclus.aiongraphos.ui.screens.planetdetail.PlanetDetailScreen
import androidx.hilt.navigation.compose.hiltViewModel
import org.aioncyclus.aiongraphos.ui.screens.main.MainScreenViewModel

import org.aioncyclus.aiongraphos.ui.screens.planetdetail.PlanetDetaiViewModel
import org.aioncyclus.aiongraphos.ui.screens.settings.SettingsScreen
import org.aioncyclus.aiongraphos.ui.screens.settings.SettingsScreenViewModel


@Composable
fun AppNavHost(
    navController: NavHostController
) {

    NavHost(
        navController = navController,
        startDestination = AppScreen.Main.route
    ) {

        composable(AppScreen.Main.route) {

            val vm = hiltViewModel<MainScreenViewModel>()

            MainScreen(
                state = vm.state,
                onPlanetClick = { planet ->
                    navController.navigate(AppScreen.PlanetDetail.planetRoute(planet))
                },
                onSettingsClick = { navController.navigate(AppScreen.Settings.route)},
                onLocationPermissionGranted = vm::load
            )
        }

        composable(AppScreen.PlanetDetail.route) { backStackEntry ->

            val vm = hiltViewModel<PlanetDetaiViewModel>()
            val planet =
                Planet.valueOf(
                    backStackEntry.arguments!!.getString("planet")!!
                )

            LaunchedEffect (planet) {
                vm.load(planet)
            }

            PlanetDetailScreen(
                state = vm.state
            )
        }

        composable(AppScreen.Settings.route){
            val vm = hiltViewModel<SettingsScreenViewModel>()

            SettingsScreen(
                state = vm.state,
                onApplyClick = { chartSettings -> vm.applySettingChanges(chartSettings)
                               navController.navigate(AppScreen.Main.route)},
                onBackClick = { navController.popBackStack()}
            )
        }
    }
}