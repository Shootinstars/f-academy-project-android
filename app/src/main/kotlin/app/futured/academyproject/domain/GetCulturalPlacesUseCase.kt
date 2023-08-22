package app.futured.academyproject.domain

import app.futured.academyproject.data.model.local.Place
import app.futured.academyproject.data.remote.ApiManager
import app.futured.arkitekt.crusecases.UseCase
import javax.inject.Inject

class GetCulturalPlacesUseCase @Inject constructor(
    private val apiManager: ApiManager,
) : UseCase<Unit, List<Place>>() {

    // TODO Krok 6:
    //  Implementuj metódu build, ktorá vráti zoznam miest.
    //  Využi ApiManager a namapuj CulturalPlaces na List<Place>.
    //  Mali by stačiť iba nasledujúce properties: id, longitude, latitude, name, type, note,
    override suspend fun build(args: Unit): List<Place> =
        apiManager.getCulturalPlaces().features.map {
            Place(
                id = it.properties.ogcFid,
                name = it.properties.name,
                type = it.type,
                email = it.properties.email,
                phone = it.properties.phone,
                image1Url = it.properties.image1Url,
                webUrl = it.properties.webUrl,
                latitude = it.geometry?.coordinates?.getOrNull(0),
                longitude = it.geometry?.coordinates?.getOrNull(1),
                note = it.properties.note
            )
        }
}
