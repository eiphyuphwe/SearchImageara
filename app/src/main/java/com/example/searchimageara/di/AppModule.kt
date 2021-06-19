package com.example.searchimageara.di

import android.content.Context
import com.example.searchimageara.SearchImagearaApplication
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideApplication(@ApplicationContext app:Context) : SearchImagearaApplication {
        return app as SearchImagearaApplication
    }

}