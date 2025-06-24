package linkletter.client.core.domain.di

import linkletter.client.core.domain.usecase.DefaultGetPostsUseCase
import linkletter.client.core.domain.usecase.GetPostsUseCase
import org.koin.dsl.module

val coreDomainModule =
    module {
        single<GetPostsUseCase> { (DefaultGetPostsUseCase(get())) }
    }
