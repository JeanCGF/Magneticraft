package com.cout970.magneticraft.gui.client.blocks

import com.cout970.magneticraft.config.Config
import com.cout970.magneticraft.gui.client.GuiBase
import com.cout970.magneticraft.gui.client.components.CallbackBarProvider
import com.cout970.magneticraft.gui.client.components.CompBackground
import com.cout970.magneticraft.gui.client.components.CompVerticalBar
import com.cout970.magneticraft.gui.common.ContainerBase
import com.cout970.magneticraft.tileentity.heat.TileFirebox
import com.cout970.magneticraft.util.STANDARD_AMBIENT_TEMPERATURE
import com.cout970.magneticraft.util.toCelsius
import com.cout970.magneticraft.util.toFahrenheit
import com.cout970.magneticraft.util.vector.Vec2d

/**
 * Created by cout970 on 08/07/2016.
 */
class GuiFirebox(container: ContainerBase) : GuiBase(container) {

    override fun initComponents() {
        components.add(CompBackground("incendiary_generator"))
        val tile = (container.tileEntity as TileFirebox)

        components.add(CompVerticalBar(
                CallbackBarProvider({ tile.heat.temperature }, { tile.heat.maxTemperature }, { STANDARD_AMBIENT_TEMPERATURE }),
                2, Vec2d(91, 56) + box.start, {
            listOf(
                    if (Config.heatUnitCelsius) {
                        String.format("%.2fC", tile.heat.temperature.toCelsius())
                    } else {
                        String.format("%.2fF", tile.heat.temperature.toFahrenheit())
                    })
        }))

        components.add(CompVerticalBar(
                CallbackBarProvider({ tile.burningTime.toDouble() }, { Math.max(tile.maxBurningTime.toDouble(), 1.0) }, { 0.0 }),
                1, Vec2d(102, 56) + box.start, { listOf("Fuel") }))
    }
}