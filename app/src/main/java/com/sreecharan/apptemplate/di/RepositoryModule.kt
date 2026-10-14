package com.sreecharan.apptemplate.di

import com.sreecharan.apptemplate.data.datastore.SettingsRepository
import com.sreecharan.apptemplate.data.datastore.SettingsRepositoryImpl
import com.sreecharan.apptemplate.data.repository.NotesRepository
import com.sreecharan.apptemplate.data.repository.NotesRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindNotesRepository(impl: NotesRepositoryImpl): NotesRepository

    @Binds
    abstract fun bindSettingsRepository(impl: SettingsRepositoryImpl): SettingsRepository
}
