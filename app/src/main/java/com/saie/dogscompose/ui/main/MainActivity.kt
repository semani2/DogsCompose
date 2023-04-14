package com.saie.dogscompose.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import coil.compose.AsyncImage
import com.saie.dogscompose.data.Breed
import com.saie.dogscompose.ui.theme.DogsComposeTheme
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import com.saie.dogscompose.ui.detail.DetailViewModel

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

            BreedDetails(
                breedId = breedId,
                viewModel = hiltViewModel()
            ) {
                navController.navigateUp()
            }
        }
    }
}

@Composable
fun Breeds(
    mainViewModel: MainViewModel,
    selectBreed: (Int) -> Unit
) {
    DogsComposeTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            val dogBreeds by mainViewModel.breedList.collectAsState(initial = emptyList())
            LazyVerticalGrid(columns = GridCells.Adaptive(minSize = 200.dp)) {
                items(dogBreeds) { breed ->
                    Breed(breed, selectBreed)
                }
            }
        }
    }
}

@Composable
fun Breed(breed: Breed, selectBreed: (Int) -> Unit) {
    Card (
        backgroundColor = MaterialTheme.colors.surface,
        modifier = Modifier
            .padding(vertical = 4.dp, horizontal = 8.dp)
            .width(200.dp)
            .height(250.dp)
            .clickable(onClick = { selectBreed(breed.id) })
    ) {
        CardContent(breed = breed)
    }
}

@Composable
fun CardContent(breed: Breed) {
    Column(
        modifier = Modifier.padding(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        AsyncImage(
            model = "https://cdn2.thedogapi.com/images/${breed.imageId}.jpg",
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .clip(CircleShape)
                .width(150.dp)
                .height(150.dp),
            onError = { Timber.e(it.toString()) }
        )
        Text(
            modifier = Modifier.padding(top = 16.dp),
            text = breed.name,
            style = MaterialTheme.typography.h3,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun BreedDetails(breedId: Int,
                 viewModel: DetailViewModel,
                 pressOnBack: () -> Unit = {}
) {
    DogsComposeTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
        ) {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .background(MaterialTheme.colors.surface)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                LaunchedEffect(key1 = breedId) {
                    viewModel.loadBreedById(breedId)
                }

                val breed by viewModel.breedDetailFlow.collectAsState(initial = null)

                breed?.let { it ->
                    Column(
                        modifier = Modifier.padding(12.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,

                        ) {
                        AsyncImage(
                            model = "https://cdn2.thedogapi.com/images/${it.imageId}.jpg",
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(350.dp)
                                .clip(RoundedCornerShape(10.dp)),
                            onError = { Timber.e(it.toString()) }
                        )
                        Text(
                            modifier = Modifier.padding(top = 16.dp),
                            text = it.name,
                            style = MaterialTheme.typography.h2
                        )

                        if (it.origin != null && it.origin.isNotEmpty()) {
                            Row(
                                modifier = Modifier.padding(top = 16.dp)
                            ) {
                                Text(
                                    text = "Origin: ",
                                    style = MaterialTheme.typography.body1
                                )

                                Text(
                                    modifier = Modifier.padding(start = 2.dp),
                                    text = it.origin,
                                    style = MaterialTheme.typography.body2
                                )
                            }
                        }

                        Row(
                            modifier = Modifier.padding(top = 16.dp)
                        ) {
                            Text(
                                text = "Life span: ",
                                style = MaterialTheme.typography.body1
                            )

                            Text(
                                modifier = Modifier.padding(start = 2.dp),
                                text = it.lifeSpan,
                                style = MaterialTheme.typography.body2
                            )
                        }

                    }
                }
            }
        }
    }
}

sealed class NavScreen(val route: String) {

    object Home : NavScreen("Home")

    object BreedDetails : NavScreen("BreedDetails") {

        const val routeWithArgument: String = "BreedDetails/{breedId}"

        const val argument0: String = "breedId"
    }
}
