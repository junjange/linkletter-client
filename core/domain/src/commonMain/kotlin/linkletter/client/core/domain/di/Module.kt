package linkletter.client.core.domain.di

import linkletter.client.core.domain.usecase.DefaultGetBlogUseCase
import linkletter.client.core.domain.usecase.GetBlogUseCase
import org.koin.dsl.module

val coreDomainModule =
    module {
        single<GetBlogUseCase> { (DefaultGetBlogUseCase(get())) }
    }
