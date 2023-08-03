package app.futured.academyproject.ui.screens.home


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import app.futured.academyproject.data.model.local.Place
import app.futured.academyproject.tools.preview.PlacesProvider
import coil.compose.AsyncImage
import kotlinx.collections.immutable.PersistentList

@Composable
private fun PlaceItem(place: String, description: String, image: String?) {
    Row {
        AsyncImage(model = image, contentDescription = "PlaceImage", modifier = Modifier.size(size = 100.dp))
        Column {
            Text(
                text = place,
                style = MaterialTheme.typography.displaySmall
            )
            Text(
                text = description,
                style = MaterialTheme.typography.labelSmall,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth())
        }
    }
}

@Preview
@Composable
private fun PlaceItemPreview(
    @PreviewParameter(PlacesProvider::class) places: PersistentList<Place>
) {
    PlaceItem(place = places[0].name, description = places[0].type, places[0].image1Url)
}

