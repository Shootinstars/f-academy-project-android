package app.futured.academyproject.ui.screens.detail

import app.futured.academyproject.domain.GetPlaceDetailUseCase
import app.futured.academyproject.tools.arch.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    override val viewState: DetailViewState,
    private val getPlaceUseCase: GetPlaceDetailUseCase
) : BaseViewModel<DetailViewState>(), Detail.Actions {

    init {
        loadPlace()
    }

    private fun loadPlace() {
        getPlaceUseCase.execute(viewState.placeId) {
            onSuccess { place -> viewState.place = place }
        }
    }

    override fun navigateBack() {
        sendEvent(NavigateBackEvent)
    }
}
