package com.saie.dogscompose.ui.main

sealed class NavScreen(val route: String) {

    object Home : NavScreen("Home")

    object BreedDetails : NavScreen("BreedDetails") {

        const val routeWithArgument: String = "BreedDetails/{breedId}"

        const val argument0: String = "breedId"
    }
}
