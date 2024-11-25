package org.pandatech.bookpedia.book.data.network

import org.pandatech.bookpedia.book.data.dto.BookWorkDto
import org.pandatech.bookpedia.book.data.dto.SearchResponseDto
import org.pandatech.bookpedia.core.domain.DataError
import org.pandatech.bookpedia.core.domain.Result

interface RemoteBookDataSource {
    suspend fun searchBooks(
        query: String,
        resultLimit: Int? = null
    ): Result<SearchResponseDto, DataError.Remote>

    suspend fun getBookDetails(bookWorkId: String): Result<BookWorkDto, DataError.Remote>

}