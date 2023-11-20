package com.example.newsapp.di

import android.app.Application
import androidx.room.Room
import com.example.newsapp.data.local.NewsDao
import com.example.newsapp.data.local.NewsDatabase
import com.example.newsapp.data.local.NewsTypeConvertor
import com.example.newsapp.data.manager.LocalUserMangerImpl
import com.example.newsapp.data.remote.dto.NewsApi
import com.example.newsapp.data.repository.NewsRepositoryImpl
import com.example.newsapp.domain.manager.LocalUserManager
import com.example.newsapp.domain.manager.usecases.news.DeleteArticle
import com.example.newsapp.domain.manager.usecases.news.GetNews
import com.example.newsapp.domain.manager.usecases.news.NewsUseCases
import com.example.newsapp.domain.manager.usecases.news.SearchNews
import com.example.newsapp.domain.manager.usecases.news.SelectArticle
import com.example.newsapp.domain.manager.usecases.news.SelectArticles
import com.example.newsapp.domain.manager.usecases.news.UpsertArticle
import com.example.newsapp.domain.repository.NewsRepository
import com.example.newsapp.domain.usercases.app_entry.AppEntryUseCases
import com.example.newsapp.domain.usercases.app_entry.ReadAppEntry
import com.example.newsapp.domain.usercases.app_entry.SaveAppEntry
import com.example.newsapp.utili.Constants.BASE_URL
import com.example.newsapp.utili.Constants.NEWS_DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

// Dagger Hilt module providing dependencies related to the application module.
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    // Provides a singleton instance of LocalUserManager, which is implemented
    // by LocalUserManagerImpl. It takes an Application as a parameter.
    @Provides
    @Singleton
    fun provideLocalUserManger(
        application: Application
    ): LocalUserManager = LocalUserMangerImpl(application)

    // Provides a singleton instance of AppEntryUseCases. It depends on a singleton
    // instance of LocalUserManager. It uses the ReadAppEntry and SaveAppEntry use cases.
    @Provides
    @Singleton
    fun provideAppEntryUseCases(
        localUserManger: LocalUserManager
    ) = AppEntryUseCases(
        readAppEntry = ReadAppEntry(localUserManger),
        saveAppEntry = SaveAppEntry(localUserManger)
    )

    // Provide a singleton instance of NewsApi using Retrofit.
    @Provides
    @Singleton
    fun provideNewsApi(): NewsApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NewsApi::class.java)
    }

    // Provide a singleton instance of a NewsRepositoryImpl.
    @Provides
    @Singleton
    fun provideNewsRepository(
        newsApi: NewsApi,
        newsDao: NewsDao
    ): NewsRepository = NewsRepositoryImpl(newsApi, newsDao)

    // Provides a singleton instance of NewsUseCases. It depends on a singleton
    // instance of NewsRepository and uses various news-related use cases.
    @Provides
    @Singleton
    fun provideNewsUseCases(
        newsRepository: NewsRepository,
    ): NewsUseCases {
        return NewsUseCases(
            getNews = GetNews(newsRepository),
            searchNews = SearchNews(newsRepository),
            upsertArticle = UpsertArticle(newsRepository),
            deleteArticle = DeleteArticle(newsRepository),
            selectArticles = SelectArticles(newsRepository),
            selectArticle = SelectArticle(newsRepository)
        )
    }

    // Provides a singleton instance of NewsDatabase using Room.
    @Provides
    @Singleton
    fun provideNewsDatabase(
        application: Application
    ): NewsDatabase{
        return Room.databaseBuilder(
            context = application,
            klass = NewsDatabase::class.java,
            name = NEWS_DATABASE_NAME
        ).addTypeConverter(NewsTypeConvertor())
            .fallbackToDestructiveMigration()
            .build()
    }

    // Provides a singleton instance of NewsDao.
    @Provides
    @Singleton
    fun provideNewsDao(
        newsDatabase: NewsDatabase
    ): NewsDao = newsDatabase.newsDao
}