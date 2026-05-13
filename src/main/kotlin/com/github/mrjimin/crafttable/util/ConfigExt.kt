package com.github.mrjimin.crafttable.util

import org.bukkit.configuration.ConfigurationSection
import org.bukkit.configuration.MemoryConfiguration

fun ConfigurationSection.getSectionList(path: String): List<ConfigurationSection> {
    val rawList = getList(path) ?: return emptyList()
    return rawList.mapNotNull {
        when (it) {
            is ConfigurationSection -> it
            is Map<*, *> -> createSectionFromMap(it)
            else -> null
        }
    }
}

private fun createSectionFromMap(map: Map<*, *>): ConfigurationSection {
    val section = MemoryConfiguration()
    map.forEach { (key, value) ->
        val path = key.toString()
        if (value is Map<*, *>) {
            section.set(path, createSectionFromMap(value))
        } else {
            section.set(path, value)
        }
    }
    return section
}
