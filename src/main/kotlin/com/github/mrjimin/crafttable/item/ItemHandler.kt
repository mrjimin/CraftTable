package com.github.mrjimin.crafttable.item

import com.github.mrjimin.crafttable.item.impl.CraftEngineSource
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

object ItemHandler {

    private val sources = hashMapOf<String, ItemSource>()
    private val matchIndex = mutableListOf<ItemSource>()

    fun init() {
        register(CraftEngineSource)
    }

    fun register(source: ItemSource) {
        sources[source.plugin.uppercase()] = source
        matchIndex += source
    }

    fun build(id: String): ItemStack {
        return if (id.contains(":")) {
            val plugin = id.substringBefore(":").uppercase()
            val itemId = id.substringAfter(":")

            val source = sources[plugin]
                ?: throw IllegalArgumentException("Unknown item source: $plugin")

            source.build(itemId)
                ?: throw IllegalArgumentException("Invalid item id: $plugin:$itemId")

        } else {
            ItemStack(Material.valueOf(id.uppercase()))
        }
    }

    fun id(itemStack: ItemStack): String {
        val source = matchIndex.firstOrNull { it.matches(itemStack) }
            ?: throw IllegalArgumentException("Unknown item type")

        val id = source.id(itemStack)
        return "${source.plugin.lowercase()}:$id"
    }
}