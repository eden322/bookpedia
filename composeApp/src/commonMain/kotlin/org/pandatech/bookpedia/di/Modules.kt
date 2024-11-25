package org.pandatech.bookpedia.di

import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module
import org.pandatech.bookpedia.book.data.network.RemoteBookDataSource
import org.pandatech.bookpedia.book.domain.BookRepository
import org.pandatech.bookpedia.core.data.HttpClientFactory

expect val platformModule: Module

val sharedModule = module {
    single { HttpClientFactory.create(get()) }
    singleOf(::KtorRemoteBookDataSource).bind<RemoteBookDataSource>()
    singleOf(::DefaultBookRepository).bind<BookRepository>()


    viewModelOf(::BookListViewModel)
}