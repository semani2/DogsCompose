package com.saie.dogscompose.ui.main

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.saie.dogscompose.data.Breed
import com.saie.dogscompose.ui.theme.DogsComposeTheme
import timber.log.Timber
import com.saie.dogscompose.R

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
            Column {

                Text(
                    modifier = Modifier.padding(top = 16.dp, start = 16.dp),
                    text = stringResource(R.string.title),
                    style = MaterialTheme.typography.h1
                )

                Text(
                    modifier = Modifier.padding(top = 10.dp, start = 16.dp, bottom = 16.dp),
                    text = stringResource(R.string.caption),
                    style = MaterialTheme.typography.caption
                )

                val dogBreeds by mainViewModel.breedList.collectAsState(initial = emptyList())
                LazyVerticalGrid(columns = GridCells.Adaptive(minSize = 200.dp)) {
                    items(dogBreeds) { breed ->
                        Breed(breed, selectBreed)
                    }
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
