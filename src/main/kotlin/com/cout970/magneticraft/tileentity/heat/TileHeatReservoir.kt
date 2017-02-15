package com.cout970.magneticraft.tileentity.heat

import com.cout970.magneticraft.api.heat.IHeatNode
import com.cout970.magneticraft.api.internal.heat.HeatContainer
import com.cout970.magneticraft.util.COPPER_HEAT_CAPACITY
import com.cout970.magneticraft.util.COPPER_MELTING_POINT
import com.cout970.magneticraft.util.DEFAULT_CONDUCTIVITY

/**
 * Created by cout970 on 04/07/2016.
 */
class TileHeatReservoir : TileHeatBase() {

    val heat = HeatContainer(dissipation = 0.0,
            specificHeat = COPPER_HEAT_CAPACITY * 9,
            maxHeat = (COPPER_HEAT_CAPACITY * 3 * COPPER_MELTING_POINT).toLong(),
            conductivity = DEFAULT_CONDUCTIVITY,
            worldGetter = { this.world },
            posGetter = { this.getPos() })

    override val heatNodes: List<IHeatNode>
        get() = listOf(heat)
}