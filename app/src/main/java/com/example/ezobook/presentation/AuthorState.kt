package com.example.ezobook.presentation

import com.example.ezobook.domain.modal.Author

data class AuthorState(
    val data: Author? = null,
    val error: String? = null,
    val isLoading: Boolean = false
)
