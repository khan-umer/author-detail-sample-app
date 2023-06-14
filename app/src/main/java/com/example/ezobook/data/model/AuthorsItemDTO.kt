package com.example.ezobook.data.model


import com.example.ezobook.domain.modal.Author
import com.google.gson.annotations.SerializedName

data class AuthorsItemDTO(
    @SerializedName("author")
    val author: String?,
    @SerializedName("download_url")
    val downloadUrl: String?,
    @SerializedName("height")
    val height: Int?,
    @SerializedName("id")
    val id: String?,
    @SerializedName("url")
    val url: String?,
    @SerializedName("width")
    val width: Int?
) {
    fun authorToDomain(): Author {
        return Author(author = this.author ?: "NA", image = this.downloadUrl ?: "NA")
    }
}