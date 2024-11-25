package org.pandatech.bookpedia.book.presentation.book_list

import org.pandatech.bookpedia.book.domain.Book
import org.pandatech.bookpedia.core.presentation.UiText

data class BookListState (
    val searchQuery: String = "Kotlin",
    val searchResults: List<Book> = emptyList(),
    val favoriteBooks: List<Book> = emptyList(),
    val isLoading: Boolean = false,
    val selectedTabIndex: Int = 0,
    val errorMessage: UiText? = null
    )