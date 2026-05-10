package com.github.mrjimin.crafttable.item.impl

import com.github.mrjimin.crafttable.item.ItemSource
import net.momirealms.craftengine.bukkit.api.CraftEngineItems
import net.momirealms.craftengine.core.util.Key
import org.bukkit.inventory.ItemStack

object CraftEngineSource : ItemSource {
    override val plugin: String = "CRAFTENGINE"

    override fun build(id: String): ItemStack? {
        return CraftEngineItems.byId(Key.of(id))?.buildBukkitItem()
    }

    override fun id(itemStack: ItemStack): String {
        val itemId = CraftEngineItems.getCustomItemId(itemStack)
        return itemId.toString()
    }

    override fun matches(itemStack: ItemStack): Boolean {
        return CraftEngineItems.getCustomItemId(itemStack) != null
    }
}