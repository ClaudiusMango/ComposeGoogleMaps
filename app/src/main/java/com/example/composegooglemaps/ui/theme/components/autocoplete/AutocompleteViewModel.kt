package com.example.composegooglemaps.ui.theme.components.autocoplete

import androidx.lifecycle.ViewModel
import com.google.android.libraries.places.api.model.AutocompletePrediction
import com.google.android.libraries.places.api.model.AutocompleteSessionToken
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class AutocompleteViewModel:ViewModel() {

    private val uiState = MutableStateFlow(AutoCompleteUiState())

    val autoCompleteUiState :StateFlow<AutoCompleteUiState> = uiState.asStateFlow()

    
    fun updateQueryString(queryString: String){

        uiState.update {
            it.copy(
                queryString = queryString
            )
        }
    }

    fun updatePredictions(predictions:List<AutocompletePrediction>){
        uiState.update {
            it.copy(
                autocompletePredictions = predictions
            )
        }
    }

    fun getSuggestionsRequest(
        queryString: String
    ): FindAutocompletePredictionsRequest {
        // Create a new token for the autocomplete session. Pass this to FindAutocompletePredictionsRequest,
        // and once again when the user makes a selection (for example when calling fetchPlace()).
        val token = AutocompleteSessionToken.newInstance()

        // Create a RectangularBounds object. (Optional)
//    val bounds = RectangularBounds.newInstance(
//        LatLng(-33.880490, 151.184363),
//        LatLng(-33.858754, 151.229596)
//    )
        // Use the builder to create a FindAutocompletePredictionsRequest.

        return FindAutocompletePredictionsRequest.builder()
            // Call either setLocationBias() OR setLocationRestriction().
//            .setLocationBias(bounds) (Optional)
//            .setLocationRestriction(bounds)(Optional)
//            .setOrigin(LatLng(-33.8749937, 151.2041382)) (Optional) *set this if you intent to calculate distance from your location
//            .setTypeFilter(TypeFilter.ADDRESS)
            .setCountries("KE")
            .setSessionToken(token)
            .setQuery(queryString)
            .build()
    }

}