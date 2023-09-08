package app.futured.academyproject.ui.screens.favorite

import app.futured.arkitekt.core.event.Event

sealed class FavoriteEvents : Event<FavoriteViewState>()


data class NavigateToDetailEvent(val placeId: Int) : FavoriteEvents()
