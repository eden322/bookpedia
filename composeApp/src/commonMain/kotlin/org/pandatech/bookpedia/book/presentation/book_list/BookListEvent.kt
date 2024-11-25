package org.pandatech.bookpedia.book.presentation.book_list

import org.pandatech.bookpedia.book.domain.Book

sealed interface BookListEvent {
    data class OnSearchQueryChange(val query: String): BookListEvent
    data class OnBookClick(val book: Book): BookListEvent
    data class OnTabSelected(val index: Int): BookListEvent
}