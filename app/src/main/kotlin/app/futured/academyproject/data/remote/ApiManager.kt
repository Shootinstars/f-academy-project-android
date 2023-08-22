@file:Suppress("UnusedPrivateMember")

package app.futured.academyproject.data.remote

import app.futured.academyproject.data.model.api.CulturalPlaces
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApiManager @Inject constructor(
    private val apiService: ApiService,
) {
    suspend fun getCulturalPlaces(): CulturalPlaces {
        try {
            return apiService.getAllPlaces()
        }
        catch (exc: ApiException) {
            throw ApiExceptionUnknown("Error Happened", null)
        }
    }

}
