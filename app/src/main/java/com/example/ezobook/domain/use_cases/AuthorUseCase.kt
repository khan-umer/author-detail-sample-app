package com.example.ezobook.domain.use_cases

import com.example.ezobook.domain.modal.Author
import com.example.ezobook.domain.repository.AuthorRepository
import com.example.ezobook.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class AuthorUseCase @Inject constructor(private val authorRepository: AuthorRepository) {

    suspend fun getAuthorDetail(): Flow<Resource<Author>> {
        return flow {
            try {
                emit(Resource.Loading())
                val response = authorRepository.getAuthor()
                if (response.isEmpty()) {
                    emit(Resource.Error("Data Not Found"))
                } else {
                    emit(Resource.Success(data = response[0].authorToDomain()))
                }
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error(message = e.localizedMessage ?: "HttpException Occurred"))
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error(message = e.localizedMessage ?: "IOException Occurred"))
            } catch (e: Exception) {
                e.printStackTrace()
                emit(Resource.Error(message = e.localizedMessage ?: "Unknown Exception Occurred"))
            }
        }

    }
}