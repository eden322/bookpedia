package org.pandatech.bookpedia.book.presentation.book_detail.presentation

import org.pandatech.bookpedia.book.domain.Book

data class BookDetailState(
    val isLoading: Boolean = true,
    val isFavorite: Boolean = false,
    val book: Book? = null
)