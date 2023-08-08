package com.example.azasportsquizviktoryn.module

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.example.data.repository.SportQuizRepositoryMock
import com.example.domain.repository.SportsQuizRepository
import com.example.domain.usecase.QuizSportUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
class TestModule {

    @Provides
    @Singleton
    fun provideContext(application: Application): Context {
        return application
    }



    @Provides
    @Singleton
    fun provideSharedPreferences(application: Application): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(application)
    }


    @Provides
    @Singleton
    fun providesGetSportsRepository():SportsQuizRepository{
       return SportQuizRepositoryMock()
    }

    @Provides
    @Singleton
    fun provideUseCase(repository: SportsQuizRepository):QuizSportUseCase{
        return QuizSportUseCase(repository)
    }
}