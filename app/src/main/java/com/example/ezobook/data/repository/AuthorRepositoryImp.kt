package com.example.ezobook.data.repository

import com.example.ezobook.data.model.AuthorsDTO
import com.example.ezobook.data.remote.ServiceApi
import com.example.ezobook.domain.repository.AuthorRepository
import javax.inject.Inject

class AuthorRepositoryImp @Inject constructor(private val serviceApi:ServiceApi):AuthorRepository {

    override suspend fun getAuthor(): AuthorsDTO {
        return serviceApi.getAuthor()
    }
}