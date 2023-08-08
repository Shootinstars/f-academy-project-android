package app.futured.academyproject.ui.screens.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import app.futured.academyproject.data.model.local.Place
import app.futured.academyproject.tools.preview.PlacesDummyData
import app.futured.arkitekt.core.ViewState
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.collections.immutable.PersistentList
import javax.inject.Inject

@ViewModelScoped
class HomeViewState @Inject constructor() : ViewState {

    var places: PersistentList<Place>? by mutableStateOf(PlacesDummyData.places)
}
