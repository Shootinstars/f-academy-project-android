package app.futured.academyproject.ui.screens.favorite

import app.futured.academyproject.domain.GetFavoritePlacesFlowUseCase
import app.futured.academyproject.domain.GetLastLocationUseCase
import app.futured.academyproject.tools.arch.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toPersistentList
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    override val viewState: FavoriteViewState,
    private val getLastLocationUseCase: GetLastLocationUseCase,
    private val getFavoritePlacesFlowUseCase: GetFavoritePlacesFlowUseCase
) : BaseViewModel<FavoriteViewState>(), Favorite.Actions {

    override fun loadCulturalPlaces() {
        viewState.error = null

        getFavoritePlacesFlowUseCase.execute(viewState.location) {
            onNext {
                Timber.d("Cultural places: $it")

                viewState.places = it.toPersistentList()
            }
            onError { error ->
                Timber.e(error)
                viewState.error = error
            }
        }
    }

    override fun tryAgain() {
        loadCulturalPlaces()
    }

    override fun onAllowedLocationPermission() {
        // don't forget to call loadCulturalPlaces() after that
        getLastLocationUseCase.execute { onSuccess { viewState.location = it} }
        loadCulturalPlaces()
        Timber.d("Location permission allowed")
    }

    override fun navigateToDetailScreen(placeId: Int) {
        sendEvent(NavigateToDetailEvent(placeId))
    }

}
