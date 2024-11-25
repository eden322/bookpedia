package org.pandatech.bookpedia.book.domain

import kotlinx.coroutines.flow.Flow
import org.pandatech.bookpedia.core.domain.DataError
import org.pandatech.bookpedia.core.domain.EmptyResult
import org.pandatech.bookpedia.core.domain.Result

interface BookRepository {
    suspend fun searchBooks(query: String): Result<List<Book>, DataError.Remote>
    suspend fun getBookDescription(bookId: String): Result<String?, DataError>
    fun getFavoriteBooks(): Flow<List<Book>>
    fun isBookFavorite(id: String): Flow<Boolean>
    suspend fun markAsFavorite(book: Book): EmptyResult<DataError.Local>
    suspend fun deleteFromFavorites(id: String)
}