package org.aioncyclus.aiongraphos.ui.screens.settings

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import org.aioncyclus.aiongraphos.data.model.ChartSettings
import org.aioncyclus.aiongraphos.data.repository.SettingsRepository
import javax.inject.Inject

@HiltViewModel
class SettingsScreenViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository
): ViewModel() {


    var state by mutableStateOf(SettingsScreenUIState())
        private set

    init {
        load()
    }

    fun load(
    ) {
        val settings = settingsRepository.getCurrentSettings()

        state = SettingsScreenUIState(
            settings
        )
    }

    fun applySettingChanges(chartSettings: ChartSettings)
    {
        settingsRepository.saveSettings(chartSettings)
    }

}