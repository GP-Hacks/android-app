package com.example.charity

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.model.ResultModel
import com.example.domain.model.CharityModel
import com.example.domain.model.PartnersCategoryModel
import com.example.domain.model.PartnersModel
import com.example.domain.usecase.GetCharityCategoriesUseCase
import com.example.domain.usecase.GetCharityUseCase
import com.example.domain.usecase.GetPartnersCategoriesUseCase
import com.example.domain.usecase.GetPartnersListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharityViewModel @Inject constructor(
    private val getCharityUseCase: GetCharityUseCase,
    private val getCharityCategoriesUseCase: GetCharityCategoriesUseCase
): ViewModel() {

    private val _listCharity = MutableStateFlow<ResultModel<List<CharityModel>>>(ResultModel.none())
    val listCharity: StateFlow<ResultModel<List<CharityModel>>>
        get() = _listCharity

    private val _listCategories = MutableStateFlow<ResultModel<List<String>>>(ResultModel.none())
    val listCategories: StateFlow<ResultModel<List<String>>>
        get() = _listCategories

    var currentCategory = mutableStateOf("all")
        private set

    fun changeCategory(category: String) {
        currentCategory.value = category

        loadPartners()
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

    fun loadPartners() {
        viewModelScope.launch {
            getCharityUseCase(currentCategory.value)
                .flowOn(Dispatchers.IO)
                .catch {
                    _listCharity.value = ResultModel.failure("Непредвиденная ошибка.")
                }
                .collect {
                    _listCharity.value = it
                }
        }
    }

}