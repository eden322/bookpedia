package org.pandatech.bookpedia.book.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.pandatech.bookpedia.book.domain.Book
import org.pandatech.bookpedia.book.domain.BookRepository
import org.pandatech.bookpedia.book.presentation.book_list.BookListEvent
import org.pandatech.bookpedia.book.presentation.book_list.BookListState
import org.pandatech.bookpedia.core.domain.onError
import org.pandatech.bookpedia.core.domain.onSuccess
import org.pandatech.bookpedia.core.presentation.toUiText

class BookListViewModel(
    private val bookRepository: BookRepository
) : ViewModel() {

    private var cachedBooks = emptyList<Book>()
    private var searchJob: Job? = null

    private val _state = MutableStateFlow(BookListState())
    val state = _state
        .onStart {
            if (cachedBooks.isEmpty()) {
                observeSearchQuery()
            }
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            _state.value
        )

    fun onAction(event: BookListEvent) {
        when (event) {
            is BookListEvent.OnBookClick -> TODO()
            is BookListEvent.OnSearchQueryChange -> {
                _state.update {
                    it.copy(searchQuery = event.query)
                }
            }

            is BookListEvent.OnTabSelected -> {
                _state.update {
                    it.copy(selectedTabIndex = event.index)
                }
            }
        }
    }

    private fun observeSearchQuery() {
        state
            .map { it.searchQuery }
            .distinctUntilChanged()
            .debounce(500L)
            .onEach { query ->
                when {
                    query.isBlank() -> {
                        _state.update {
                            it.copy(
                                errorMessage = null,
                                searchResults = cachedBooks
                            )
                        }
                    }

                    query.length >= 2 -> {
                        searchJob?.cancel()
                        searchJob = searchBooks(query)
                    }
                }
            }
            .launchIn(viewModelScope)
    }

    private fun searchBooks(query: String) = viewModelScope.launch {
        _state.update {
            it.copy(
                isLoading = true
            )
        }
        bookRepository
            .searchBooks(query)
            .onSuccess { searchResults ->
                _state.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = null,
                        searchResults = searchResults
                    )
                }
            }
            .onError { error ->
                _state.update {
                    it.copy(
                        searchResults = emptyList(),
                        isLoading = false,
                        errorMessage = error.toUiText()
                    )
                }

            }
    }
}