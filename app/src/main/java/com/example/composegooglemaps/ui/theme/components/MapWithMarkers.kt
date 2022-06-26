package com.example.composegooglemaps.ui.theme.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings

@Composable
fun MapWithMarkers(
    markerList: List<GoogleMapMarkerDataClass>,
    onMarkerInfoWindowClicked:(markerDataClassId:Int)->Unit,
    cameraPosition: CameraPositionState,
    mapUiSettings: MapUiSettings,
    mapProperties: MapProperties
) {
    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPosition,
        uiSettings = mapUiSettings,
        properties = mapProperties,
    ) {
        markerList.forEach {
            GoogleMapMarker(
                position = LatLng(it.latitude, it.longitude),
                title = it.title,
                snippet = it.snippet,
                infoWindowClicked = { onMarkerInfoWindowClicked(it.id)}
            )
        }
    }
}