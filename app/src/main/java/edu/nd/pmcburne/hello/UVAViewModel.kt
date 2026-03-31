package edu.nd.pmcburne.hello

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.google.maps.android.compose.CameraPositionState
import edu.nd.pmcburne.hello.data.AppDatabase
import edu.nd.pmcburne.hello.data.LocationEntity
import edu.nd.pmcburne.hello.network.ApiClient
import kotlinx.coroutines.launch

class UVAViewModel(application: Application) : AndroidViewModel(application) {
    val cameraPositionState = CameraPositionState()
    var hasCentered = false

    private val db = Room.databaseBuilder(
        application,
        AppDatabase::class.java,
        "uva-db"
    ).build()

    var locations by mutableStateOf<List<LocationEntity>>(emptyList())
    var selectedTag by mutableStateOf("core")
        private set
    val tags: List<String>
        get() = locations
            .flatMap { it.tags.split(",")}
            .distinct()
            .sorted()

    val filteredLocations: List<LocationEntity>
        get() = locations.filter {
            it.tags.split(",").contains(selectedTag)
        }

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch{
            val dao = db.locationDao()

            val apiData = ApiClient.api.getPlacemarks()

            val entities = apiData.map {
                LocationEntity(
                    id = it.id,
                    name = it.name,
                    description = it.description,
                    latitude = it.visual_center.latitude,
                    longitude = it.visual_center.longitude,
                    tags = it.tag_list.joinToString(",")
                )
            }

            dao.insertAll(entities)

            locations = dao.getAll()
        }
    }

    fun setTag(tag: String) {
        selectedTag = tag
    }
}