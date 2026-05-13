package com.github.mrjimin.crafttable.util

import net.kyori.adventure.key.Key
import org.bukkit.Registry
import org.bukkit.Sound

object SoundUtil {

    fun parseSound(name: String): Sound? {
        val key = if (name.contains(":")) {
            Key.key(name.lowercase())
        } else {
            Key.key(Key.MINECRAFT_NAMESPACE, name.lowercase())
        }

        return Registry.SOUND_EVENT.get(org.bukkit.NamespacedKey.fromString(key.asString()) ?: return null)
    }
}