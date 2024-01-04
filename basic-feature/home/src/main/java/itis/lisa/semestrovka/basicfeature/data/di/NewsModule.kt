package itis.lisa.semestrovka.basicfeature.data.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import itis.lisa.semestrovka.basicfeature.data.remote.api.NewsApi
import itis.lisa.semestrovka.basicfeature.data.repository.NewsRepositoryImpl
import itis.lisa.semestrovka.basicfeature.domain.repository.NewsRepository
import itis.lisa.semestrovka.basicfeature.domain.usecase.GetNewsUseCase
import itis.lisa.semestrovka.basicfeature.domain.usecase.RefreshNewsUseCase
import itis.lisa.semestrovka.basicfeature.domain.usecase.getNews
import itis.lisa.semestrovka.basicfeature.domain.usecase.refreshNews
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)

internal object NewsModule {

    @Provides
    @Singleton
    fun provideNewsApi(
        retrofit: Retrofit,
    ): NewsApi {
        return retrofit.create(NewsApi::class.java)
    }

    @Provides
    fun provideGetNewssUseCase(
        newsRepository: NewsRepository
    ): GetNewsUseCase {
        return GetNewsUseCase {
            getNews(newsRepository)
        }
    }

    @Provides
    fun provideRefreshNewsUseCase(
        newsRepository: NewsRepository
    ): RefreshNewsUseCase {
        return RefreshNewsUseCase {
            refreshNews(newsRepository)
        }
    }

    @Module
    @InstallIn(SingletonComponent::class)
    interface BindsModule {

        @Binds
        @Singleton
        fun bindNewsRepository(impl: NewsRepositoryImpl): NewsRepository
    }
}
