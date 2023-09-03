package app.futured.academyproject.domain

import app.futured.academyproject.data.persistence.PlacesPersistence
import app.futured.arkitekt.crusecases.UseCase
import javax.inject.Inject

class SetFavoritePlaceUseCase @Inject constructor(
    private val persistence: PlacesPersistence,
): UseCase<SetFavoritePlaceUseCase.Args, Unit>() {

    override suspend fun build(args: Args) {
        if (!persistence.addPlaceId(args.placeID)) {
            persistence.removePlaceId(args.placeID)
        }
    }
    data class Args(val placeID: Int)
}
