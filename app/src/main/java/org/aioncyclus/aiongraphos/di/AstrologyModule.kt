package org.aioncyclus.aiongraphos.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import org.aioncyclus.aiongraphos.data.ephemeris.SwissEphemerisCalculator
import org.aioncyclus.aiongraphos.data.ephemeris.SwissEphemerisProvider
import org.aioncyclus.aiongraphos.domain.analyst.AspectCalculator
import org.aioncyclus.aiongraphos.domain.analyst.ChartAnalyser
import org.aioncyclus.aiongraphos.domain.analyst.LotCalculator
import org.aioncyclus.aiongraphos.domain.calculator.ChartCalculator
import org.aioncyclus.aiongraphos.domain.calculator.HousesCalculator
import org.aioncyclus.aiongraphos.domain.calculator.PlanetCalculator
import org.aioncyclus.aiongraphos.domain.service.ChartService
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AstrologyModule {

    @Provides
    @Singleton
    fun provideChartService(
        @ApplicationContext context: Context
    ): ChartService {

        val swe = SwissEphemerisProvider.Companion.loadSwissEphemeris(context)
        val astroEngine = SwissEphemerisCalculator(swe)
        val planetCalculator= PlanetCalculator(astroEngine)
        val housesCalculator= HousesCalculator(astroEngine)
        val chartCalculator= ChartCalculator(planetCalculator, housesCalculator)
        val aspectCalculator= AspectCalculator()
        val lotCalculator= LotCalculator()
        val chartAnalyser= ChartAnalyser(aspectCalculator, lotCalculator)

        return ChartService(
            chartCalculator,
            chartAnalyser
        )
    }
}