package app.futured.academyproject.ui.screens.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import app.futured.academyproject.data.model.local.Place
import app.futured.academyproject.navigation.NavigationDestinations
import app.futured.academyproject.tools.arch.EventsEffect
import app.futured.academyproject.tools.arch.onEvent
import app.futured.academyproject.tools.compose.ScreenPreviews
import app.futured.academyproject.ui.components.Showcase
import app.futured.academyproject.tools.preview.PlacesDummyData
import coil.compose.AsyncImage

@Composable
fun DetailScreen(
    navigation: NavigationDestinations,
    viewModel: DetailViewModel = hiltViewModel(),
) {
    with(viewModel) {
        EventsEffect {
            onEvent<NavigateBackEvent> {
                navigation.popBackStack()
            }
        }

        Detail.Content(
            this,
            viewState.place,
        )
    }
}

object Detail {

    interface Actions {
        fun navigateBack() = Unit
    }

    object PreviewActions : Actions

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun Content(
        actions: Actions,
        place: Place?,
        modifier: Modifier = Modifier,
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = "Detaily") },
                    navigationIcon = {
                        IconButton(onClick = { actions.navigateBack() }) {
                            Icon(Icons.Filled.ArrowBack, contentDescription = null)
                        }
                    },
                )
            },
            modifier = modifier
        ) { contentPadding ->
            place?.let {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .padding(contentPadding)
                        .fillMaxSize(),
                ) {
                    Text(
                        text = place.name,
                        style = MaterialTheme.typography.headlineLarge,
                        modifier = Modifier.padding(16.dp)
                    )
                    AsyncImage(
                        model = place.image1Url,
                        contentDescription = "",
                        modifier = Modifier
                            .size(200.dp)
                            .fillMaxWidth())
                    place.webUrl?.let {
                            Text(
                                text = "Více informací najdete na:",
                                modifier = Modifier.padding(24.dp),
                                style = MaterialTheme.typography.headlineMedium
                            )
                            Text(
                                text = place.webUrl,
                                style = MaterialTheme.typography.headlineSmall,
                                modifier = Modifier.padding(20.dp),
                                textAlign = TextAlign.Center)
                    }
                    Text(
                        text = "Kontakt:",
                        modifier = Modifier.padding(12.dp),
                        style = MaterialTheme.typography.headlineMedium
                    )
                    Column() {
                        place.email?.let {
                            Text(
                                text = "Email:      ${place.email}",
                                modifier = Modifier.padding(12.dp),
                                style = MaterialTheme.typography.bodyLarge
                                )
                        }
                        place.phone?.let {
                            Text(
                                text = "Telefon:    ${place.phone}",
                                modifier = Modifier.padding(12.dp),
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                    }
                }
            }
        }
    }
}


@ScreenPreviews
@Composable
fun DetailContentPreview() {
    Showcase {
        Detail.Content(
            Detail.PreviewActions,
            place = PlacesDummyData.places[0],
        )
    }
}
