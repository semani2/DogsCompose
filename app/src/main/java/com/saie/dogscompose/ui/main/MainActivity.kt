package com.saie.dogscompose.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            App()
        }
    }
}

@Composable
fun App(
    mainViewModel: MainViewModel = hiltViewModel()
) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = NavScreen.Home.route) {
        composable(NavScreen.Home.route) {
            Breeds(
                mainViewModel,
                selectBreed = {
                    navController.navigate("${NavScreen.BreedDetails.route}/$it")
                }
            )

            LaunchedEffect(Unit) {
                Timber.d("Navigating to home")
            }
        }

        composable(
            route = NavScreen.BreedDetails.routeWithArgument,
            arguments = listOf(navArgument(NavScreen.BreedDetails.argument0) { type = NavType.IntType}
            )
        ) { backStackEntry ->
            val breedId = backStackEntry.arguments?.getInt(NavScreen.BreedDetails.argument0)
                ?: return@composable

            com.saie.dogscompose.ui.detail.BreedDetails(
                breedId = breedId,
                viewModel = hiltViewModel()
            ) {
                navController.navigateUp()
            }
        }
    }
}
