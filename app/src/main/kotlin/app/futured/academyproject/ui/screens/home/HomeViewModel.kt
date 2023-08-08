package app.futured.academyproject.ui.screens.home

import app.futured.academyproject.domain.GetPlacesUseCase
import app.futured.academyproject.tools.arch.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toPersistentList
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    override val viewState: HomeViewState,
    private val getPlacesUseCase: GetPlacesUseCase
) : BaseViewModel<HomeViewState>(), Home.Actions {

    init {
        loadCulturalPlaces()
    }

    private fun loadCulturalPlaces() {
        getPlacesUseCase.execute {
            onSuccess { places ->  viewState.places = places?.toPersistentList()}
        }
    }

    override fun navigateToDetailScreen(placeId: Int) {
        sendEvent(NavigateToDetailEvent(placeId))
    }
}
