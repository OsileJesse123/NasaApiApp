package com.jesse.nasaapi.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Qualifier

/**
 * This acts as a container for CoroutineDispatchers that would be required across the app.
 * It is responsible for creating, managing and providing these CoroutineDispatchers.
 * */
@Module
@InstallIn(SingletonComponent::class)
object CoroutineDispatcherModule {

    /**
     * This provides a CoroutineDispatcher of the type IO. This Dispatcher is used for running
     * operations such as network calls and/or database operations on the IO thread.
     * */
    @IoDispatcher
    @Provides
    fun provideIoDispatcher(): CoroutineDispatcher = Dispatchers.IO


    /**
     * This provides a CoroutineDispatcher of the type Default. This Dispatcher is used for running
     * operations such as sorting a list on the Default thread.
     * */
    @DefaultDispatcher
    @Provides
    fun provideDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default
}

// The annotation classes are used to differentiate between the different Coroutine Dispatcher
// when creating new instances.
@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class IoDispatcher

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class DefaultDispatcher