package org.pandatech.bookpedia.book.data.dto

import kotlinx.serialization.SerialName

data class SearchResponseDto (
    @SerialName("docs") val results: List<SearchedBookDto>
)