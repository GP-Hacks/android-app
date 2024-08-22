package com.example.stocks

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.model.ResultModel
import com.example.domain.model.CharityModel
import com.example.domain.model.PartnersCategoryModel
import com.example.domain.model.PartnersModel
import com.example.domain.usecase.GetPartnersCategoriesUseCase
import com.example.domain.usecase.GetPartnersListUseCase
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
class StocksViewModel @Inject constructor(
    private val getPartnersListUseCase: GetPartnersListUseCase,
    private val getPartnersCategoriesUseCase: GetPartnersCategoriesUseCase
): ViewModel() {

    private val _listPartners = MutableStateFlow<ResultModel<List<PartnersModel>>>(ResultModel.none())
    private val _listPartnersAndSearch = MutableStateFlow<ResultModel<List<PartnersModel>>>(ResultModel.none())
    val listPartners: StateFlow<ResultModel<List<PartnersModel>>>
        get() = _listPartnersAndSearch

    private val _listCategories = MutableStateFlow<ResultModel<List<PartnersCategoryModel>>>(ResultModel.none())
    val listCategories: StateFlow<ResultModel<List<PartnersCategoryModel>>>
        get() = _listCategories

    var currentCategory = mutableStateOf("all")
        private set

    var currentSearch = mutableStateOf("")
        private set

    fun updateSearch(newSearch: String) {
        currentSearch.value = newSearch

        if (_listPartners.value.status == ResultModel.Status.SUCCESS && _listPartners.value.data is List) {
            viewModelScope.launch {
                updateListFromSearch(_listPartners.value.data!!, currentSearch.value)
                    .flowOn(Dispatchers.IO)
                    .catch {
                        _listPartnersAndSearch.value = ResultModel.failure("Непредвиденная ошибка.")
                    }
                    .collect {
                        _listPartnersAndSearch.value = it
                    }
            }
        } else {
            _listPartnersAndSearch.value = _listPartners.value
        }
    }

    fun changeCategory(category: String) {
        currentCategory.value = category

        loadPartners(category)
    }

    private fun updateListFromSearch(list: List<PartnersModel>, search: String): Flow<ResultModel<List<PartnersModel>>> = flow {
        emit(ResultModel.loading())

        emit(ResultModel.success(buildList {
            list.forEach {
                if (search.lowercase() in it.title.lowercase()) {
                    add(it)
                }
            }
        }))
    }

    fun loadCategories() {
        viewModelScope.launch {
            getPartnersCategoriesUseCase()
                .flowOn(Dispatchers.IO)
                .catch {
                    _listCategories.value = ResultModel.failure("Непредвиденная ошибка.")
                }
                .collect {
                    _listCategories.value = it
                }
        }
    }

    fun loadPartners(category: String? = null) {
        viewModelScope.launch {
            getPartnersListUseCase()
                .flowOn(Dispatchers.IO)
                .catch {
                    _listPartners.value = ResultModel.failure("Непредвиденная ошибка.")
                    _listPartners.value = ResultModel.failure("Непредвиденная ошибка.")
                }
                .collect{
                    if (category != "all"&& category != null) {
                        if (it.status == ResultModel.Status.SUCCESS) {
                            _listPartners.value = ResultModel.success(
                                buildList {
                                    it.data?.forEach { m ->
                                        if (m.category.title == category) {
                                            add(m)
                                        }
                                    }
                                }
                            )
                        } else {
                            _listPartners.value = it
                        }
                    } else {
                        _listPartners.value = it
                    }
                    updateSearch(currentSearch.value)
                    Log.i("NO PAR", it.data.toString())
                    Log.i("NO PAR", it.message.toString())
                }
        }
    }

}