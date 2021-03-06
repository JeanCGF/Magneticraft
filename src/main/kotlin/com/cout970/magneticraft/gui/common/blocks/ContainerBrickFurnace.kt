package com.cout970.magneticraft.gui.common.blocks


import com.cout970.magneticraft.gui.common.ContainerBase
import com.cout970.magneticraft.gui.common.DATA_ID_BURNING_TIME
import com.cout970.magneticraft.gui.common.DATA_ID_MACHINE_HEAT
import com.cout970.magneticraft.misc.network.IBD
import com.cout970.magneticraft.misc.tileentity.getTile
import com.cout970.magneticraft.tileentity.heat.TileBrickFurnace
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import net.minecraftforge.items.SlotItemHandler

/**
 * Created by cout970 on 22/07/2016.
 */
class ContainerBrickFurnace(player: EntityPlayer, world: World, blockPos: BlockPos) : ContainerBase(player, world, blockPos) {

    val tile = world.getTile<TileBrickFurnace>(blockPos)

    init {
        val inv = tile?.inventory
        inv?.let {
            addSlotToContainer(object : SlotItemHandler(inv, 0, 91, 16) {
                override fun isItemValid(stack: ItemStack?): Boolean {
                    return true
                }

                override fun canTakeStack(playerIn: EntityPlayer?): Boolean {
                    return true
                }

                override fun decrStackSize(amount: Int): ItemStack {
                    tile!!.inventory.ignoreFilter = true
                    val ret = this.itemHandler.extractItem(this.slotIndex, amount, false)
                    tile.inventory.ignoreFilter = false
                    return ret
                }
            })
            addSlotToContainer(object : SlotItemHandler(inv, 1, 91, 48) {
                override fun isItemValid(stack: ItemStack?): Boolean {
                    return false
                }

                override fun canTakeStack(playerIn: EntityPlayer?): Boolean {
                    return true
                }
            })
        }
        bindPlayerInventory(player.inventory)
    }

    override fun sendDataToClient(): IBD {
        tile!!
        val data = IBD()
        data.setFloat(DATA_ID_BURNING_TIME, tile.burningTime)
        data.setDouble(DATA_ID_MACHINE_HEAT, tile.heat.heat)
        return data
    }

    override fun receiveDataFromServer(ibd: IBD) {
        tile!!
        ibd.getFloat(DATA_ID_BURNING_TIME, { tile.burningTime = it })
        ibd.getDouble(DATA_ID_MACHINE_HEAT, { tile.heat.heat = it })
    }
}