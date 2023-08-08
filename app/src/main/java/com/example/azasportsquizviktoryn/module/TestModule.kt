package com.example.azasportsquizviktoryn.module

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
    fun providesGetSportsRepository():SportsQuizRepository{
       return SportQuizRepositoryMock()
    }

    @Provides
    @Singleton
    fun provideUseCase(repository: SportsQuizRepository):QuizSportUseCase{
        return QuizSportUseCase(repository)
    }
}