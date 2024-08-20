package com.example.places

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.model.ResultModel
import com.example.domain.model.PlaceModel
import com.example.domain.usecase.BuyTicketForPlaceUseCase
import com.example.domain.usecase.GetPlacesUseCase
import com.example.places.utils.LocationUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlacesViewModel @Inject constructor(
    private val getPlacesUseCase: GetPlacesUseCase,
    private val buyTicketForPlaceUseCase: BuyTicketForPlaceUseCase
//    private val locationUtils: LocationUtils
): ViewModel() {

    private val _listPlaces = MutableStateFlow<ResultModel<List<PlaceModel>>>(ResultModel.none())
    val listPlaces: StateFlow<ResultModel<List<PlaceModel>>>
        get() = _listPlaces

    fun loadPlaces() {
        viewModelScope.launch {
            getPlacesUseCase()
                .flowOn(Dispatchers.IO)
                .catch {
                    _listPlaces.value = ResultModel.failure("Непредвиденная ошибка.")
                }
                .collect{
                    _listPlaces.value = it
                    Log.i("NO PAR", it.data.toString())
                    Log.i("NO PAR", it.message.toString())
                }
        }
    }

    fun loadPlaces(
        latitude: Double,
        longitude: Double
    ) {
        viewModelScope.launch {
            getPlacesUseCase(latitude, longitude)
                .flowOn(Dispatchers.IO)
                .catch {
                    _listPlaces.value = ResultModel.failure("Непредвиденная ошибка.")
                }
                .collect{
                    _listPlaces.value = it
                    Log.i("YES PAR", it.data.toString())
                    Log.i("YES PAR", it.message.toString())
                }
        }
    }

    fun buyTicket(
        id: Int,
        date: Long,
        time: Long,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        viewModelScope.launch {
            buyTicketForPlaceUseCase(id, date, time)
                .flowOn(Dispatchers.IO)
                .catch {
                    Log.e("BUY TICKET", it.toString())
                    onFailure("Непредвиденная ошибка.")
                }
                .collect {
                    Log.i("BUY TICKET", it.message.toString())
                    Log.i("BUY TICKET", it.data.toString())
                    if (it.status == ResultModel.Status.SUCCESS && it.data == true) {
                        onSuccess()
                    } else if (it.status == ResultModel.Status.FAILURE){
                        onFailure(it.message ?: "Непредвиденная ошибка.")
                    }
                }
        }
    }

//    fun loadPlacesBasedOnLocation() {
//        locationUtils.getLastLocation { location ->
//            if (location != null) {
//                loadPlaces(location.latitude, location.longitude)
//            } else {
//                loadPlaces()
//            }
//        }
//    }

}