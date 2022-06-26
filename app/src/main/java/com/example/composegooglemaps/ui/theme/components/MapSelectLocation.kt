package com.example.composegooglemaps.ui.theme.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings

@Composable
fun MapSelectLocation(
    cameraPositionState: CameraPositionState,
    mapUiSettings: MapUiSettings,
    mapProperties: MapProperties
){
    val selectedLocation:MutableState<LatLng?> = remember {
        mutableStateOf(null)
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
    ){
        GoogleMap(
            cameraPositionState = cameraPositionState,
            uiSettings = mapUiSettings,
            properties = mapProperties,
            onMapLongClick = {
                selectedLocation.value = it
            }
        ){
            if (selectedLocation.value != null){
                GoogleMapMarker(
                    position = selectedLocation.value!!,
                    title = "My Selected location",
                    snippet = "Long press to delete",
                    infoWindowLongClick = { selectedLocation.value = null }
                )
            }
        }
        Text(
            text = "Long press on your location to select",
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(0.7f)
                .align(Alignment.TopCenter)
                .clip(RoundedCornerShape(8.dp))
                .background(Color.White)
            ,
            textAlign = TextAlign.Center
        )
    }

}