package edu.nd.pmcburne.hello

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.key
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerInfoWindow
import com.google.maps.android.compose.MarkerInfoWindowContent
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState


@Composable
fun UVAScreen(viewModel: UVAViewModel = viewModel()) {
    val cameraPositionState = viewModel.cameraPositionState

    Column {
        Dropdown(
            tags = viewModel.tags,
            selected = viewModel.selectedTag,
            onSelected = {viewModel.setTag(it)}
        )

        GoogleMap(modifier = Modifier.fillMaxSize(), cameraPositionState = cameraPositionState) {

            val locations = viewModel.filteredLocations

            LaunchedEffect(locations) {
                if (!viewModel.hasCentered && locations.isNotEmpty()) {
                    val boundsBuilder = LatLngBounds.builder()
                    locations.forEach { location ->
                        boundsBuilder.include(
                            LatLng(location.latitude, location.longitude)
                        )
                    }

                    val bounds = boundsBuilder.build()

                    cameraPositionState.animate(
                        update = CameraUpdateFactory.newLatLngBounds(bounds, 100),
                        durationMs = 1000
                    )

                    viewModel.hasCentered = true
                }
            }

            locations.forEach { location ->
                key(location.id) {
                    val markerState = rememberMarkerState(
                        position = LatLng(
                            location.latitude,
                            location.longitude
                        )
                    )
                        MarkerInfoWindowContent(state = markerState,
                            title=location.name,
                            snippet = location.description) {
                            Column(modifier = Modifier.padding(8.dp)) {
                                Text(text = location.name, style = typography.titleMedium)
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(text = location.description)
                            }
                        }

                }
            }
        }
    }
}