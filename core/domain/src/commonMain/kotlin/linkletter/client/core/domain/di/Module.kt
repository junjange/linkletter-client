package linkletter.client.core.domain.di

import linkletter.client.core.domain.usecase.DefaultDeleteBlogInfoUseCase
import linkletter.client.core.domain.usecase.DefaultFetchPostListUseCase
import linkletter.client.core.domain.usecase.DefaultGetAllBlogInfosUseCase
import linkletter.client.core.domain.usecase.DefaultGetBlogUseCase
import linkletter.client.core.domain.usecase.DefaultInsertBlogInfoUseCase
import linkletter.client.core.domain.usecase.DeleteBlogInfoUseCase
import linkletter.client.core.domain.usecase.FetchPostListUseCase
import linkletter.client.core.domain.usecase.GetAllBlogInfosUseCase
import linkletter.client.core.domain.usecase.GetBlogUseCase
import linkletter.client.core.domain.usecase.InsertBlogInfoUseCase
import org.koin.dsl.module

val coreDomainModule =
    module {
        single<GetBlogUseCase> { (DefaultGetBlogUseCase(get())) }
        single<DeleteBlogInfoUseCase> { (DefaultDeleteBlogInfoUseCase(get())) }
        single<FetchPostListUseCase> { (DefaultFetchPostListUseCase(get())) }
        single<GetAllBlogInfosUseCase> { (DefaultGetAllBlogInfosUseCase(get())) }
        single<InsertBlogInfoUseCase> { (DefaultInsertBlogInfoUseCase(get())) }
    }
