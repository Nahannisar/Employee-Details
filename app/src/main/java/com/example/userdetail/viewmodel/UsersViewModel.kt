package com.example.userdetail.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.userdetail.model.User
import com.example.userdetail.paging.UsersPagingSource
import com.example.userdetail.repository.ApiRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UsersViewModel @Inject constructor(private val repository: ApiRepository) : ViewModel() {

    val loading = MutableLiveData<Boolean>()

    val usersList = Pager(PagingConfig(1)) {
        UsersPagingSource(repository)
    }.flow.cachedIn(viewModelScope)

    val userDetails = MutableLiveData<User>()
    fun loadDetailsUser(userId: Int) = viewModelScope.launch {
        loading.postValue(true)
        val response = repository.getUserDetails(userId)
        userDetails.postValue(response)
        loading.postValue(false)
    }
}