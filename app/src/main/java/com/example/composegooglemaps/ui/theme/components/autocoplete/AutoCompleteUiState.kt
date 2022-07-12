package com.example.composegooglemaps.ui.theme.components.autocoplete

import com.google.android.libraries.places.api.model.AutocompletePrediction

data class AutoCompleteUiState(
    val queryString: String = "",
    val autocompletePredictions: List<AutocompletePrediction> = emptyList()
)
