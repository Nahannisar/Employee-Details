package com.example.userdetail.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.userdetail.model.User
import com.example.userdetail.repository.ApiRepository
import retrofit2.HttpException

class UsersPagingSource(
    private val repository: ApiRepository,
) : PagingSource<Int, User>() {
    private var responseData = mutableListOf<User>()
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int,User> {
        return try {
            val currentPage = params.key ?: 1
            val loadStart = (currentPage-1) * 10
            val response = repository.getUsersList( 10,loadStart)
            val data = response.users
            responseData = mutableListOf<User>()
            responseData.addAll(data)
                LoadResult.Page(
                    data = responseData,
                    prevKey = if (currentPage == 1) null else -1,
                    nextKey = if (responseData.isEmpty()) null else currentPage + 1
                )
        } catch (e: Exception) {
            LoadResult.Error(e)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }

    }

    override fun getRefreshKey(state: PagingState<Int, User>): Int? {
        return null
    }


}