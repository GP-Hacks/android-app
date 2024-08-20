package com.example.data.source.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.data.response.toNewsModel
import com.example.data.source.remote.ApiRemoteSource
import com.example.domain.model.NewsModel

class NewsPagingSource(
    private val apiRemoteSource: ApiRemoteSource,
    private val pageSize: Int
): PagingSource<Int, NewsModel>() {
    override fun getRefreshKey(state: PagingState<Int, NewsModel>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, NewsModel> {
        return try {
            val currentPage = params.key ?: 1
            val news = apiRemoteSource.getListNews(
                page = currentPage,
                pageSize = pageSize
            )
            Log.i("NEWS PAGING SOURCE", news.data.toString())
            LoadResult.Page(
                data = news.data!!.result.posts.map { it.toNewsModel() },
                prevKey = if (currentPage == 1) null else currentPage - 1,
                nextKey = if (news.data!!.result.posts.isEmpty()) null else currentPage + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}