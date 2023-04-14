package com.saie.dogscompose.ui.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.saie.dogscompose.ui.theme.DogsComposeTheme
import timber.log.Timber

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
