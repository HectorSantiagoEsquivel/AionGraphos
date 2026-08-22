package org.aioncyclus.aiongraphos.di


import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.aioncyclus.aiongraphos.data.location.DefaultLocationTracker
import org.aioncyclus.aiongraphos.data.location.LocationTracker
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class LocationModule {
    @Binds
    @Singleton
    abstract fun bindLocationTracker(
        tracker: DefaultLocationTracker
    ): LocationTracker
}