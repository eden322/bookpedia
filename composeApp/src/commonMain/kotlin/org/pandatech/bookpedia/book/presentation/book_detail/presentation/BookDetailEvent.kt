package org.pandatech.bookpedia.book.presentation.book_detail.presentation

import org.pandatech.bookpedia.book.domain.Book

sealed interface BookDetailEvent {
    data object OnBackClick: BookDetailEvent
    data object OnFavoriteClick: BookDetailEvent
    data class OnSelectedBookChange(val book: Book): BookDetailEvent
}