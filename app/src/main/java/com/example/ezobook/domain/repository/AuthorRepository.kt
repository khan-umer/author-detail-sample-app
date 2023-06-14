package com.example.ezobook.domain.repository

import com.example.ezobook.data.model.AuthorsDTO

interface AuthorRepository {

    suspend fun getAuthor():AuthorsDTO
}