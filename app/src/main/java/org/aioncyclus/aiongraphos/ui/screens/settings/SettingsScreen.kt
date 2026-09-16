package org.aioncyclus.aiongraphos.ui.screens.settings

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchColors
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.sp
import org.aioncyclus.aiongraphos.data.model.ChartSettings
import org.aioncyclus.aiongraphos.domain.model.dignity.DignitySystemType
import org.aioncyclus.aiongraphos.domain.model.house.HouseSystem
import org.aioncyclus.aiongraphos.domain.model.lot.allLotTypes
import org.aioncyclus.aiongraphos.domain.model.planet.Planet
import org.aioncyclus.aiongraphos.ui.theme.Anton
import org.aioncyclus.aiongraphos.ui.theme.JetBrainsMono

@Composable
fun SettingsScreen(
    state: SettingsScreenUIState,
    onApplyClick: (ChartSettings) -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val settings = state.chartSettings ?: return
    var isTraditional by remember(settings) { mutableStateOf(settings.planets.size==7) }
    var isTrueNode by remember(settings) { mutableStateOf(settings.isTrueNode) }
    var selectedLots by remember(settings) { mutableStateOf(settings.lots.toList()) }
    var selectedHouseSystem by remember(settings) { mutableStateOf(settings.houseSystem) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding()
            .padding(10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {

        // ───────── HEADER ─────────

        Text(
            text = "CHART CONFIGURATION",
            fontFamily = JetBrainsMono,
            fontSize = 18.sp,
        )

        HorizontalDivider()


        // ───────── PRIMARY SWITCHES ─────────

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {

            SettingSwitchPanel(
                title = "TRADITIONAL PLANETS",
                checked = isTraditional,
                onCheckedChange =  { isTraditional = it },
                modifier = Modifier.weight(1f)
            )

            SettingSwitchPanel(
                title = "TRUE NODE",
                checked = isTrueNode,
                onCheckedChange = { isTrueNode = it },
                modifier = Modifier.weight(1f)
            )
        }


        // ───────── CONFIGURATION ─────────

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {

            SettingPanel(
                title = "01  HOUSE SYSTEM",
                modifier = Modifier.weight(1f)
            ) {
                HouseSystem.entries.forEach { system ->
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .selectable(
                                selected = system == selectedHouseSystem,
                                onClick = { selectedHouseSystem = system },
                                role = Role.RadioButton
                            )
                            .padding(vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(selected = system == selectedHouseSystem, onClick = null)
                        Text(text = system.systemName, fontFamily = JetBrainsMono)
                    }
                }
            }

            SettingPanel(
                title = "02  LOTS",
                modifier = Modifier.weight(1f)
            ) {
                allLotTypes.forEach { lot ->
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .selectable(
                                selected = lot in selectedLots,
                                onClick = {selectedLots=if (lot in selectedLots) {
                                    selectedLots - lot
                                } else {
                                    selectedLots + lot
                                }},
                                role = Role.Checkbox
                            )
                            .padding(vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(checked = lot in selectedLots, onCheckedChange = null)
                        Text(text = lot.name, fontFamily = JetBrainsMono)
                    }
                }
            }
        }

        HorizontalDivider()

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            OutlinedButton(
                onClick = {onBackClick()},
                shape = RectangleShape
            ) {
                Text(text="◀ BACK ",
                    fontFamily = Anton)
            }

            Button(
                onClick = {
                    onApplyClick(
                        ChartSettings(
                            buildPlanetsList(isTraditional),
                            selectedLots,
                            selectedHouseSystem,
                            isTrueNode,
                            DignitySystemType.TRADITIONAL
                        )
                    )
                },
                shape = RectangleShape
            ) {
                Text(text = "APPLY ▶",
                    fontFamily = Anton
                )
            }
        }
    }
}

private fun buildPlanetsList(isTraditional: Boolean): List<Planet>
{
    val planets=mutableListOf<Planet>(
        Planet.MOON,
        Planet.MERCURY,
        Planet.VENUS,
        Planet.SUN,
        Planet.MARS,
        Planet.JUPITER,
        Planet.SATURN
    )
    if (!isTraditional)
    {
        planets.add(Planet.URANUS)
        planets.add(Planet.NEPTUNE)
        planets.add(Planet.PLUTO)
    }
    return planets
}

@Composable
fun SettingPanel(
    title: String,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = modifier
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.onSurface
            )
            .padding(10.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Text(
            text = title,
            fontFamily = JetBrainsMono,
            fontSize = 14.sp,
        )

        HorizontalDivider()

        content()
    }
}

@Composable
fun SettingSwitchPanel(
    title: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .border(1.dp, MaterialTheme.colorScheme.onSurface)
            .padding(horizontal = 10.dp, vertical = 6.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp),
    ) {
        Text(
            text = title,
            fontFamily = JetBrainsMono,
            fontSize = 13.sp,
        )
        HorizontalDivider()
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors().copy(
                // When Checked & Enabled: Thumb and Track both Primary
                checkedThumbColor = MaterialTheme.colorScheme.onPrimary,
                checkedTrackColor = MaterialTheme.colorScheme.primary,

                // When Disabled (Regardless of Checked state):
                // Track becomes Background, Border becomes OnBackground
                uncheckedTrackColor = MaterialTheme.colorScheme.background,
                uncheckedThumbColor = MaterialTheme.colorScheme.onBackground,
                uncheckedBorderColor = MaterialTheme.colorScheme.onBackground
            ),
            modifier = Modifier.align(Alignment.End),
        )
    }
}