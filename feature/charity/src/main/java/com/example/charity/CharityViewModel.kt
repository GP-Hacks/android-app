package com.example.charity

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.model.ResultModel
import com.example.domain.model.CharityModel
import com.example.domain.model.PlaceModel
import com.example.domain.usecase.DonateToCharityUseCase
import com.example.domain.usecase.GetCharityCategoriesUseCase
import com.example.domain.usecase.GetCharityUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharityViewModel @Inject constructor(
    private val getCharityUseCase: GetCharityUseCase,
    private val getCharityCategoriesUseCase: GetCharityCategoriesUseCase,
    private val donateToCharityUseCase: DonateToCharityUseCase
): ViewModel() {

    private val _listCharity = MutableStateFlow<ResultModel<List<CharityModel>>>(ResultModel.none())
    private val _listCharityAndSearch = MutableStateFlow<ResultModel<List<CharityModel>>>(ResultModel.none())
    val listCharity: StateFlow<ResultModel<List<CharityModel>>>
        get() = _listCharityAndSearch

    private val _listCategories = MutableStateFlow<ResultModel<List<String>>>(ResultModel.none())
    val listCategories: StateFlow<ResultModel<List<String>>>
        get() = _listCategories

    var currentCategory = mutableStateOf("all")
        private set

    var currentSearch = mutableStateOf("")
        private set

    fun updateSearch(newSearch: String) {
        currentSearch.value = newSearch

        if (_listCharity.value.status == ResultModel.Status.SUCCESS && _listCharity.value.data is List) {
            viewModelScope.launch {
                updateListFromSearch(_listCharity.value.data!!, currentSearch.value)
                    .flowOn(Dispatchers.IO)
                    .catch {
                        _listCharityAndSearch.value = ResultModel.failure("Непредвиденная ошибка.")
                    }
                    .collect {
                        _listCharityAndSearch.value = it
                    }
            }
        } else {
            _listCharityAndSearch.value = _listCharity.value
        }
    }

    private fun updateListFromSearch(list: List<CharityModel>, search: String): Flow<ResultModel<List<CharityModel>>> = flow {
        emit(ResultModel.loading())

        emit(ResultModel.success(buildList {
            list.forEach {
                if (search.lowercase() in it.name.lowercase()) {
                    add(it)
                }
            }
        }))
    }

    fun changeCategory(category: String) {
        currentCategory.value = category

        loadCharity()
    }

    fun loadCategories() {
        viewModelScope.launch {
            getCharityCategoriesUseCase()
                .flowOn(Dispatchers.IO)
                .catch {
                    _listCategories.value = ResultModel.failure("Непредвиденная ошибка.")
                }
                .collect {
                    _listCategories.value = it
                }
        }
    }

    fun loadCharity() {
        viewModelScope.launch {
            getCharityUseCase(currentCategory.value)
                .flowOn(Dispatchers.IO)
                .catch {
                    _listCharity.value = ResultModel.failure("Непредвиденная ошибка.")
                    _listCharityAndSearch.value = ResultModel.failure("Непредвиденная ошибка.")
                }
                .collect {
                    _listCharity.value = it
                    updateSearch(currentSearch.value)
                }
        }
    }

    fun donateToCharity(
        id: Int,
        amount: Int,
        onSuccess: () -> Unit,
        onFailure: () -> Unit
    ) {
        viewModelScope.launch {
            donateToCharityUseCase(id, amount)
                .flowOn(Dispatchers.IO)
                .catch {
                    onFailure()
                }
                .collect {
                    if (it.data == true) {
                        onSuccess()
                    } else if (it.status == ResultModel.Status.FAILURE) {
                        onFailure()
                    }
                }
        }
    }

}