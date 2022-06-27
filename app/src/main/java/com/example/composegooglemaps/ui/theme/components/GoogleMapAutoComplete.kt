package com.example.composegooglemaps.ui.theme.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.google.android.gms.common.api.ApiException
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.AutocompletePrediction
import com.google.android.libraries.places.api.model.AutocompleteSessionToken
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsResponse

val TAG = "AUTOCOMPLETE"


fun getSuggestions(
    queryString: String
):FindAutocompletePredictionsRequest{
    // Create a new token for the autocomplete session. Pass this to FindAutocompletePredictionsRequest,
    // and once again when the user makes a selection (for example when calling fetchPlace()).
    val token = AutocompleteSessionToken.newInstance()

    // Create a RectangularBounds object. (Optional)
//    val bounds = RectangularBounds.newInstance(
//        LatLng(-33.880490, 151.184363),
//        LatLng(-33.858754, 151.229596)
//    )
    // Use the builder to create a FindAutocompletePredictionsRequest.
    val request = FindAutocompletePredictionsRequest.builder()
            // Call either setLocationBias() OR setLocationRestriction().
//            .setLocationBias(bounds) (Optional)
            //.setLocationRestriction(bounds)(Optional)
//            .setOrigin(LatLng(-33.8749937, 151.2041382)) (Optional) *set this if you intent to calculate distance from your location
            .setCountries("KE")
//            .setTypeFilter(TypeFilter.ADDRESS)
            .setSessionToken(token)
            .setQuery(queryString)
            .build()

    return request
}

@Composable
fun GoogleMapsAutoComplete(
    selectedLocation:(AutocompletePrediction)->Unit
){

    // Create a new PlacesClient instance
    val placesClient = Places.createClient(LocalContext.current)

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        val predictions:MutableState<List<AutocompletePrediction>> = remember {
            mutableStateOf(emptyList())
        }
        val querySting = remember {
            mutableStateOf("")
        }
        var expanded by remember { mutableStateOf(false) }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(Alignment.TopStart)
        ) {

            OutlinedTextField(
                value = querySting.value,
                onValueChange = {
                    querySting.value = it
                    placesClient.findAutocompletePredictions(getSuggestions(queryString = querySting.value) )
                        .addOnSuccessListener { response: FindAutocompletePredictionsResponse ->
                            expanded = true
                            predictions.value = response.autocompletePredictions
                            for (prediction in predictions.value) {
                                Log.d(TAG, prediction.placeId)
                                Log.d(TAG, prediction.getPrimaryText(null).toString())
                            }
                        }.addOnFailureListener { exception: Exception? ->
                            if (exception is ApiException) {
                                Log.e(TAG, "Place not found: " + exception.statusCode)
                            }
                        }
                },
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
                    .height(48.dp)
            )


            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
                    .background(Color.White)
            ) {
                predictions.value.forEachIndexed { index, autocompletePrediction ->
                    DropdownMenuItem(
                        onClick = {
                            selectedLocation(autocompletePrediction)
                            Log.d(TAG, "You selected ${autocompletePrediction.getPrimaryText(null)}")
                        }
                    ) {
                        Text(text = autocompletePrediction.getFullText(null).toString())
                    }
                }
            }
        }
    }
}