package com.github.mrjimin.crafttable.item

import com.github.mrjimin.crafttable.item.impl.CraftEngineProvider
import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import java.util.Locale

object ItemManager {
    private val sources = LinkedHashMap<String, ItemProvider>()
    private val materials by lazy { Material.entries.associateBy { it.name } }

    fun init() {
        register(
            CraftEngineProvider
        )
    }

    fun register(vararg providers: ItemProvider) {
        for (provider in providers) {
            sources[provider.identifier().uppercase(Locale.ROOT)] = provider
        }
    }

    fun unregister(provider: ItemProvider) {
        sources.remove(provider.identifier().uppercase(Locale.ROOT))
    }

    fun build(id: String): ItemStack {
        val split = id.indexOf(':')
        if (split == -1) {
            return ItemStack(materials[id.uppercase(Locale.ROOT)] ?: Material.STONE)
        }

        val prefix = id.substring(0, split).uppercase(Locale.ROOT)
        val provider = sources[prefix] ?: throw IllegalArgumentException("Unknown: $id")

        return provider.build(id.substring(split + 1)) ?: throw IllegalArgumentException("Invalid: $id")
    }

    fun id(item: ItemStack): String {
        val type = item.type
        if (type == Material.AIR) return "minecraft:air"

        val provider = sources.values.find { it.matches(item) }
        return if (provider != null) {
            "${provider.identifier().lowercase(Locale.ROOT)}:${provider.id(item)}"
        } else {
            "minecraft:${type.name.lowercase(Locale.ROOT)}"
        }
    }
}