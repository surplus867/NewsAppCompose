package com.example.newsapp.di

import android.app.Application
import com.example.newsapp.data.manager.LocalUserMangerImpl
import com.example.newsapp.domain.manager.LocalUserManager
import com.example.newsapp.domain.usercases.app_entry.AppEntryUseCases
import com.example.newsapp.domain.usercases.app_entry.ReadAppEntry
import com.example.newsapp.domain.usercases.app_entry.SaveAppEntry
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
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


}