package com.example.quicknews.ui.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.example.quicknews.data.local.AppDatabase
import com.example.quicknews.data.model.Post
import com.example.quicknews.data.remote.RetrofitClient
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val postDao = AppDatabase.getDatabase(application).postDao()
    private val apiService = RetrofitClient.apiService

    private val searchQuery = MutableLiveData("")

    val posts: LiveData<List<Post>> = searchQuery.switchMap { query ->
        if (query.isEmpty()) {
            postDao.getAllPosts()
        } else {
            postDao.searchPosts(query)
        }
    }

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    init {
        fetchPosts()
    }

    fun fetchPosts() {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                _error.value = null

                val response = apiService.getPosts()

                if (response.isSuccessful) {
                    response.body()?.let { fetchedPosts ->
                        postDao.insertAll(fetchedPosts)
                    }
                } else {
                    _error.value = "Failed to fetch posts: ${response.code()}"
                }
            } catch (e: Exception) {
                _error.value = "Network error: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun searchPosts(query: String) {
        searchQuery.value = query
    }

    fun updatePost(post: Post) {
        viewModelScope.launch {
            postDao.update(post)
        }
    }

    fun deletePost(post: Post) {
        viewModelScope.launch {
            postDao.delete(post)
        }
    }
}