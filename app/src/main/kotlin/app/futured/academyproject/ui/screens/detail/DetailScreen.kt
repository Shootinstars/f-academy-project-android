package app.futured.academyproject.ui.screens.detail

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import app.futured.academyproject.data.model.local.Place
import app.futured.academyproject.navigation.NavigationDestinations
import app.futured.academyproject.tools.arch.EventsEffect
import app.futured.academyproject.tools.arch.onEvent
import app.futured.academyproject.tools.compose.ScreenPreviews
import app.futured.academyproject.ui.components.Showcase
import coil.compose.AsyncImage
import androidx.compose.foundation.text.ClickableText
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.sp

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
        fun navigateBack()
        fun onFavorite()
    }

    object PreviewActions : Actions {
        override fun navigateBack() {}
        override fun onFavorite() {}
    }

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
                    actions = {
                        val (iconRes, iconColor) = if (place?.isFavourite == true) {
                            Icons.Filled.Favorite to MaterialTheme.colorScheme.error
                        } else {
                            Icons.Filled.FavoriteBorder to MaterialTheme.colorScheme.onSurface
                        }

                        IconButton(onClick = actions::onFavorite) {
                            Icon(
                                imageVector = iconRes,
                                tint = iconColor,
                                contentDescription = null,
                            )
                        }
                    },
                    navigationIcon = {
                        IconButton(onClick = { actions.navigateBack() }) {
                            Icon(Icons.Filled.ArrowBack, contentDescription = null)
                        }
                    },
                )
            },
            modifier = modifier,
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
                            .fillMaxWidth()
                    )
                    place.webUrl?.let {
                        Text(
                            text = "Více informací najdete na:",
                            modifier = Modifier.padding(24.dp),
                            style = MaterialTheme.typography.headlineMedium
                        )
                        val localUriHandler = LocalUriHandler.current
                        ClickableText(
                            modifier = Modifier.padding(8.dp),
                            text = AnnotatedString(
                                place.webUrl,
                                spanStyle = SpanStyle(
                                    color = MaterialTheme.colorScheme.primary,
                                    fontSize = 20.sp,
                                    textDecoration = TextDecoration.Underline
                                )
                            ),
                            onClick = {
                                val url = if (place.webUrl.startsWith("http") || place.webUrl.startsWith("https")) {
                                    place.webUrl
                                } else {
                                    "https://${place.webUrl}"
                                }
                                localUriHandler.openUri(url)
                            }
                        )
                    }
                    Text(
                        text = "Kontakt:",
                        modifier = Modifier.padding(12.dp),
                        style = MaterialTheme.typography.headlineMedium
                    )
                    Column (Modifier.padding(8.dp)) {
                        place.email?.let {
                            Row(Modifier.padding(8.dp)) {
                                Text(
                                    text = "Email:       ",
                                    modifier = Modifier.padding(5.dp),
                                    style = MaterialTheme.typography.bodyLarge
                                )
                                ClickableEmail(place.email)
                            }
                        }
                        place.phone?.let {
                            Row(Modifier.padding(8.dp)) {
                                Text(
                                    text = "Telefon:         ",
                                    style = MaterialTheme.typography.bodyLarge,
                                    modifier = Modifier.padding(5.dp),
                                    textAlign = TextAlign.Left
                                )
                                ClickablePhoneNumber(phoneNumber = place.phone)
                            }
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun ClickablePhoneNumber(phoneNumber: String, modifier: Modifier = Modifier) {
        val dialerLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.StartActivityForResult()
        ) {}
        ClickableText(
            text = AnnotatedString(
                text = phoneNumber,
                spanStyle = SpanStyle(
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 20.sp,
                    textDecoration = TextDecoration.Underline
                )
            ),
            onClick = {
                val phoneUri = Uri.parse("tel:$phoneNumber")
                val dialIntent = Intent(Intent.ACTION_DIAL, phoneUri)
                dialerLauncher.launch(dialIntent)
            }
        )
    }

    @Composable
    fun ClickableEmail(address: String, modifier: Modifier = Modifier) {
        val context = LocalContext.current
        ClickableText(
            text = AnnotatedString(
                text = address,
                spanStyle = SpanStyle(
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 20.sp,
                    textDecoration = TextDecoration.Underline
                )
            ),
            onClick = {
                context.sendMail(to = address, subject = "")
            }
        )
    }
    fun Context.sendMail(to: String, subject: String) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "vnd.android.cursor.item/email"
        intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(to))
        intent.putExtra(Intent.EXTRA_SUBJECT, subject)
        startActivity(intent)
    }
}

@ScreenPreviews
@Composable
fun DetailContentPreview() {
    Showcase {
        Detail.Content(
            Detail.PreviewActions,
            place = null,
        )
    }
}
