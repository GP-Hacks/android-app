package com.example.news

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.domain.model.NewsModel
import com.example.domain.usecase.GetNewsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val getNewsUseCase: GetNewsUseCase
): ViewModel() {

    private val _listNews = MutableStateFlow<PagingData<NewsModel>>(PagingData.empty())
    val listNews: StateFlow<PagingData<NewsModel>>
        get() = _listNews

    private val pageSize = 5

    fun loadNews() {
        viewModelScope.launch {
            getNewsUseCase(pageSize)
                .flowOn(Dispatchers.IO)
                .distinctUntilChanged()
                .cachedIn(viewModelScope)
                .catch {
                    _listNews.value = PagingData.empty()
                    Log.e("NEWS VM", it.message.toString())
                }
                .collect { res ->
                    _listNews.value = res
                    Log.i("NEWS VM", res.toString())
                }
        }
    }

}