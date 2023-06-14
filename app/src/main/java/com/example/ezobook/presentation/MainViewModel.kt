package com.example.ezobook.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ezobook.domain.use_cases.AuthorUseCase
import com.example.ezobook.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "MainViewModel"

@HiltViewModel
class MainViewModel @Inject constructor(private val authorUseCase: AuthorUseCase) : ViewModel() {

    private var _author: MutableStateFlow<AuthorState> = MutableStateFlow(AuthorState())
    val author: StateFlow<AuthorState> = _author

    var authorJob: Job? = null


    fun getAuthorDetails() {
        authorJob?.cancel()
        authorJob = viewModelScope.launch {
            Log.d(TAG, "new Job created!")
            authorUseCase.getAuthorDetail().onEach {
                when (it) {
                    is Resource.Loading -> {
                        _author.value = AuthorState(isLoading = true)
                    }
                    is Resource.Success -> {
                        _author.value = AuthorState(data = it.data)
                    }
                    is Resource.Error -> {
                        _author.value = AuthorState(error = it.message)
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

    override fun onCleared() {
        super.onCleared()
        authorJob?.cancel()
    }


}